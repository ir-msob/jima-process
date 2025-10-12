package ir.msob.jima.process.api.restful.service.rest.task;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.api.restful.service.rest.task.read.BaseCountTaskRestResource;
import ir.msob.jima.process.api.restful.service.rest.task.read.BaseGetOneTaskRestResource;
import ir.msob.jima.process.api.restful.service.rest.task.read.BaseGetPageTaskRestResource;
import ir.msob.jima.process.api.restful.service.rest.task.write.BaseCompleteTaskRestResource;
import ir.msob.jima.process.api.restful.service.rest.task.write.BaseDeleteTaskRestResource;
import ir.msob.jima.process.api.restful.service.rest.task.write.BaseSaveTaskRestResource;
import ir.msob.jima.process.commons.criteria.BaseTaskCriteria;
import ir.msob.jima.process.commons.domain.BaseTask;
import ir.msob.jima.process.commons.dto.BaseTaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import ir.msob.jima.process.commons.service.BaseTaskService;

public interface BaseTaskRestResource<
        USER extends BaseUser,
        D extends BaseTask,
        DTO extends BaseTaskDto,
        C extends BaseTaskCriteria,
        R extends BaseTaskRepository<D, C>,
        S extends BaseTaskService<USER, D, DTO, C, R>>
        extends
        BaseCompleteTaskRestResource<USER, D, DTO, C, R, S>,
        BaseDeleteTaskRestResource<USER, D, DTO, C, R, S>,
        BaseGetOneTaskRestResource<USER, D, DTO, C, R, S>,
        BaseCountTaskRestResource<USER, D, DTO, C, R, S>,
        BaseGetPageTaskRestResource<USER, D, DTO, C, R, S>,
        BaseSaveTaskRestResource<USER, D, DTO, C, R, S> {
}
