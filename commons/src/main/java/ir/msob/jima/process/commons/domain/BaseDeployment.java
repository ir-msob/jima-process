package ir.msob.jima.process.commons.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.domain.BaseDomainAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDeployment extends BaseDomainAbstract<String> {
    private String name;
    private String key;
    private String filePath;
    private String category;
    private String tenantId;
    private Integer version;
    private String projectReleaseVersion;
    private Instant deploymentDate;

    public BaseDeployment(String id, String name, String key, String filePath, String category, String tenantId, Integer version, String projectReleaseVersion, Instant deploymentDate) {
        super(id);
        this.name = name;
        this.key = key;
        this.filePath = filePath;
        this.category = category;
        this.tenantId = tenantId;
        this.version = version;
        this.projectReleaseVersion = projectReleaseVersion;
        this.deploymentDate = deploymentDate;
    }
}
