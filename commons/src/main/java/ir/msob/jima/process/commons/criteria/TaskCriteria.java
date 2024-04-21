package ir.msob.jima.process.commons.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.process.commons.dto.TaskDto;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskCriteria {
    private String id;
    private String name;
    private String localizedName;
    private String description;
    private String localizedDescription;
    private Integer priority;
    private String owner;
    private String assignee;
    private TaskDto.DelegationState delegationState;
    private Instant dueDate;
    private String category;
    private String parentTaskId;
    private String tenantId;
    private String formKey;
    private boolean suspended = false;
    private Integer appVersion;
    private Map<String, Object> variables;
}
