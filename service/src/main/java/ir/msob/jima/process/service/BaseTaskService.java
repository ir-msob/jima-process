package ir.msob.jima.process.service;

import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.TaskCriteria;
import ir.msob.jima.process.commons.dto.TaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BaseTaskService<USER extends BaseUser, TR extends BaseTaskRepository> {

    TR getTaskRepository();

    @MethodStats
    default Mono<TaskDto> save(TaskDto dto, USER user) {
        return getTaskRepository().save(dto);
    }

    @MethodStats
    default Mono<TaskDto> complete(TaskCriteria criteria, TaskDto dto, USER user) {
        return getTaskRepository().complete(criteria, dto);
    }

    @MethodStats
    default Mono<TaskDto> completeById(String id, TaskDto dto, USER user) {
        TaskCriteria criteria = TaskCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return complete(criteria, dto, user);
    }

    @MethodStats
    default Mono<String> delete(TaskCriteria criteria, USER user) {
        return getTaskRepository().delete(criteria);
    }

    @MethodStats
    default Mono<String> deleteById(String id, USER user) {
        TaskCriteria criteria = TaskCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return delete(criteria, user);
    }

    @MethodStats
    default Mono<TaskDto> getOne(TaskCriteria criteria, USER user) {
        return getTaskRepository().getOne(criteria);
    }

    @MethodStats
    default Mono<TaskDto> getById(String id, USER user) {
        TaskCriteria criteria = TaskCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return getOne(criteria, user);
    }

    @MethodStats
    default Mono<Long> count(TaskCriteria criteria, USER user) {
        return getTaskRepository().count(criteria);
    }

    @MethodStats
    default Mono<Page<TaskDto>> getPage(TaskCriteria criteria, Pageable pageable, USER user) {
        return getTaskRepository().getPage(criteria, pageable);
    }
}
