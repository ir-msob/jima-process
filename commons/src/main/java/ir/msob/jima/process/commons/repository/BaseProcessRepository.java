package ir.msob.jima.process.commons.repository;

import ir.msob.jima.core.commons.repository.BaseRepository;
import ir.msob.jima.process.commons.criteria.BaseProcessCriteria;
import ir.msob.jima.process.commons.domain.BaseProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BaseProcessRepository<D extends BaseProcess, C extends BaseProcessCriteria> extends BaseRepository<String, D> {

    Mono<D> save(D domain);

    Mono<D> start(C criteria, D domain);

    Mono<String> delete(C criteria);

    Mono<Boolean> suspend(C criteria);

    Mono<Boolean> resume(C criteria);

    Mono<D> getOne(C criteria);

    Mono<Long> count(C criteria);

    Mono<Page<D>> getPage(C criteria, Pageable pageable);
}
