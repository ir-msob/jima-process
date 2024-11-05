package ir.msob.jima.process.commons.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.model.criteria.filter.Filter;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeploymentCriteria {
    private Filter<String> id;
    private Filter<String> name;
    private Filter<String> filePath;
    private Filter<Instant> deploymentDate;
    private Filter<String> category;
    private Filter<String> key;
    private Filter<String> tenantId;
    private Filter<Integer> version;
    private Filter<String> projectReleaseVersion;
}
