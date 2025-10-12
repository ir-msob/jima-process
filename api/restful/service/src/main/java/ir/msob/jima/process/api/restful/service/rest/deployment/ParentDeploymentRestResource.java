package ir.msob.jima.process.api.restful.service.rest.deployment;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.BaseDeploymentCriteria;
import ir.msob.jima.process.commons.domain.BaseDeployment;
import ir.msob.jima.process.commons.dto.BaseDeploymentDto;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.commons.service.BaseDeploymentService;

public interface ParentDeploymentRestResource<
        USER extends BaseUser,
        D extends BaseDeployment,
        DTO extends BaseDeploymentDto,
        C extends BaseDeploymentCriteria,
        R extends BaseDeploymentRepository<D, C>,
        S extends BaseDeploymentService<USER, D, DTO, C, R>>
        extends BaseRestResource<String, USER> {

    S getService();
}
