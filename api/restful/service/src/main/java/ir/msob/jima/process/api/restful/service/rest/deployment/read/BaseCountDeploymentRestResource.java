package ir.msob.jima.process.api.restful.service.rest.deployment.read;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BaseCountDeploymentRestResource<
        USER extends BaseUser,
        D extends BaseDeployment,
        DTO extends BaseDeploymentDto,
        C extends BaseDeploymentCriteria,
        R extends BaseDeploymentRepository<D, C>,
        S extends BaseDeploymentService<USER, D, DTO, C, R>>
        extends ParentDeploymentRestResource<USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseCountDeploymentRestResource.class);

    @GetMapping(Operations.COUNT)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.COUNT)
    @MethodStats
    default ResponseEntity<Mono<Long>> count(C criteria, ServerWebExchange serverWebExchange, Principal principal) {
        log.debug("REST request to count deployment, criteria {}", criteria);

        /*
         * Init user data from request
         */
        USER user = getUser(serverWebExchange, principal);

        return this.countResponse(this.getService().count(criteria, user), user);
    }

    default ResponseEntity<Mono<Long>> countResponse(Mono<Long> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
