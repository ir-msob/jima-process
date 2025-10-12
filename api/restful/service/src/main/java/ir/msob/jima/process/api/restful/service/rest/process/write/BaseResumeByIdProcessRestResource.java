package ir.msob.jima.process.api.restful.service.rest.process.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.api.restful.service.rest.process.ParentProcessRestResource;
import ir.msob.jima.process.commons.criteria.BaseProcessCriteria;
import ir.msob.jima.process.commons.domain.BaseProcess;
import ir.msob.jima.process.commons.dto.BaseProcessDto;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.commons.service.BaseProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BaseResumeByIdProcessRestResource<
        USER extends BaseUser,
        D extends BaseProcess,
        DTO extends BaseProcessDto,
        C extends BaseProcessCriteria,
        R extends BaseProcessRepository<D, C>,
        S extends BaseProcessService<USER, D, DTO, C, R>>
        extends ParentProcessRestResource<USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseResumeByIdProcessRestResource.class);

    @PostMapping("{id}/resume")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.RESUME_BY_ID)
    @MethodStats
    default ResponseEntity<Mono<DTO>> resumeById(@PathVariable("id") String id, ServerWebExchange serverWebExchange, Principal principal) {
        log.debug("REST request to resume process by id, id {}", id);

        /*
         * Init user data from request
         */
        USER user = getUser(serverWebExchange, principal);

        return this.resumeByIdResponse(this.getService().resumeById(id, user), user);
    }

    default ResponseEntity<Mono<DTO>> resumeByIdResponse(Mono<DTO> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
