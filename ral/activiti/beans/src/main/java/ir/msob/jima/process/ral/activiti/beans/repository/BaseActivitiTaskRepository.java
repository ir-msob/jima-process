package ir.msob.jima.process.ral.activiti.beans.repository;

import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.process.commons.criteria.BaseTaskCriteria;
import ir.msob.jima.process.commons.domain.BaseTask;
import ir.msob.jima.process.ral.activiti.beans.query.TaskQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.TaskQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public abstract class BaseActivitiTaskRepository<D extends BaseTask, C extends BaseTaskCriteria> implements ir.msob.jima.process.commons.repository.BaseTaskRepository<D, C> {
    private final TaskService taskService;
    private final TaskQueryBuilder queryBuilder;

    public BaseQueryBuilder getQueryBuilder() {
        return this.queryBuilder;
    }

    @Override
    public Mono<D> save(D dto) {
        org.activiti.engine.task.Task task = taskService.saveTask(prepareTask(dto));
        return Mono.just(prepareDomain(task));
    }

    @Override
    public Mono<D> complete(C criteria, D dto) {
        return getOne(criteria)
                .doOnSuccess(task -> taskService.complete(criteria.getId().getEq(), dto.getTaskVariables()));
    }

    @Override
    public Mono<String> delete(C criteria) {
        taskService.deleteTask(criteria.getId().getEq(), true);
        return Mono.just(criteria.getId().getEq());
    }

    @Override
    public Mono<D> getOne(C criteria) {
        TaskQuery taskQuery = getQueryBuilder().build(criteria);
        return Mono.just(prepareDomain(taskQuery.singleResult()));
    }

    @Override
    public Mono<Long> count(C criteria) {
        TaskQuery taskQuery = getQueryBuilder().build(criteria);
        return Mono.just(taskQuery.count());
    }

    @Override
    public Mono<Page<D>> getPage(C criteria, Pageable pageable) {
        TaskQuery taskQuery = getQueryBuilder().build(criteria);
        long count = taskQuery.count();
        if (count < 1) {
            return Mono.just(new PageImpl<>(new ArrayList<>(), pageable, 0L));
        } else {
            Collection<org.activiti.engine.task.Task> tasks = taskQuery.listPage(pageable.getPageNumber(), pageable.getPageSize());
            return Mono.just(new PageImpl<>(prepareDomains(tasks), pageable, count));
        }
    }

    private org.activiti.engine.task.Task prepareTask(D dto) {
        org.activiti.engine.task.Task task = taskService.newTask();
        task.setName(dto.getName());
        task.setLocalizedName(dto.getLocalizedName());
        task.setDescription(dto.getDescription());
        task.setLocalizedDescription(dto.getLocalizedDescription());
        task.setPriority(dto.getPriority());
        task.setOwner(dto.getOwner());
        task.setAssignee(dto.getAssignee());
        task.setDueDate(Date.from(dto.getDueDate()));
        task.setCategory(dto.getCategory());
        task.setParentTaskId(dto.getParentTaskId());
        task.setTenantId(dto.getTenantId());
        task.setFormKey(dto.getFormKey());
        task.setAppVersion(dto.getAppVersion());
        task.getTaskLocalVariables().putAll(dto.getTaskVariables());
        task.getProcessVariables().putAll(dto.getProcessVariables());
        return task;
    }

    private List<D> prepareDomains(Collection<org.activiti.engine.task.Task> tasks) {
        return tasks.stream().map(this::prepareDomain).toList();
    }

    private D prepareDomain(org.activiti.engine.task.Task task) {
        D domain = (D) new BaseTask();
        domain.setId(task.getId());
        domain.setName(task.getName());
        domain.setLocalizedName(domain.getLocalizedName());
        domain.setDescription(task.getDescription());
        domain.setPriority(task.getPriority());
        domain.setOwner(task.getOwner());
        domain.setAssignee(task.getAssignee());
        domain.setDelegationState(prepareDelegationState(task.getDelegationState()));
        if (task.getDueDate() != null)
            domain.setDueDate(task.getDueDate().toInstant());
        domain.setCategory(task.getCategory());
        domain.setParentTaskId(task.getParentTaskId());
        domain.setTenantId(task.getTenantId());
        domain.setFormKey(task.getFormKey());
        domain.setAppVersion(task.getAppVersion());
        domain.setSuspended(task.isSuspended());
        domain.setTaskVariables(task.getTaskLocalVariables());
        domain.setProcessVariables(task.getProcessVariables());
        return domain;
    }

    private D.DelegationState prepareDelegationState(DelegationState delegationState) {
        if (delegationState == DelegationState.PENDING)
            return D.DelegationState.PENDING;
        else if (delegationState == DelegationState.RESOLVED)
            return D.DelegationState.RESOLVED;
        return null;
    }
}
