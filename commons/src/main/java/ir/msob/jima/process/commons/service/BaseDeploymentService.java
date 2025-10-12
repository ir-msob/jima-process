package ir.msob.jima.process.commons.service;

import ir.msob.jima.core.commons.file.BaseFileClient;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.process.commons.criteria.BaseDeploymentCriteria;
import ir.msob.jima.process.commons.domain.BaseDeployment;
import ir.msob.jima.process.commons.dto.BaseDeploymentDto;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BaseDeploymentService<USER extends BaseUser, D extends BaseDeployment, DTO extends BaseDeploymentDto, C extends BaseDeploymentCriteria, R extends BaseDeploymentRepository<D, C>> {

    BaseFileClient getFileClient();

    R getRepository();

    DTO toDto(D domain, USER user);

    D toDomain(DTO dto, USER user);

    @MethodStats
    default Mono<DTO> save(DTO dto, USER user) {
        return getFileClient().get(dto.getFilePath(), user)
                .flatMap(inputStream -> getRepository().save(toDomain(dto, user), inputStream))
                .map(d -> toDto(d, user));
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

    @MethodStats
    default Mono<DTO> getOne(C criteria, USER user) {
        return getRepository().getOne(criteria)
                .map(d -> toDto(d, user));
    }

    @MethodStats
    default Mono<DTO> getById(String id, USER user) {
        C criteria = CriteriaUtil.idCriteria(id);
        return this.getOne(criteria, user);
    }

    @MethodStats
    default Mono<Long> count(C criteria, USER user) {
        return getRepository().count(criteria);
    }

    @MethodStats
    default Mono<String> delete(C criteria, USER user) {
        return getRepository().getOne(criteria)
                .flatMap(dto -> getFileClient().delete(dto.getFilePath(), user))
                .then(getRepository().delete(criteria))
                .map(BaseDeployment::getId);
    }

    @MethodStats
    default Mono<String> deleteById(String id, USER user) {
        C criteria = CriteriaUtil.idCriteria(id);
        return this.delete(criteria, user);
    }
}
