package ir.msob.jima.process.api.restful.service.rest.deployment;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.service.BaseDeploymentService;

import java.io.Serializable;

public interface BaseDeploymentRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        DR extends BaseDeploymentRepository,
        S extends BaseDeploymentService<USER, DR>>
        extends BaseDeleteDeploymentRestResource<ID, USER, DR, S>,
        BaseGetOneDeploymentRestResource<ID, USER, DR, S>,
        BaseCountDeploymentRestResource<ID, USER, DR, S>,
        BaseGetPageDeploymentRestResource<ID, USER, DR, S>,
        BaseSaveDeploymentRestResource<ID, USER, DR, S> {

}
