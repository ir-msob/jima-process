package ir.msob.jima.process.commons.service;

import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.process.commons.criteria.BaseProcessCriteria;
import ir.msob.jima.process.commons.domain.BaseProcess;
import ir.msob.jima.process.commons.dto.BaseProcessDto;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BaseProcessService<USER extends BaseUser, D extends BaseProcess, DTO extends BaseProcessDto, C extends BaseProcessCriteria, R extends BaseProcessRepository<D, C>> {

    R getRepository();

    DTO toDto(D domain, USER user);

    D toDomain(DTO dto, USER user);


    @MethodStats
    default Mono<DTO> save(DTO dto, USER user) {
        return getRepository().save(toDomain(dto, user))
                .map(d -> toDto(d, user));
    }

    @MethodStats
    default Mono<DTO> start(C criteria, DTO dto, USER user) {
        return getRepository().start(criteria, toDomain(dto, user))
                .map(d -> toDto(d, user));
    }

    @MethodStats
    default Mono<DTO> startById(String id, DTO dto, USER user) {
        C criteria = CriteriaUtil.idCriteria(id);
        return start(criteria, dto, user);
    }

    @MethodStats
    default Mono<String> delete(C criteria, USER user) {
        return getRepository().delete(criteria);
    }

    @MethodStats
    default Mono<String> deleteById(String id, USER user) {
        C criteria = CriteriaUtil.idCriteria(id);
        return delete(criteria, user);
    }

    @MethodStats
    default Mono<DTO> suspend(C criteria, USER user) {
        return getRepository().suspend(criteria)
                .then(getOne(criteria, user));
    }

    @MethodStats
    default Mono<DTO> suspendById(String id, USER user) {
        C criteria = CriteriaUtil.idCriteria(id);
        return suspend(criteria, user);
    }

    @MethodStats
    default Mono<DTO> resume(C criteria, USER user) {
        return getRepository().resume(criteria)
                .then(getOne(criteria, user));
    }

    @MethodStats
    default Mono<DTO> resumeById(String id, USER user) {
        C criteria = CriteriaUtil.idCriteria(id);
        return resume(criteria, user);
    }

    @MethodStats
    default Mono<DTO> getOne(C criteria, USER user) {
        return getRepository().getOne(criteria)
                .map(d -> toDto(d, user));
    }

    @MethodStats
    default Mono<DTO> getById(String id, USER user) {
        C criteria = CriteriaUtil.idCriteria(id);
        return getOne(criteria, user);
    }

    @MethodStats
    default Mono<Long> count(C criteria, USER user) {
        return getRepository().count(criteria);
    }

    @MethodStats
    default Mono<Page<DTO>> getPage(C criteria, Pageable pageable, USER user) {
        return getRepository().getPage(criteria, pageable)
                .map(page -> {
                    List<DTO> dtos = page.getContent()
                            .stream()
                            .map(d -> toDto(d, user))
                            .toList();
                    return new PageImpl<>(dtos, pageable, page.getTotalElements());
                });
    }
}
