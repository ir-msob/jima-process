package ir.msob.jima.process.ral.activiti.beans.query;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import org.activiti.engine.RepositoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class DeploymentQueryBuilder implements BaseQueryBuilder {

    private final RepositoryService repositoryService;

    public DeploymentQueryBuilder(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public <ID extends Comparable<ID> & Serializable, C extends BaseCriteria<ID>, Q extends ir.msob.jima.core.commons.repository.BaseQuery>
    Q build(C criteria) {
        return build(criteria, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ID extends Comparable<ID> & Serializable, C extends BaseCriteria<ID>, Q extends ir.msob.jima.core.commons.repository.BaseQuery>
    Q build(C criteria, Pageable pageable) {
        org.activiti.engine.repository.DeploymentQuery q = repositoryService.createDeploymentQuery();
        if (criteria != null) ActivitiQueryUtils.applyCriteriaToQuery(q, criteria);
        DeploymentQuery wrapper = new DeploymentQuery(q);
        if (pageable != null) wrapper.setPageable(pageable);
        return (Q) wrapper;
    }
}