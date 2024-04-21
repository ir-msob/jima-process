package ir.msob.jima.process.commons.repository;

import ir.msob.jima.process.commons.criteria.TaskCriteria;
import ir.msob.jima.process.commons.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BaseTaskRepository {

    Mono<TaskDto> save(TaskDto dto);

    Mono<TaskDto> complete(TaskCriteria criteria);

    Mono<Boolean> delete(TaskCriteria criteria);

    Mono<TaskDto> getOne(TaskCriteria criteria);

    Mono<Long> count(TaskCriteria criteria);

    Mono<Page<TaskDto>> getPage(TaskCriteria criteria, Pageable pageable);
}
