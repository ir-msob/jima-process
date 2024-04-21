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
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.service.BaseProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

public interface BaseCountProcessRestController<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        PR extends BaseProcessRepository,
        S extends BaseProcessService<ID, USER, PR>>
        extends ParentProcessRestController<ID, USER, PR, S> {

    Logger log = LoggerFactory.getLogger(BaseCountProcessRestController.class);

    @GetMapping(Operations.COUNT)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(Operations.COUNT)
    @MethodStats
    default ResponseEntity<Mono<Long>> count(ProcessCriteria criteria, ServerWebExchange serverWebExchange, Principal principal) throws JsonProcessingException {
        log.debug("REST request to count process, criteria {}", criteria);

        /*
         * Init user data from request
         */
        Optional<USER> user = getUser(serverWebExchange, principal);

        return this.countResponse(this.getService().count(criteria, user), user);
    }

    default ResponseEntity<Mono<Long>> countResponse(Mono<Long> result, Optional<USER> user) {
        return ResponseEntity.ok(result);
    }
}
