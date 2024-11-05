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
                .variables(dto.getProcessVariables())
                .transientVariables(dto.getTransientVariables())
                .messageName(dto.getMessageName())
                .start();
        return Mono.just(prepareProcessDto(processInstance));
    }


    @SneakyThrows
    @Override
    public Mono<ProcessDto> start(ProcessCriteria criteria, ProcessDto dto) {
        ProcessInstance processInstance = null;
        if (criteria.getId() != null) {
            processInstance = runtimeService.startProcessInstanceById(criteria.getId().getEq(), dto.getProcessVariables());
        } else if (criteria.getKey() != null) {
            processInstance = runtimeService.startProcessInstanceByKey(criteria.getKey().getEq(), dto.getProcessVariables());
        } else {
            BadRequestException.init("Cannot fount any deployment id or key {}", dto);
        }
        return Mono.just(prepareProcessDto(processInstance));
    }

    @Override
    public Mono<String> delete(ProcessCriteria criteria) {
        runtimeService.deleteProcessInstance(criteria.getId().getEq(), null);
        return Mono.just(criteria.getId().getEq());
    }

    @Override
    public Mono<Boolean> suspend(ProcessCriteria criteria) {
        runtimeService.suspendProcessInstanceById(criteria.getId().getEq());
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> resume(ProcessCriteria criteria) {
        runtimeService.activateProcessInstanceById(criteria.getId().getEq());
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
        if (criteria != null) {
            if (criteria.getId() != null) {
                if (criteria.getId().getEq() != null)
                    processInstanceQuery.processInstanceId(criteria.getId().getEq());
                else if (criteria.getId().getIn() != null) {
                    processInstanceQuery.processInstanceIds(criteria.getId().getIn());
                }
            }
            if (criteria.getSuspended() != null) {
                if (criteria.getSuspended().getEq()) {
                    processInstanceQuery.suspended();
                }
            }
            if (criteria.getName() != null)
                if (criteria.getName().getEq() != null) {
                    processInstanceQuery.processInstanceName(criteria.getName().getEq());
                } else if (criteria.getName().getRegex() != null) {
                    processInstanceQuery.processInstanceNameLike(criteria.getName().getRegex());
                }
            if (criteria.getKey() != null) {
                if (criteria.getKey().getEq() != null)
                    processInstanceQuery.processDefinitionKey(criteria.getKey().getEq());
                else if (criteria.getKey().getIn() != null) {
                    processInstanceQuery.processDefinitionKeys(criteria.getKey().getIn());
                }
            }
            if (criteria.getVersion() != null)
                processInstanceQuery.processDefinitionVersion(criteria.getVersion().getEq());
            if (criteria.getDeploymentId() != null) {
                if (criteria.getDeploymentId().getEq() != null)
                    processInstanceQuery.deploymentId(criteria.getDeploymentId().getEq());
                else if (criteria.getDeploymentId().getIn() != null) {
                    processInstanceQuery.deploymentIdIn(criteria.getDeploymentId().getIn().stream().toList());
                }
            }
            if (criteria.getBusinessKey() != null)
                processInstanceQuery.processInstanceBusinessKey(criteria.getBusinessKey().getEq());
            if (criteria.getTenantId() != null) {
                if (criteria.getTenantId().getEq() != null)
                    processInstanceQuery.processInstanceTenantId(criteria.getTenantId().getEq());
                else if (criteria.getTenantId().getIn() != null) {
                    processInstanceQuery.processInstanceTenantIdLike(criteria.getTenantId().getRegex());
                }
            }
            if (criteria.getStartUserId() != null)
                processInstanceQuery.involvedUser(criteria.getStartUserId().getEq());
        }
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
        processDto.setProcessVariables(processInstance.getProcessVariables());
        processDto.setTenantId(processInstance.getTenantId());
        processDto.setLocalizedName(processInstance.getLocalizedName());
        processDto.setLocalizedDescription(processInstance.getLocalizedDescription());
        processDto.setStartDate(processInstance.getStartTime().toInstant());
        processDto.setStartUserId(processInstance.getStartUserId());
        processDto.setAppVersion(processInstance.getAppVersion());
        return processDto;
    }
}
