package ir.msob.jima.process.ral.activiti.beans;

import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.process.commons.criteria.ProcessCriteria;
import ir.msob.jima.process.commons.dto.ProcessDto;
import ir.msob.jima.process.commons.repository.BaseProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessRepository implements BaseProcessRepository {

    private final RuntimeService runtimeService;

    @SneakyThrows
    @Override
    public Mono<ProcessDto> save(ProcessDto dto) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                .name(dto.getName())
                .processDefinitionId(dto.getId())
                .processDefinitionKey(dto.getKey())
                .businessKey(dto.getBusinessKey())
                .tenantId(dto.getTenantId())
                .variables(dto.getVariables())
                .transientVariables(dto.getTransientVariables())
                .messageName(dto.getMessageName())
                .start();
        return Mono.just(prepareProcessDto(processInstance));
    }

//    private void duplicateCheck(ProcessDto dto) throws DuplicateException {
//        if (dto.isDuplicateCheck()) {
//            ProcessCriteria criteria = new ProcessCriteria();
//            criteria.setKey(dto.getKey());
//            criteria.setBusinessKey(dto.getBusinessKey());
//
//            ProcessInstanceQuery processInstanceQuery = prepareProcessInstanceQuery(criteria);
//            long count = processInstanceQuery.count();
//            if (count > 0) {
//                DuplicateException.init("A process has already been executed for these key {} and businessKey {}.", dto.getKey(), dto.getBusinessKey());
//            }
//        }
//    }

    @SneakyThrows
    @Override
    public Mono<ProcessDto> start(ProcessDto dto) {
        ProcessInstance processInstance = null;
        if (Strings.isNotBlank(dto.getId())) {
            processInstance = runtimeService.startProcessInstanceById(dto.getId(), dto.getVariables());
        } else if (Strings.isNotBlank(dto.getKey())) {
            processInstance = runtimeService.startProcessInstanceByKey(dto.getKey(), dto.getVariables());
        } else {
            BadRequestException.init("Cannot fount any deployment id or key {}", dto);
        }
        return Mono.just(prepareProcessDto(processInstance));
    }

    @Override
    public Mono<Boolean> delete(ProcessCriteria criteria) {
        runtimeService.deleteProcessInstance(criteria.getId(), null);
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> suspend(ProcessCriteria criteria) {
        runtimeService.suspendProcessInstanceById(criteria.getId());
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> resume(ProcessCriteria criteria) {
        runtimeService.activateProcessInstanceById(criteria.getId());
        return Mono.just(true);
    }

    @Override
    public Mono<ProcessDto> getOne(ProcessCriteria criteria) {
        ProcessInstanceQuery processInstanceQuery = prepareProcessInstanceQuery(criteria);
        return Mono.just(prepareProcessDto(processInstanceQuery.singleResult()));
    }

    @Override
    public Mono<Long> count(ProcessCriteria criteria) {
        ProcessInstanceQuery processInstanceQuery = prepareProcessInstanceQuery(criteria);
        return Mono.just(processInstanceQuery.count());
    }

    @Override
    public Mono<Page<ProcessDto>> getPage(ProcessCriteria criteria, Pageable pageable) {
        long count = prepareProcessInstanceQuery(criteria).count();
        if (count < 1) {
            return Mono.just(new PageImpl<>(new ArrayList<>(), pageable, 0L));
        } else {
            Collection<ProcessInstance> processInstances = prepareProcessInstanceQuery(criteria).listPage(pageable.getPageNumber(), pageable.getPageSize());
            return Mono.just(new PageImpl<>(prepareProcessDto(processInstances), pageable, count));
        }
    }

    private ProcessInstanceQuery prepareProcessInstanceQuery(ProcessCriteria criteria) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        if (Strings.isNotBlank(criteria.getId()))
            processInstanceQuery.processInstanceId(criteria.getId());
        if (criteria.isSuspended())
            processInstanceQuery.suspended();
        if (Strings.isNotBlank(criteria.getName()))
            processInstanceQuery.processInstanceName(criteria.getName());
        if (Strings.isNotBlank(criteria.getKey()))
            processInstanceQuery.processDefinitionKey(criteria.getKey());
        if (criteria.getVersion() != null)
            processInstanceQuery.processDefinitionVersion(criteria.getVersion());
        if (Strings.isNotBlank(criteria.getDeploymentId()))
            processInstanceQuery.deploymentId(criteria.getDeploymentId());
        if (Strings.isNotBlank(criteria.getBusinessKey()))
            processInstanceQuery.processInstanceBusinessKey(criteria.getBusinessKey());
        if (Strings.isNotBlank(criteria.getTenantId()))
            processInstanceQuery.processInstanceTenantId(criteria.getTenantId());
        if (Strings.isNotBlank(criteria.getStartUserId()))
            processInstanceQuery.involvedUser(criteria.getStartUserId());
        return processInstanceQuery;
    }

    private List<ProcessDto> prepareProcessDto(Collection<ProcessInstance> processInstances) {
        return processInstances.stream().map(this::prepareProcessDto).toList();
    }

    private ProcessDto prepareProcessDto(ProcessInstance processInstance) {
        ProcessDto processDto = new ProcessDto();
        processDto.setId(processInstance.getProcessDefinitionId());
        processDto.setName(processInstance.getProcessDefinitionName());
        processDto.setDescription(processInstance.getDescription());
        processDto.setKey(processInstance.getProcessDefinitionKey());
        processDto.setVersion(processInstance.getProcessDefinitionVersion());
        processDto.setDeploymentId(processInstance.getDeploymentId());
        processDto.setBusinessKey(processInstance.getBusinessKey());
        processDto.setSuspended(processInstance.isSuspended());
        processDto.setVariables(processInstance.getProcessVariables());
        processDto.setTenantId(processInstance.getTenantId());
        processDto.setLocalizedName(processInstance.getLocalizedName());
        processDto.setLocalizedDescription(processInstance.getLocalizedDescription());
        processDto.setStartDate(processInstance.getStartTime().toInstant());
        processDto.setStartUserId(processInstance.getStartUserId());
        processDto.setAppVersion(processInstance.getAppVersion());
        return processDto;
    }
}
