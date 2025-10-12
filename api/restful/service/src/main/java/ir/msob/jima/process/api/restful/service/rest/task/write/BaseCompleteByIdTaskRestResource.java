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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BaseCompleteByIdTaskRestResource<
        USER extends BaseUser,
        D extends BaseTask,
        DTO extends BaseTaskDto,
        C extends BaseTaskCriteria,
        R extends BaseTaskRepository<D, C>,
        S extends BaseTaskService<USER, D, DTO, C, R>>
        extends ParentTaskRestResource<USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseCompleteByIdTaskRestResource.class);

    @PostMapping("{id}/complete")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.COMPLETE_BY_ID)
    @MethodStats
    default ResponseEntity<Mono<DTO>> completeById(@PathVariable("id") String id, DTO dto, Principal principal) {
        log.debug("REST request to complete task, id {}, dto {}", id, dto);

        /*
         * Init user data from request
         */
        USER user = getUser(principal);

        return this.completeByIdResponse(this.getService().completeById(id, dto, user), user);
    }

    default ResponseEntity<Mono<DTO>> completeByIdResponse(Mono<DTO> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
