package ir.msob.jima.process.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDto {
    private String id;
    private String name;
    private String description;
    private String key;
    private Integer version;
    private String deploymentId;
    private String businessKey;
    private boolean suspended;
    private Map<String, Object> variables;
    private String tenantId;
    private Map<String, Object> transientVariables;
    private String localizedName;
    private String localizedDescription;
    private Instant startDate;
    private String startUserId;
    private Integer appVersion;
    private String messageName;
    private boolean duplicateCheck = false;

}
