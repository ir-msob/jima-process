package ir.msob.jima.process.commons.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.domain.BaseDomainAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseTask extends BaseDomainAbstract<String> {
    private String name;
    private String localizedName;
    private String description;
    private String localizedDescription;
    private int priority;
    private String owner;
    private String assignee;
    private DelegationState delegationState;
    private Instant dueDate;
    private String category;
    private String parentTaskId;
    private String tenantId;
    private String formKey;
    private boolean suspended;
    private Integer appVersion;
    private Map<String, Object> taskVariables;
    private Map<String, Object> processVariables;

    public BaseTask(String id, String name, String localizedName, String description, String localizedDescription, int priority, String owner, String assignee, DelegationState delegationState, Instant dueDate, String category, String parentTaskId, String tenantId, String formKey, boolean suspended, Integer appVersion, Map<String, Object> taskVariables, Map<String, Object> processVariables) {
        super(id);
        this.name = name;
        this.localizedName = localizedName;
        this.description = description;
        this.localizedDescription = localizedDescription;
        this.priority = priority;
        this.owner = owner;
        this.assignee = assignee;
        this.delegationState = delegationState;
        this.dueDate = dueDate;
        this.category = category;
        this.parentTaskId = parentTaskId;
        this.tenantId = tenantId;
        this.formKey = formKey;
        this.suspended = suspended;
        this.appVersion = appVersion;
        this.taskVariables = taskVariables;
        this.processVariables = processVariables;
    }

    public enum DelegationState {
        PENDING,
        RESOLVED
    }
}
