package ir.msob.jima.process.ral.activiti.beans.repository;

import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.process.commons.criteria.BaseProcessCriteria;
import ir.msob.jima.process.commons.domain.BaseProcess;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import ir.msob.jima.process.ral.activiti.beans.query.ProcessInstanceQueryBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
public abstract class BaseActivitiProcessRepository<D extends BaseProcess, C extends BaseProcessCriteria> implements BaseProcessRepository<D, C> {

    private final RuntimeService runtimeService;
    private final ProcessInstanceQueryBuilder queryBuilder;

    public BaseQueryBuilder getQueryBuilder() {
        return this.queryBuilder;
    }

    @SneakyThrows
    @Override
    public Mono<D> save(D dto) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                .name(dto.getName())
                .processDefinitionId(dto.getId())
                .processDefinitionKey(dto.getKey())
                .businessKey(dto.getBusinessKey())
                .tenantId(dto.getTenantId())
                .variables(dto.getProcessVariables())
                .transientVariables(dto.getTransientVariables())
                .messageName(dto.getMessageName())
                .start();
        return Mono.just(prepareDomain(processInstance));
    }


    @SneakyThrows
    @Override
    public Mono<D> start(C criteria, D dto) {
        ProcessInstance processInstance = null;
        if (criteria.getId() != null) {
            processInstance = runtimeService.startProcessInstanceById(criteria.getId().getEq(), dto.getProcessVariables());
        } else if (criteria.getKey() != null) {
            processInstance = runtimeService.startProcessInstanceByKey(criteria.getKey().getEq(), dto.getProcessVariables());
        } else {
            BadRequestException.init("Cannot fount any deployment id or key {}", dto);
        }
        return Mono.just(prepareDomain(processInstance));
    }

    @Override
    public Mono<String> delete(C criteria) {
        runtimeService.deleteProcessInstance(criteria.getId().getEq(), null);
        return Mono.just(criteria.getId().getEq());
    }

    @Override
    public Mono<Boolean> suspend(C criteria) {
        runtimeService.suspendProcessInstanceById(criteria.getId().getEq());
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> resume(C criteria) {
        runtimeService.activateProcessInstanceById(criteria.getId().getEq());
        return Mono.just(true);
    }

    @Override
    public Mono<D> getOne(C criteria) {
        ProcessInstanceQuery processInstanceQuery = getQueryBuilder().build(criteria);
        return Mono.just(prepareDomain(processInstanceQuery.singleResult()));
    }

    @Override
    public Mono<Long> count(C criteria) {
        ProcessInstanceQuery processInstanceQuery = getQueryBuilder().build(criteria);
        return Mono.just(processInstanceQuery.count());
    }

    @Override
    public Mono<Page<D>> getPage(C criteria, Pageable pageable) {
        ProcessInstanceQuery processInstanceQuery = getQueryBuilder().build(criteria);
        long count = processInstanceQuery.count();
        if (count < 1) {
            return Mono.just(new PageImpl<>(new ArrayList<>(), pageable, 0L));
        } else {
            Collection<ProcessInstance> processInstances = processInstanceQuery.listPage(pageable.getPageNumber(), pageable.getPageSize());
            return Mono.just(new PageImpl<>(prepareDomain(processInstances), pageable, count));
        }
    }

    private List<D> prepareDomain(Collection<ProcessInstance> processInstances) {
        return processInstances.stream().map(this::prepareDomain).toList();
    }

    private D prepareDomain(ProcessInstance processInstance) {
        D domain = (D) new BaseProcess();
        domain.setId(processInstance.getProcessDefinitionId());
        domain.setName(processInstance.getProcessDefinitionName());
        domain.setDescription(processInstance.getDescription());
        domain.setKey(processInstance.getProcessDefinitionKey());
        domain.setVersion(processInstance.getProcessDefinitionVersion());
        domain.setDeploymentId(processInstance.getDeploymentId());
        domain.setBusinessKey(processInstance.getBusinessKey());
        domain.setSuspended(processInstance.isSuspended());
        domain.setProcessVariables(processInstance.getProcessVariables());
        domain.setTenantId(processInstance.getTenantId());
        domain.setLocalizedName(processInstance.getLocalizedName());
        domain.setLocalizedDescription(processInstance.getLocalizedDescription());
        domain.setStartDate(processInstance.getStartTime().toInstant());
        domain.setStartUserId(processInstance.getStartUserId());
        domain.setAppVersion(processInstance.getAppVersion());
        return domain;
    }
}
