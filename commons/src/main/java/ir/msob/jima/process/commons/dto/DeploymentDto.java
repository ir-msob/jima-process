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
public class DeploymentDto {
    private String id;
    private String name;
    private String key;
    private String filePath;
    private String category;
    private String tenantId;
    private Integer version;
    private String projectReleaseVersion;
    private Instant deploymentDate;
}
