package ir.msob.jima.process.api.restful.service.rest.task.read;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.process.api.restful.service.rest.task.ParentTaskRestResource;
import ir.msob.jima.process.commons.criteria.BaseTaskCriteria;
import ir.msob.jima.process.commons.domain.BaseTask;
import ir.msob.jima.process.commons.dto.BaseTaskDto;
import ir.msob.jima.process.commons.repository.BaseTaskRepository;
import ir.msob.jima.process.commons.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BaseGetPageTaskRestResource<
        USER extends BaseUser,
        D extends BaseTask,
        DTO extends BaseTaskDto,
        C extends BaseTaskCriteria,
        R extends BaseTaskRepository<D, C>,
        S extends BaseTaskService<USER, D, DTO, C, R>>
        extends ParentTaskRestResource<USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetPageTaskRestResource.class);

    @GetMapping(Operations.GET_PAGE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @Scope(operation = Operations.GET_PAGE)
    @MethodStats
    default ResponseEntity<Mono<Page<DTO>>> getPage(C criteria, Pageable pageable, Principal principal) {
        log.debug("REST request to get page task, criteria {}", criteria);

        /*
         * Init user data from request
         */
        USER user = getUser(principal);

        return this.getPageResponse(this.getService().getPage(criteria, pageable, user), user);
    }

    default ResponseEntity<Mono<Page<DTO>>> getPageResponse(Mono<Page<DTO>> result, USER user) {
        return ResponseEntity.ok(result);
    }
}
