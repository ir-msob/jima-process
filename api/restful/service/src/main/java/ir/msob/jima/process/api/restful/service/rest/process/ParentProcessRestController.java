package ir.msob.jima.process.api.restful.service.rest.process;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.service.BaseProcessService;

import java.io.Serializable;

public interface ParentProcessRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        PR extends BaseProcessRepository,
        S extends BaseProcessService<ID, USER, PR>>
        extends BaseRestResource<ID, USER> {

    S getService();
}
