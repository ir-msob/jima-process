package ir.msob.jima.process.ral.activiti.beans;

import ir.msob.jima.process.commons.criteria.TaskCriteria;
import ir.msob.jima.process.commons.dto.TaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository implements BaseTaskRepository {
    private final TaskService taskService;

    @Override
    public Mono<TaskDto> save(TaskDto dto) {
        Task task = taskService.saveTask(prepareTask(dto));
        return Mono.just(prepareTaskDto(task));
    }

    @Override
    public Mono<TaskDto> complete(TaskCriteria criteria, TaskDto dto) {
        return getOne(criteria)
                .doOnSuccess(task -> taskService.complete(criteria.getId().getEq(), dto.getTaskVariables()));
    }

    @Override
    public Mono<String> delete(TaskCriteria criteria) {
        taskService.deleteTask(criteria.getId().getEq(), true);
        return Mono.just(criteria.getId().getEq());
    }

    @Override
    public Mono<TaskDto> getOne(TaskCriteria criteria) {
        TaskQuery taskQuery = prepareTaskQuery(criteria);
        return Mono.just(prepareTaskDto(taskQuery.singleResult()));
    }

    @Override
    public Mono<Long> count(TaskCriteria criteria) {
        return Mono.just(prepareTaskQuery(criteria).count());
    }

    @Override
    public Mono<Page<TaskDto>> getPage(TaskCriteria criteria, Pageable pageable) {
        long count = prepareTaskQuery(criteria).count();
        if (count < 1) {
            return Mono.just(new PageImpl<>(new ArrayList<>(), pageable, 0L));
        } else {
            Collection<Task> tasks = prepareTaskQuery(criteria).listPage(pageable.getPageNumber(), pageable.getPageSize());
            return Mono.just(new PageImpl<>(prepareTaskDto(tasks), pageable, count));
        }
    }

    private TaskQuery prepareTaskQuery(TaskCriteria criteria) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (criteria != null) {

            if (criteria.getId() != null)
                taskQuery.taskId(criteria.getId().getEq());
            if (criteria.getName() != null)
                taskQuery.taskName(criteria.getName().getEq());
            if (criteria.getDescription() != null)
                taskQuery.taskDescription(criteria.getDescription().getEq());
            if (criteria.getPriority() != null)
                taskQuery.taskPriority(criteria.getPriority().getEq());
            if (criteria.getOwner() != null)
                taskQuery.taskOwner(criteria.getOwner().getEq());
            if (criteria.getAssignee() != null)
                taskQuery.taskAssignee(criteria.getAssignee().getEq());
            if (criteria.getDueDate() != null)
                taskQuery.taskDueDate(Date.from(criteria.getDueDate().getEq()));
            if (criteria.getCategory() != null)
                taskQuery.taskCategory(criteria.getCategory().getEq());
            if (criteria.getParentTaskId() != null)
                taskQuery.taskParentTaskId(criteria.getParentTaskId().getEq());
            if (criteria.getTenantId() != null)
                taskQuery.taskTenantId(criteria.getTenantId().getEq());
            if (criteria.getSuspended() != null) {
                if (criteria.getSuspended().getEq()) {
                    taskQuery.suspended();
                }
            }
        }
        return taskQuery;
    }

    private Task prepareTask(TaskDto dto) {
        Task task = taskService.newTask();
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

    private List<TaskDto> prepareTaskDto(Collection<Task> tasks) {
        return tasks.stream().map(this::prepareTaskDto).toList();
    }

    private TaskDto prepareTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setLocalizedName(taskDto.getLocalizedName());
        taskDto.setDescription(task.getDescription());
        taskDto.setPriority(task.getPriority());
        taskDto.setOwner(task.getOwner());
        taskDto.setAssignee(task.getAssignee());
        taskDto.setDelegationState(prepareDelegationState(task.getDelegationState()));
        if (task.getDueDate() != null)
            taskDto.setDueDate(task.getDueDate().toInstant());
        taskDto.setCategory(task.getCategory());
        taskDto.setParentTaskId(task.getParentTaskId());
        taskDto.setTenantId(task.getTenantId());
        taskDto.setFormKey(task.getFormKey());
        taskDto.setAppVersion(task.getAppVersion());
        taskDto.setSuspended(task.isSuspended());
        taskDto.setTaskVariables(task.getTaskLocalVariables());
        taskDto.setProcessVariables(task.getProcessVariables());
        return taskDto;
    }

    private TaskDto.DelegationState prepareDelegationState(DelegationState delegationState) {
        if (delegationState == DelegationState.PENDING)
            return TaskDto.DelegationState.PENDING;
        else if (delegationState == DelegationState.RESOLVED)
            return TaskDto.DelegationState.RESOLVED;
        return null;
    }
}
