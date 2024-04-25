package ir.msob.jima.process.api.restful.service.rest.task;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import ir.msob.jima.process.service.BaseTaskService;

import java.io.Serializable;

public interface ParentTaskRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        TR extends BaseTaskRepository,
        S extends BaseTaskService<USER, TR>>
        extends BaseRestResource<ID, USER> {

    S getService();
}
