package ir.msob.jima.process.service;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.TaskCriteria;
import ir.msob.jima.process.commons.dto.TaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;

public interface BaseTaskService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, TR extends BaseTaskRepository> {

    TR getTaskRepository();

    @MethodStats
    default Mono<TaskDto> save(TaskDto dto, Optional<USER> user) {
        return getTaskRepository().save(dto);
    }

    @MethodStats
    default Mono<TaskDto> complete(TaskCriteria criteria, Optional<USER> user) {
        return getTaskRepository().complete(criteria);
    }

    @MethodStats
    default Mono<Boolean> delete(TaskCriteria criteria, Optional<USER> user) {
        return getTaskRepository().delete(criteria);
    }

    @MethodStats
    default Mono<TaskDto> getOne(TaskCriteria criteria, Optional<USER> user) {
        return getTaskRepository().getOne(criteria);
    }

    @MethodStats
    default Mono<Long> count(TaskCriteria criteria, Optional<USER> user) {
        return getTaskRepository().count(criteria);
    }

    @MethodStats
    default Mono<Page<TaskDto>> getPage(TaskCriteria criteria, Pageable pageable, Optional<USER> user) {
        return getTaskRepository().getPage(criteria, pageable);
    }
}
