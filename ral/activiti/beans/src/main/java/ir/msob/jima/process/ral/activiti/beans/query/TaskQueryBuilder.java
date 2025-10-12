package ir.msob.jima.process.ral.activiti.beans.query;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import org.activiti.engine.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class TaskQueryBuilder implements BaseQueryBuilder {

    private final TaskService taskService;

    public TaskQueryBuilder(TaskService taskService) {
        this.taskService = taskService;
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
        org.activiti.engine.task.TaskQuery q = taskService.createTaskQuery();
        if (criteria != null) ActivitiQueryUtils.applyCriteriaToQuery(q, criteria);
        TaskQuery wrapper = new TaskQuery(q);
        if (pageable != null) wrapper.setPageable(pageable);
        return (Q) wrapper;
    }
}