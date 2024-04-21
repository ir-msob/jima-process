package ir.msob.jima.process.api.restful.service.rest.process;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.service.BaseProcessService;

import java.io.Serializable;

public interface BaseProcessRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        PR extends BaseProcessRepository,
        S extends BaseProcessService<ID, USER, PR>>
        extends
        BaseSaveProcessRestController<ID, USER, PR, S>,
        BaseDeleteProcessRestController<ID, USER, PR, S>,
        BaseGetOneProcessRestController<ID, USER, PR, S>,
        BaseCountProcessRestController<ID, USER, PR, S>,
        BaseGetPageProcessRestController<ID, USER, PR, S>,
        BaseResumeProcessRestController<ID, USER, PR, S>,
        BaseStartProcessRestController<ID, USER, PR, S>,
        BaseSuspendProcessRestController<ID, USER, PR, S> {

}
