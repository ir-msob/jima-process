package ir.msob.jima.process.ral.activiti.beans;

import ir.msob.jima.process.commons.criteria.TaskCriteria;
import ir.msob.jima.process.commons.dto.TaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public Mono<TaskDto> complete(TaskCriteria criteria) {
        return getOne(criteria)
                .doOnSuccess(task -> taskService.complete(criteria.getId(), criteria.getVariables()));
    }

    @Override
    public Mono<Boolean> delete(TaskCriteria criteria) {
        taskService.deleteTask(criteria.getId(), true);
        return Mono.just(true);
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
        if (Strings.isNotBlank(criteria.getId()))
            taskQuery.taskId(criteria.getId());
        if (Strings.isNotBlank(criteria.getName()))
            taskQuery.taskName(criteria.getName());
        if (Strings.isNotBlank(criteria.getDescription()))
            taskQuery.taskDescription(criteria.getDescription());
        if (criteria.getPriority() != null)
            taskQuery.taskPriority(criteria.getPriority());
        if (Strings.isNotBlank(criteria.getOwner()))
            taskQuery.taskOwner(criteria.getOwner());
        if (Strings.isNotBlank(criteria.getAssignee()))
            taskQuery.taskAssignee(criteria.getAssignee());
        if (criteria.getDueDate() != null)
            taskQuery.taskDueDate(Date.from(criteria.getDueDate()));
        if (Strings.isNotBlank(criteria.getCategory()))
            taskQuery.taskCategory(criteria.getCategory());
        if (Strings.isNotBlank(criteria.getParentTaskId()))
            taskQuery.taskParentTaskId(criteria.getParentTaskId());
        if (Strings.isNotBlank(criteria.getTenantId()))
            taskQuery.taskTenantId(criteria.getTenantId());
        if (criteria.isSuspended())
            taskQuery.suspended();
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
        return task;
    }

    private List<TaskDto> prepareTaskDto(Collection<Task> tasks) {
        return tasks.stream().map(task -> prepareTaskDto(task)).collect(Collectors.toList());
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
        taskDto.setDelegationState(prepareDelegationState(task));
        if (task.getDueDate() != null)
            taskDto.setDueDate(task.getDueDate().toInstant());
        taskDto.setCategory(task.getCategory());
        taskDto.setParentTaskId(task.getParentTaskId());
        taskDto.setTenantId(task.getTenantId());
        taskDto.setFormKey(task.getFormKey());
        taskDto.setAppVersion(task.getAppVersion());
        taskDto.setSuspended(task.isSuspended());
        return taskDto;
    }

    private TaskDto.DelegationState prepareDelegationState(Task task) {
        if (task.getDelegationState() == DelegationState.PENDING)
            return TaskDto.DelegationState.PENDING;
        else if (task.getDelegationState() == DelegationState.RESOLVED)
            return TaskDto.DelegationState.RESOLVED;
        return null;
    }
}
