package ir.msob.jima.process.api.restful.service.rest.process;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.api.restful.service.rest.process.read.BaseCountProcessRestResource;
import ir.msob.jima.process.api.restful.service.rest.process.read.BaseGetOneProcessRestResource;
import ir.msob.jima.process.api.restful.service.rest.process.read.BaseGetPageProcessRestResource;
import ir.msob.jima.process.api.restful.service.rest.process.write.*;
import ir.msob.jima.process.commons.criteria.BaseProcessCriteria;
import ir.msob.jima.process.commons.domain.BaseProcess;
import ir.msob.jima.process.commons.dto.BaseProcessDto;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.commons.service.BaseProcessService;

public interface BaseProcessRestResource<
        USER extends BaseUser,
        D extends BaseProcess,
        DTO extends BaseProcessDto,
        C extends BaseProcessCriteria,
        R extends BaseProcessRepository<D, C>,
        S extends BaseProcessService<USER, D, DTO, C, R>>
        extends
        BaseSaveProcessRestResource<USER, D, DTO, C, R, S>,
        BaseDeleteProcessRestResource<USER, D, DTO, C, R, S>,
        BaseGetOneProcessRestResource<USER, D, DTO, C, R, S>,
        BaseCountProcessRestResource<USER, D, DTO, C, R, S>,
        BaseGetPageProcessRestResource<USER, D, DTO, C, R, S>,
        BaseResumeProcessRestResource<USER, D, DTO, C, R, S>,
        BaseStartProcessRestResource<USER, D, DTO, C, R, S>,
        BaseSuspendProcessRestResource<USER, D, DTO, C, R, S> {

}
