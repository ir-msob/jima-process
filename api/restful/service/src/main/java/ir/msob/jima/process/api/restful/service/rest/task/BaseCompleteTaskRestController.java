package ir.msob.jima.process.api.restful.service.rest.task;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.TaskCriteria;
import ir.msob.jima.process.commons.dto.TaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import ir.msob.jima.process.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

public interface BaseCompleteTaskRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        TR extends BaseTaskRepository,
        S extends BaseTaskService<ID, USER, TR>>
        extends ParentTaskRestController<ID, USER, TR, S> {

    Logger log = LoggerFactory.getLogger(BaseCompleteTaskRestController.class);

    @PostMapping(Operations.COMPLETE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(Operations.COMPLETE)
    @MethodStats
    default ResponseEntity<Mono<TaskDto>> complete(TaskCriteria criteria, Principal principal) {
        log.debug("REST request to complete task, criteria {}", criteria);

        /*
         * Init user data from request
         */
        Optional<USER> user = getUser(principal);

        return this.completeResponse(this.getService().complete(criteria, user), user);
    }

    default ResponseEntity<Mono<TaskDto>> completeResponse(Mono<TaskDto> result, Optional<USER> user) {
        return ResponseEntity.ok(result);
    }
}
