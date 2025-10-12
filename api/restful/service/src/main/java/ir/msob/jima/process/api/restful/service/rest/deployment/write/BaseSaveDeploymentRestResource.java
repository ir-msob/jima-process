package ir.msob.jima.process.api.restful.service.rest.deployment.write;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BaseSaveDeploymentRestResource<
        USER extends BaseUser,
        D extends BaseDeployment,
        DTO extends BaseDeploymentDto,
        C extends BaseDeploymentCriteria,
        R extends BaseDeploymentRepository<D, C>,
        S extends BaseDeploymentService<USER, D, DTO, C, R>>
        extends ParentDeploymentRestResource<USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseSaveDeploymentRestResource.class);

    @PostMapping(Operations.SAVE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.SAVE)
    @MethodStats
    default ResponseEntity<Mono<DTO>> save(@RequestBody DTO dto, ServerWebExchange serverWebExchange, Principal principal) {
        log.debug("REST request to save deployment, dto {}", dto);

        /*
         * Init user data from request
         */
        USER user = getUser(serverWebExchange, principal);

        return this.saveResponse(this.getService().save(dto, user), user);
    }

    default ResponseEntity<Mono<DTO>> saveResponse(Mono<DTO> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
