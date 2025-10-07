package ir.msob.jima.process.api.restful.service.rest.task;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import ir.msob.jima.process.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseDeleteByIdTaskRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        TR extends BaseTaskRepository,
        S extends BaseTaskService<USER, TR>>
        extends ParentTaskRestResource<ID, USER, TR, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByIdTaskRestResource.class);

    @DeleteMapping("{id}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.DELETE_BY_ID)
    @MethodStats
    default ResponseEntity<Mono<String>> deleteById(@PathVariable("id") String id, Principal principal) {
        log.debug("REST request to delete task, id {}", id);

        /*
         * Init user data from request
         */
        USER user = getUser(principal);

        return this.deleteResponse(this.getService().deleteById(id, user), user);
    }

    default ResponseEntity<Mono<String>> deleteResponse(Mono<String> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
