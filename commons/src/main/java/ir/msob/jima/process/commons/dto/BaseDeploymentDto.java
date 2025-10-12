package ir.msob.jima.process.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.process.commons.domain.BaseDeployment;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDeploymentDto extends BaseDeployment {
    @Builder
    public BaseDeploymentDto(String id, String name, String key, String filePath, String category, String tenantId, Integer version, String projectReleaseVersion, Instant deploymentDate) {
        super(id, name, key, filePath, category, tenantId, version, projectReleaseVersion, deploymentDate);
    }
}
