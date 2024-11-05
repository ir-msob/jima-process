package ir.msob.jima.process.api.restful.service.rest.task;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import ir.msob.jima.process.service.BaseTaskService;

import java.io.Serializable;

public interface BaseTaskRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        TR extends BaseTaskRepository,
        S extends BaseTaskService<USER, TR>>
        extends
        BaseCompleteTaskRestResource<ID, USER, TR, S>,
        BaseDeleteTaskRestResource<ID, USER, TR, S>,
        BaseGetOneTaskRestResource<ID, USER, TR, S>,
        BaseCountTaskRestResource<ID, USER, TR, S>,
        BaseGetPageTaskRestResource<ID, USER, TR, S>,
        BaseSaveTaskRestResource<ID, USER, TR, S> {
}
