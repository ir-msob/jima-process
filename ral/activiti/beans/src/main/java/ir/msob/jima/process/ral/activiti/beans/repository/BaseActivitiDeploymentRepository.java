package ir.msob.jima.process.ral.activiti.beans.repository;

import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.process.commons.criteria.BaseDeploymentCriteria;
import ir.msob.jima.process.commons.domain.BaseDeployment;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.ral.activiti.beans.Constants;
import ir.msob.jima.process.ral.activiti.beans.query.DeploymentQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public abstract class BaseActivitiDeploymentRepository<D extends BaseDeployment, C extends BaseDeploymentCriteria> implements BaseDeploymentRepository<D, C> {
    private final RepositoryService repositoryService;
    private final DeploymentQueryBuilder queryBuilder;

    public BaseQueryBuilder getQueryBuilder() {
        return this.queryBuilder;
    }

    @Override
    public Mono<D> save(D domain, InputStream inputStream) {
        org.activiti.engine.repository.Deployment deployment = repositoryService.createDeployment()
                .name(domain.getName())
                .key(domain.getKey())
                .category(domain.getCategory())
                .tenantId(domain.getTenantId())
                .addInputStream(domain.getName() + Constants.BPMN_FILE_POSTFIX, inputStream)
                .deploy();
        prepareDomain(domain, deployment);
        return Mono.just(domain);
    }


    @Override
    public Mono<Page<D>> getPage(C criteria, Pageable pageable) {
        DeploymentQuery deploymentQuery = getQueryBuilder().build(criteria);
        long count = deploymentQuery.count();
        if (count < 1) {
            return Mono.just(new PageImpl<>(new ArrayList<>(), pageable, 0L));
        } else {
            Collection<org.activiti.engine.repository.Deployment> deployments = deploymentQuery.listPage(pageable.getPageNumber(), pageable.getPageSize());
            return Mono.just(new PageImpl<>(prepareDomains(deployments), pageable, count));
        }
    }

    @Override
    public Mono<D> getOne(C criteria) {
        DeploymentQuery deploymentQuery = getQueryBuilder().build(criteria);
        org.activiti.engine.repository.Deployment deployment = deploymentQuery.latest().singleResult();
        return Mono.just(prepareDomain(deployment));
    }

    @Override
    public Mono<Long> count(C criteria) {
        DeploymentQuery deploymentQuery = getQueryBuilder().build(criteria);
        return Mono.just(deploymentQuery.latest().count());
    }

    @Override
    public Mono<D> delete(C criteria) {
        DeploymentQuery deploymentQuery = getQueryBuilder().build(criteria);
        org.activiti.engine.repository.Deployment deployment = deploymentQuery.latest().singleResult();
        repositoryService.deleteDeployment(criteria.getId().getEq(), true);
        return Mono.just(prepareDomain(deployment));
    }

    private List<D> prepareDomains(Collection<org.activiti.engine.repository.Deployment> deployments) {
        return deployments.stream().map(this::prepareDomain).toList();
    }

    private D prepareDomain(org.activiti.engine.repository.Deployment deployment) {
        D domain = (D) new BaseDeployment();
        prepareDomain(domain, deployment);
        return domain;
    }

    private void prepareDomain(D domain, org.activiti.engine.repository.Deployment deployment) {
        domain.setId(deployment.getId());
        domain.setName(deployment.getName());
        domain.setDeploymentDate(deployment.getDeploymentTime().toInstant());
        domain.setCategory(deployment.getCategory());
        domain.setKey(deployment.getCategory());
        domain.setTenantId(deployment.getTenantId());
        domain.setVersion(deployment.getVersion());
        domain.setProjectReleaseVersion(deployment.getProjectReleaseVersion());
    }
}
