package ir.msob.jima.process.api.restful.service.rest.deployment.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.api.restful.service.rest.deployment.ParentDeploymentRestResource;
import ir.msob.jima.process.commons.criteria.BaseDeploymentCriteria;
import ir.msob.jima.process.commons.domain.BaseDeployment;
import ir.msob.jima.process.commons.dto.BaseDeploymentDto;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.commons.service.BaseDeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BaseDeleteByIdDeploymentRestResource<
        USER extends BaseUser,
        D extends BaseDeployment,
        DTO extends BaseDeploymentDto,
        C extends BaseDeploymentCriteria,
        R extends BaseDeploymentRepository<D, C>,
        S extends BaseDeploymentService<USER, D, DTO, C, R>>
        extends ParentDeploymentRestResource<USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByIdDeploymentRestResource.class);

    @DeleteMapping("{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(operation = Operations.DELETE_BY_ID)
    default ResponseEntity<Mono<String>> deleteById(@PathVariable("id") String id, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete deployment by id, id {}", id);

        /*
         * Init user data from request
         */
        USER user = getUser(serverWebExchange, principal);

        return this.deleteByIdResponse(this.getService().deleteById(id, user), user);
    }

    default ResponseEntity<Mono<String>> deleteByIdResponse(Mono<String> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
