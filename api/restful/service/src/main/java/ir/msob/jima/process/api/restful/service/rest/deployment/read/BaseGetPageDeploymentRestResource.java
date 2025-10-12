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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BaseGetPageDeploymentRestResource<
        USER extends BaseUser,
        D extends BaseDeployment,
        DTO extends BaseDeploymentDto,
        C extends BaseDeploymentCriteria,
        R extends BaseDeploymentRepository<D, C>,
        S extends BaseDeploymentService<USER, D, DTO, C, R>>
        extends ParentDeploymentRestResource<USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetPageDeploymentRestResource.class);

    @GetMapping(Operations.GET_PAGE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.GET_PAGE)
    @MethodStats
    default ResponseEntity<Mono<Page<DTO>>> getPage(C criteria, Pageable pageable, ServerWebExchange serverWebExchange, Principal principal) {
        log.debug("REST request to get one deployment, criteria {}", criteria);

        /*
         * Init user data from request
         */
        USER user = getUser(serverWebExchange, principal);

        return this.getPageResponse(this.getService().getPage(criteria, pageable, user), user);
    }

    default ResponseEntity<Mono<Page<DTO>>> getPageResponse(Mono<Page<DTO>> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
