package ir.msob.jima.process.api.restful.service.rest.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.dto.ProcessDto;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.service.BaseProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseSaveProcessRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        PR extends BaseProcessRepository,
        S extends BaseProcessService<USER, PR>>
        extends ParentProcessRestResource<ID, USER, PR, S> {

    Logger log = LoggerFactory.getLogger(BaseSaveProcessRestResource.class);

    @PostMapping(Operations.SAVE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.SAVE)
    @MethodStats
    default ResponseEntity<Mono<ProcessDto>> save(@RequestBody ProcessDto dto, ServerWebExchange serverWebExchange, Principal principal) throws JsonProcessingException {
        log.debug("REST request to save process, dto {}", dto);

        /*
         * Init user data from request
         */
        USER user = getUser(serverWebExchange, principal);

        return this.saveResponse(this.getService().save(dto, user), user);
    }

    default ResponseEntity<Mono<ProcessDto>> saveResponse(Mono<ProcessDto> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
