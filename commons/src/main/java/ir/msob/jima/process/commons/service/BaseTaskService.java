package ir.msob.jima.process.commons.service;

import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.process.commons.criteria.BaseTaskCriteria;
import ir.msob.jima.process.commons.domain.BaseTask;
import ir.msob.jima.process.commons.dto.BaseTaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BaseTaskService<USER extends BaseUser, D extends BaseTask, DTO extends BaseTaskDto, C extends BaseTaskCriteria, R extends BaseTaskRepository<D, C>> {

    R getRepository();

    DTO toDto(D domain, USER user);

    D toDomain(DTO dto, USER user);


    @MethodStats
    default Mono<DTO> save(DTO dto, USER user) {
        return getRepository().save(toDomain(dto, user))
                .map(d -> toDto(d, user));
    }

    @MethodStats
    default Mono<DTO> complete(C criteria, DTO dto, USER user) {
        return getRepository().complete(criteria, toDomain(dto, user))
                .map(d -> toDto(d, user));
    }

    @MethodStats
    default Mono<DTO> completeById(String id, DTO dto, USER user) {
        C criteria = CriteriaUtil.idCriteria(id);
        return complete(criteria, dto, user);
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
