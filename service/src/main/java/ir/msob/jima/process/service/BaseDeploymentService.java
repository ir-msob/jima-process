package ir.msob.jima.process.service;

import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.file.BaseFileClient;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.DeploymentCriteria;
import ir.msob.jima.process.commons.dto.DeploymentDto;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BaseDeploymentService<USER extends BaseUser, DR extends BaseDeploymentRepository> {

    BaseFileClient getFileClient();

    DR getDeploymentRepository();

    @MethodStats
    default Mono<DeploymentDto> save(DeploymentDto dto, USER user) {
        return getFileClient().get(dto.getFilePath(), user)
                .flatMap(inputStream -> getDeploymentRepository().save(dto, inputStream));
    }

    @MethodStats
    default Mono<Page<DeploymentDto>> getPage(DeploymentCriteria criteria, Pageable pageable, USER user) {
        return getDeploymentRepository().getPage(criteria, pageable);
    }

    @MethodStats
    default Mono<DeploymentDto> getOne(DeploymentCriteria criteria, USER user) {
        return getDeploymentRepository().getOne(criteria);
    }

    @MethodStats
    default Mono<DeploymentDto> getById(String id, USER user) {
        DeploymentCriteria criteria = DeploymentCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return this.getOne(criteria, user);
    }

    @MethodStats
    default Mono<Long> count(DeploymentCriteria criteria, USER user) {
        return getDeploymentRepository().count(criteria);
    }

    @MethodStats
    default Mono<String> delete(DeploymentCriteria criteria, USER user) {
        return getDeploymentRepository().getOne(criteria)
                .flatMap(deploymentDto -> getFileClient().delete(deploymentDto.getFilePath(), user))
                .then(getDeploymentRepository().delete(criteria))
                .map(DeploymentDto::getId);
    }

    @MethodStats
    default Mono<String> deleteById(String id, USER user) {
        DeploymentCriteria criteria = DeploymentCriteria.builder()
                .id(Filter.eq(id))
                .build();
        return this.delete(criteria, user);
    }
}
