package ir.msob.jima.process.service;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.model.criteria.filter.Filter;
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
    default Mono<ProcessDto> start(ProcessCriteria criteria, ProcessDto dto, Optional<USER> user) {
        return getProcessRepository().start(criteria, dto);
    }

    @MethodStats
    default Mono<ProcessDto> startById(String id, ProcessDto dto, Optional<USER> user) {
        ProcessCriteria criteria = ProcessCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return start(criteria, dto, user);
    }

    @MethodStats
    default Mono<String> delete(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().delete(criteria);
    }

    @MethodStats
    default Mono<String> deleteById(String id, Optional<USER> user) {
        ProcessCriteria criteria = ProcessCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return delete(criteria, user);
    }

    @MethodStats
    default Mono<ProcessDto> suspend(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().suspend(criteria)
                .then(getOne(criteria, user));
    }

    @MethodStats
    default Mono<ProcessDto> suspendById(String id, Optional<USER> user) {
        ProcessCriteria criteria = ProcessCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return suspend(criteria, user);
    }

    @MethodStats
    default Mono<ProcessDto> resume(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().resume(criteria)
                .then(getOne(criteria, user));
    }

    @MethodStats
    default Mono<ProcessDto> resumeById(String id, Optional<USER> user) {
        ProcessCriteria criteria = ProcessCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return resume(criteria, user);
    }

    @MethodStats
    default Mono<ProcessDto> getOne(ProcessCriteria criteria, Optional<USER> user) {
        return getProcessRepository().getOne(criteria);
    }

    @MethodStats
    default Mono<ProcessDto> getById(String id, Optional<USER> user) {
        ProcessCriteria criteria = ProcessCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return getOne(criteria, user);
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
