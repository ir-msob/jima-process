package ir.msob.jima.process.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDto {
    private String id;
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

    public enum DelegationState {
        PENDING,
        RESOLVED
    }
}
