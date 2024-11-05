package ir.msob.jima.process.api.restful.service.rest.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.commons.criteria.ProcessCriteria;
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
import java.util.Optional;

public interface BaseStartProcessRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        PR extends BaseProcessRepository,
        S extends BaseProcessService<USER, PR>>
        extends ParentProcessRestResource<ID, USER, PR, S> {

    Logger log = LoggerFactory.getLogger(BaseStartProcessRestResource.class);

    @PostMapping(Operations.START)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(Operations.START)
    @MethodStats
    default ResponseEntity<Mono<ProcessDto>> start(ProcessCriteria criteria, @RequestBody ProcessDto dto, ServerWebExchange serverWebExchange, Principal principal) throws JsonProcessingException {
        log.debug("REST request to start process, criteria {}, dto {}", criteria, dto);

        /*
         * Init user data from request
         */
        Optional<USER> user = getUser(serverWebExchange, principal);

        return this.startResponse(this.getService().start(criteria, dto, user), user);
    }

    default ResponseEntity<Mono<ProcessDto>> startResponse(Mono<ProcessDto> result, Optional<USER> user) {
        return ResponseEntity.ok(result);
    }
}
