package ir.msob.jima.process.api.restful.service.rest.deployment;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.DeploymentCriteria;
import ir.msob.jima.process.commons.dto.DeploymentDto;
import ir.msob.jima.process.commons.repository.BaseDeploymentRepository;
import ir.msob.jima.process.service.BaseDeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

public interface BaseGetOneDeploymentRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        DR extends BaseDeploymentRepository,
        S extends BaseDeploymentService<ID, USER, DR>>
        extends ParentDeploymentRestController<ID, USER, DR, S> {

    Logger log = LoggerFactory.getLogger(BaseGetOneDeploymentRestController.class);

    @GetMapping(Operations.GET_ONE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(Operations.GET_ONE)
    @MethodStats
    default ResponseEntity<Mono<DeploymentDto>> getOne(DeploymentCriteria criteria, ServerWebExchange serverWebExchange, Principal principal) throws JsonProcessingException {
        log.debug("REST request to get one deployment, criteria {}", criteria);

        /*
         * Init user data from request
         */
        Optional<USER> user = getUser(serverWebExchange, principal);

        return this.getOneResponse(this.getService().getOne(criteria, user), user);
    }

    default ResponseEntity<Mono<DeploymentDto>> getOneResponse(Mono<DeploymentDto> result, Optional<USER> user) {
        return ResponseEntity.ok(result);
    }
}
