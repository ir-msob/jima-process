package ir.msob.jima.process.api.restful.service.rest.task;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import ir.msob.jima.process.service.BaseTaskService;

import java.io.Serializable;

public interface BaseTaskRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        TR extends BaseTaskRepository,
        S extends BaseTaskService<USER, TR>>
        extends
        BaseCompleteTaskRestController<ID, USER, TR, S>,
        BaseDeleteTaskRestController<ID, USER, TR, S>,
        BaseGetOneTaskRestController<ID, USER, TR, S>,
        BaseCountTaskRestController<ID, USER, TR, S>,
        BaseGetPageTaskRestController<ID, USER, TR, S>,
        BaseSaveTaskRestController<ID, USER, TR, S> {
}
