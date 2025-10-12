package ir.msob.jima.process.commons.repository;

import ir.msob.jima.core.commons.repository.BaseRepository;
import ir.msob.jima.process.commons.criteria.BaseTaskCriteria;
import ir.msob.jima.process.commons.domain.BaseTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BaseTaskRepository<D extends BaseTask, C extends BaseTaskCriteria> extends BaseRepository<String, D> {

    Mono<D> save(D domain);

    Mono<D> complete(C criteria, D domain);

    Mono<String> delete(C criteria);

    Mono<D> getOne(C criteria);

    Mono<Long> count(C criteria);

    Mono<Page<D>> getPage(C criteria, Pageable pageable);
}
