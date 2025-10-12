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
public class BaseProcess extends BaseDomainAbstract<String> {
    private String name;
    private String description;
    private String key;
    private Integer version;
    private String deploymentId;
    private String businessKey;
    private boolean suspended;
    private Map<String, Object> processVariables;
    private String tenantId;
    private Map<String, Object> transientVariables;
    private String localizedName;
    private String localizedDescription;
    private Instant startDate;
    private String startUserId;
    private Integer appVersion;
    private String messageName;
    private boolean duplicateCheck = false;

    public BaseProcess(String id, String name, String description, String key, Integer version, String deploymentId, String businessKey, boolean suspended, Map<String, Object> processVariables, String tenantId, Map<String, Object> transientVariables, String localizedName, String localizedDescription, Instant startDate, String startUserId, Integer appVersion, String messageName, boolean duplicateCheck) {
        super(id);
        this.name = name;
        this.description = description;
        this.key = key;
        this.version = version;
        this.deploymentId = deploymentId;
        this.businessKey = businessKey;
        this.suspended = suspended;
        this.processVariables = processVariables;
        this.tenantId = tenantId;
        this.transientVariables = transientVariables;
        this.localizedName = localizedName;
        this.localizedDescription = localizedDescription;
        this.startDate = startDate;
        this.startUserId = startUserId;
        this.appVersion = appVersion;
        this.messageName = messageName;
        this.duplicateCheck = duplicateCheck;
    }
}
