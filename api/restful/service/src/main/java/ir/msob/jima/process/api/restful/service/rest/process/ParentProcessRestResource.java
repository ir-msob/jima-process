package ir.msob.jima.process.api.restful.service.rest.process;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.BaseProcessCriteria;
import ir.msob.jima.process.commons.domain.BaseProcess;
import ir.msob.jima.process.commons.dto.BaseProcessDto;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.commons.service.BaseProcessService;

public interface ParentProcessRestResource<
        USER extends BaseUser,
        D extends BaseProcess,
        DTO extends BaseProcessDto,
        C extends BaseProcessCriteria,
        R extends BaseProcessRepository<D, C>,
        S extends BaseProcessService<USER, D, DTO, C, R>>
        extends BaseRestResource<String, USER> {

    S getService();
}
