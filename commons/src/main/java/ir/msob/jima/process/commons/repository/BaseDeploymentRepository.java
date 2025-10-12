package ir.msob.jima.process.commons.repository;

import ir.msob.jima.core.commons.repository.BaseRepository;
import ir.msob.jima.process.commons.criteria.BaseDeploymentCriteria;
import ir.msob.jima.process.commons.domain.BaseDeployment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.io.InputStream;

public interface BaseDeploymentRepository<D extends BaseDeployment, C extends BaseDeploymentCriteria> extends BaseRepository<String, D> {
    Mono<D> save(D dto, InputStream inputStream);

    Mono<Page<D>> getPage(C criteria, Pageable pageable);

    Mono<D> getOne(C criteria);

    Mono<Long> count(C criteria);

    Mono<D> delete(C criteria);
}
