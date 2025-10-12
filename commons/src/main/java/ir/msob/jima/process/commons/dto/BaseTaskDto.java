package ir.msob.jima.process.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.process.commons.domain.BaseTask;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseTaskDto extends BaseTask {
    @Builder
    public BaseTaskDto(String id, String name, String localizedName, String description, String localizedDescription, int priority, String owner, String assignee, DelegationState delegationState, Instant dueDate, String category, String parentTaskId, String tenantId, String formKey, boolean suspended, Integer appVersion, Map<String, Object> taskVariables, Map<String, Object> processVariables) {
        super(id, name, localizedName, description, localizedDescription, priority, owner, assignee, delegationState, dueDate, category, parentTaskId, tenantId, formKey, suspended, appVersion, taskVariables, processVariables);
    }
}
