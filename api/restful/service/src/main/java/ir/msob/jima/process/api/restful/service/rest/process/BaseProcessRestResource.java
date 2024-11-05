package ir.msob.jima.process.api.restful.service.rest.process;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.service.BaseProcessService;

import java.io.Serializable;

public interface BaseProcessRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        PR extends BaseProcessRepository,
        S extends BaseProcessService<USER, PR>>
        extends
        BaseSaveProcessRestResource<ID, USER, PR, S>,
        BaseDeleteProcessRestResource<ID, USER, PR, S>,
        BaseGetOneProcessRestResource<ID, USER, PR, S>,
        BaseCountProcessRestResource<ID, USER, PR, S>,
        BaseGetPageProcessRestResource<ID, USER, PR, S>,
        BaseResumeProcessRestResource<ID, USER, PR, S>,
        BaseStartProcessRestResource<ID, USER, PR, S>,
        BaseSuspendProcessRestResource<ID, USER, PR, S> {

}
