package ir.msob.jima.process.commons.criteria;

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
public class DeploymentCriteria {
    private String id;
    private String name;
    private String filePath;
    private Instant date;
    private String category;
    private String key;
    private String tenantId;
    private Integer version;
    private String projectReleaseVersion;
}
