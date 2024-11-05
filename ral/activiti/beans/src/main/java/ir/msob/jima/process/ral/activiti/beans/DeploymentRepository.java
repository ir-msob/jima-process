package ir.msob.jima.process.ral.activiti.beans;

import ir.msob.jima.process.commons.criteria.DeploymentCriteria;
import ir.msob.jima.process.commons.dto.DeploymentDto;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeploymentRepository implements BaseDeploymentRepository {
    private final RepositoryService repositoryService;

    @Override
    public Mono<DeploymentDto> save(DeploymentDto dto, InputStream inputStream) {
        Deployment deployment = repositoryService.createDeployment()
                .name(dto.getName())
                .key(dto.getKey())
                .category(dto.getCategory())
                .tenantId(dto.getTenantId())
                .addInputStream(dto.getName() + Constants.BPMN_FILE_POSTFIX, inputStream)
                .deploy();
        prepareDeploymentDto(dto, deployment);
        return Mono.just(dto);
    }


    @Override
    public Mono<Page<DeploymentDto>> getPage(DeploymentCriteria criteria, Pageable pageable) {
        long count = prepareDeploymentQuery(criteria).count();
        if (count < 1) {
            return Mono.just(new PageImpl<>(new ArrayList<>(), pageable, 0L));
        } else {
            Collection<Deployment> deployments = prepareDeploymentQuery(criteria).listPage(pageable.getPageNumber(), pageable.getPageSize());
            return Mono.just(new PageImpl<>(prepareDeploymentDto(deployments), pageable, count));
        }
    }

    @Override
    public Mono<DeploymentDto> getOne(DeploymentCriteria criteria) {
        Deployment deployment = prepareDeploymentQuery(criteria).latest().singleResult();
        return Mono.just(prepareDeploymentDto(deployment));
    }

    @Override
    public Mono<Long> count(DeploymentCriteria criteria) {
        return Mono.just(prepareDeploymentQuery(criteria).latest().count());
    }

    @Override
    public Mono<DeploymentDto> delete(DeploymentCriteria criteria) {
        Deployment deployment = prepareDeploymentQuery(criteria).latest().singleResult();
        repositoryService.deleteDeployment(criteria.getId().getEq(), true);
        return Mono.just(prepareDeploymentDto(deployment));
    }

    private DeploymentQuery prepareDeploymentQuery(DeploymentCriteria criteria) {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        if (criteria != null) {
            if (criteria.getId() != null)
                deploymentQuery.deploymentId(criteria.getId().getEq());
            if (criteria.getName() != null) {
                if (criteria.getName().getEq() != null)
                    deploymentQuery.deploymentName(criteria.getName().getEq());
                else if (criteria.getName().getRegex() != null) {
                    deploymentQuery.deploymentNameLike(criteria.getName().getRegex());
                }
            }
            if (criteria.getCategory() != null) {
                if (criteria.getCategory().getEq() != null)
                    deploymentQuery.deploymentCategory(criteria.getCategory().getEq());
                else if (criteria.getCategory().getRegex() != null) {
                    deploymentQuery.deploymentCategoryLike(criteria.getCategory().getRegex());
                } else if (criteria.getCategory().getNe() != null) {
                    deploymentQuery.deploymentCategoryNotEquals(criteria.getCategory().getNe());
                }
            }
            if (criteria.getKey() != null) {
                if (criteria.getKey().getEq() != null)
                    deploymentQuery.deploymentKey(criteria.getKey().getEq());
                else if (criteria.getKey().getRegex() != null) {
                    deploymentQuery.deploymentKeyLike(criteria.getKey().getRegex());
                }
            }
            if (criteria.getTenantId() != null) {
                if (criteria.getTenantId().getEq() != null)
                    deploymentQuery.deploymentTenantId(criteria.getTenantId().getEq());
                else if (criteria.getTenantId().getRegex() != null) {
                    deploymentQuery.deploymentTenantIdLike(criteria.getTenantId().getRegex());
                } else if (criteria.getTenantId().getExists() != null
                        && !criteria.getTenantId().getExists()) {
                    deploymentQuery.deploymentWithoutTenantId();
                }
            }
        }
        return deploymentQuery;
    }

    private List<DeploymentDto> prepareDeploymentDto(Collection<Deployment> deployments) {
        return deployments.stream().map(this::prepareDeploymentDto).toList();
    }

    private DeploymentDto prepareDeploymentDto(Deployment deployment) {
        DeploymentDto dto = new DeploymentDto();
        prepareDeploymentDto(dto, deployment);
        return dto;
    }

    private void prepareDeploymentDto(DeploymentDto dto, Deployment deployment) {
        dto.setId(deployment.getId());
        dto.setName(deployment.getName());
        dto.setDeploymentDate(deployment.getDeploymentTime().toInstant());
        dto.setCategory(deployment.getCategory());
        dto.setKey(deployment.getCategory());
        dto.setTenantId(deployment.getTenantId());
        dto.setVersion(deployment.getVersion());
        dto.setProjectReleaseVersion(deployment.getProjectReleaseVersion());
    }
}
