package ir.msob.jima.process.api.restful.service.rest.deployment;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.api.restful.service.rest.deployment.read.BaseCountDeploymentRestResource;
import ir.msob.jima.process.api.restful.service.rest.deployment.read.BaseGetOneDeploymentRestResource;
import ir.msob.jima.process.api.restful.service.rest.deployment.read.BaseGetPageDeploymentRestResource;
import ir.msob.jima.process.api.restful.service.rest.deployment.write.BaseDeleteDeploymentRestResource;
import ir.msob.jima.process.api.restful.service.rest.deployment.write.BaseSaveDeploymentRestResource;
import ir.msob.jima.process.commons.criteria.BaseDeploymentCriteria;
import ir.msob.jima.process.commons.domain.BaseDeployment;
import ir.msob.jima.process.commons.dto.BaseDeploymentDto;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.commons.service.BaseDeploymentService;

public interface BaseDeploymentRestResource<
        USER extends BaseUser,
        D extends BaseDeployment,
        DTO extends BaseDeploymentDto,
        C extends BaseDeploymentCriteria,
        R extends BaseDeploymentRepository<D, C>,
        S extends BaseDeploymentService<USER, D, DTO, C, R>>
        extends BaseDeleteDeploymentRestResource<USER, D, DTO, C, R, S>,
        BaseGetOneDeploymentRestResource<USER, D, DTO, C, R, S>,
        BaseCountDeploymentRestResource<USER, D, DTO, C, R, S>,
        BaseGetPageDeploymentRestResource<USER, D, DTO, C, R, S>,
        BaseSaveDeploymentRestResource<USER, D, DTO, C, R, S> {

}
