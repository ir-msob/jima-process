package ir.msob.jima.process.commons.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.criteria.filter.Filter;
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
public class ProcessCriteria {
    private Filter<String> id;
    private Filter<String> name;
    private Filter<String> description;
    private Filter<String> key;
    private Filter<Integer> version;
    private Filter<String> deploymentId;
    private Filter<String> businessKey;
    private Filter<Boolean> suspended = Filter.eq(false);
    private Map<String, Filter<? extends Serializable>> variables = new HashMap<>();
    private Filter<String> tenantId;
    private Map<String, Filter<? extends Serializable>> transientVariables = new HashMap<>();
    private Filter<String> localizedName;
    private Filter<String> localizedDescription;
    private Filter<Instant> startDate;
    private Filter<String> startUserId;
    private Filter<Integer> appVersion;
    private Filter<String> messageName;
}
