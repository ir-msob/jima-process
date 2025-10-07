package ir.msob.jima.process.api.restful.service.rest.deployment;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.DeploymentCriteria;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.service.BaseDeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseDeleteDeploymentRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        DR extends BaseDeploymentRepository,
        S extends BaseDeploymentService<USER, DR>>
        extends ParentDeploymentRestResource<ID, USER, DR, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteDeploymentRestResource.class);

    @DeleteMapping(Operations.DELETE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.DELETE)
    @MethodStats
    default ResponseEntity<Mono<String>> delete(DeploymentCriteria criteria, ServerWebExchange serverWebExchange, Principal principal) throws JsonProcessingException {
        log.debug("REST request to delete deployment, criteria {}", criteria);

        /*
         * Init user data from request
         */
        USER user = getUser(serverWebExchange, principal);

        return this.deleteResponse(this.getService().delete(criteria, user), user);
    }

    default ResponseEntity<Mono<String>> deleteResponse(Mono<String> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
