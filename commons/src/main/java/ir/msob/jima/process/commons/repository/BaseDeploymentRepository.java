package ir.msob.jima.process.commons.repository;

import ir.msob.jima.process.commons.criteria.DeploymentCriteria;
import ir.msob.jima.process.commons.dto.DeploymentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.io.InputStream;

public interface BaseDeploymentRepository {
    Mono<DeploymentDto> save(DeploymentDto dto, InputStream inputStream);

    Mono<Page<DeploymentDto>> getPage(DeploymentCriteria criteria, Pageable pageable);

    Mono<DeploymentDto> getOne(DeploymentCriteria criteria);

    Mono<Long> count(DeploymentCriteria criteria);

    Mono<DeploymentDto> delete(DeploymentCriteria criteria);
}
