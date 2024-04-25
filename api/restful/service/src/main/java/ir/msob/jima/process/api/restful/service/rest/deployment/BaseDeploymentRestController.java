package ir.msob.jima.process.api.restful.service.rest.deployment;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.service.BaseDeploymentService;

import java.io.Serializable;

public interface BaseDeploymentRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        DR extends BaseDeploymentRepository,
        S extends BaseDeploymentService<USER, DR>>
        extends BaseDeleteDeploymentRestController<ID, USER, DR, S>,
        BaseGetOneDeploymentRestController<ID, USER, DR, S>,
        BaseCountDeploymentRestController<ID, USER, DR, S>,
        BaseGetPageDeploymentRestController<ID, USER, DR, S>,
        BaseSaveDeploymentRestController<ID, USER, DR, S> {

}
