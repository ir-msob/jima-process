package ir.msob.jima.process.ral.activiti.beans.query;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import org.activiti.engine.RuntimeService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ProcessInstanceQueryBuilder implements BaseQueryBuilder {

    private final RuntimeService runtimeService;

    public ProcessInstanceQueryBuilder(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
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
        org.activiti.engine.runtime.ProcessInstanceQuery q = runtimeService.createProcessInstanceQuery();
        if (criteria != null) ActivitiQueryUtils.applyCriteriaToQuery(q, criteria);
        ProcessInstanceQuery wrapper = new ProcessInstanceQuery(q);
        if (pageable != null) wrapper.setPageable(pageable);
        return (Q) wrapper;
    }
}
