package ir.msob.jima.process.commons.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.model.criteria.filter.Filter;
import ir.msob.jima.process.commons.dto.TaskDto;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskCriteria {
    private Filter<String> id;
    private Filter<String> name;
    private Filter<String> localizedName;
    private Filter<String> description;
    private Filter<String> localizedDescription;
    private Filter<Integer> priority;
    private Filter<String> owner;
    private Filter<String> assignee;
    private Filter<TaskDto.DelegationState> delegationState;
    private Filter<Instant> dueDate;
    private Filter<String> category;
    private Filter<String> parentTaskId;
    private Filter<String> tenantId;
    private Filter<String> formKey;
    private Filter<Boolean> suspended = Filter.eq(false);
    private Filter<Integer> appVersion;
    private Map<String, Filter<Serializable>> taskVariables = new HashMap<>();
    private Map<String, Filter<Serializable>> processVariables = new HashMap<>();
}
