package ir.msob.jima.process.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.process.commons.domain.BaseProcess;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseProcessDto extends BaseProcess {

    @Builder
    public BaseProcessDto(String id, String name, String description, String key, Integer version, String deploymentId, String businessKey, boolean suspended, Map<String, Object> processVariables, String tenantId, Map<String, Object> transientVariables, String localizedName, String localizedDescription, Instant startDate, String startUserId, Integer appVersion, String messageName, boolean duplicateCheck) {
        super(id, name, description, key, version, deploymentId, businessKey, suspended, processVariables, tenantId, transientVariables, localizedName, localizedDescription, startDate, startUserId, appVersion, messageName, duplicateCheck);
    }
}
