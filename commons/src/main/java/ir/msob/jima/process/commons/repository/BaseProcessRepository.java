package ir.msob.jima.process.commons.repository;

import ir.msob.jima.process.commons.criteria.ProcessCriteria;
import ir.msob.jima.process.commons.dto.ProcessDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BaseProcessRepository {

    Mono<ProcessDto> save(ProcessDto dto);

    Mono<ProcessDto> start(ProcessDto dto);

    Mono<Boolean> delete(ProcessCriteria criteria);

    Mono<Boolean> suspend(ProcessCriteria criteria);

    Mono<Boolean> resume(ProcessCriteria criteria);

    Mono<ProcessDto> getOne(ProcessCriteria criteria);

    Mono<Long> count(ProcessCriteria criteria);

    Mono<Page<ProcessDto>> getPage(ProcessCriteria criteria, Pageable pageable);
}
