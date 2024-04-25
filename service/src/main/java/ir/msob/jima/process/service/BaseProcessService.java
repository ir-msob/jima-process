package ir.msob.jima.process.service;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.ProcessCriteria;
import ir.msob.jima.process.commons.dto.ProcessDto;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface BaseProcessService<USER extends BaseUser, PR extends BaseProcessRepository> {

    PR getProcessRepository();

    @MethodStats
    default Mono<ProcessDto> save(ProcessDto dto, Optional<USER> user) {
        return getProcessRepository().save(dto);
    }

    @MethodStats
    default Mono<ProcessDto> start(ProcessDto dto, Optional<USER> user) {
        return getProcessRepository().start(dto);
    }

    @MethodStats
    default Mono<Boolean> delete(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().delete(criteria);
    }

    @MethodStats
    default Mono<Boolean> suspend(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().suspend(criteria);
    }

    @MethodStats
    default Mono<Boolean> resume(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().resume(criteria);
    }

    @MethodStats
    default Mono<ProcessDto> getOne(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().getOne(criteria);
    }

    @MethodStats
    default Mono<Long> count(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().count(criteria);
    }

    @MethodStats
    default Mono<Page<ProcessDto>> getPage(ProcessCriteria criteria, Pageable pageable, Optional<USER> user) {
        return getProcessRepository().getPage(criteria, pageable);
    }
}
