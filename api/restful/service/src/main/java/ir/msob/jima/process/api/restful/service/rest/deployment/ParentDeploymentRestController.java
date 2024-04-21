package ir.msob.jima.process.api.restful.service.rest.deployment;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.service.BaseDeploymentService;

import java.io.Serializable;

public interface ParentDeploymentRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        DR extends BaseDeploymentRepository,
        S extends BaseDeploymentService<ID, USER, DR>>
        extends BaseRestResource<ID, USER> {

    S getService();
}
