package ir.msob.jima.process.ral.activiti.beans.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeploymentQuery extends BaseActivitiQuery<org.activiti.engine.repository.DeploymentQuery, DeploymentQuery> {

    public DeploymentQuery(org.activiti.engine.repository.DeploymentQuery query) {
        super(query, new DeploymentMethodNameResolver());
    }

    // convenience helpers
    public DeploymentQuery byId(String id) {
        return is("id", id);
    }

    public DeploymentQuery byNameLike(String pattern) {
        return regex("name", pattern);
    }

    public DeploymentQuery byKey(String key) {
        return is("key", key);
    }

    public DeploymentQuery byTenantId(String tenantId) {
        return is("tenantId", tenantId);
    }

    public DeploymentQuery deployedBefore(Instant instant) {
        return lt("deploymentTime", instant);
    }

    public DeploymentQuery deployedAfter(Instant instant) {
        return gt("deploymentTime", instant);
    }
}