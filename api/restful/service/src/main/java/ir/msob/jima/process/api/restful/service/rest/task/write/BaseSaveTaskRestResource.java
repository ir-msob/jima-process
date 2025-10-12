package ir.msob.jima.process.api.restful.service.rest.task.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.api.restful.service.rest.task.ParentTaskRestResource;
import ir.msob.jima.process.commons.criteria.BaseTaskCriteria;
import ir.msob.jima.process.commons.domain.BaseTask;
import ir.msob.jima.process.commons.dto.BaseTaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import ir.msob.jima.process.commons.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BaseSaveTaskRestResource<
        USER extends BaseUser,
        D extends BaseTask,
        DTO extends BaseTaskDto,
        C extends BaseTaskCriteria,
        R extends BaseTaskRepository<D, C>,
        S extends BaseTaskService<USER, D, DTO, C, R>>
        extends ParentTaskRestResource<USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseSaveTaskRestResource.class);

    @PostMapping(Operations.SAVE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.SAVE)
    @MethodStats
    default ResponseEntity<Mono<DTO>> save(@RequestBody DTO dto, Principal principal) {
        log.debug("REST request to save task, dto {}", dto);

        /*
         * Init user data from request
         */
        USER user = getUser(principal);

        return this.saveResponse(this.getService().save(dto, user), user);
    }

    default ResponseEntity<Mono<DTO>> saveResponse(Mono<DTO> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
