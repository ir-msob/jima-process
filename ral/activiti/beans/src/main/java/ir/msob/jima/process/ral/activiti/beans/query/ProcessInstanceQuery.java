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
public class ProcessInstanceQuery extends BaseActivitiQuery<org.activiti.engine.runtime.ProcessInstanceQuery, ProcessInstanceQuery> {

    public ProcessInstanceQuery(org.activiti.engine.runtime.ProcessInstanceQuery query) {
        super(query, new ProcessInstanceMethodNameResolver());
    }

    public ProcessInstanceQuery byId(String id) {
        return is("id", id);
    }

    public ProcessInstanceQuery byBusinessKey(String key) {
        return is("businessKey", key);
    }

    public ProcessInstanceQuery byDefinitionKey(String key) {
        return is("key", key);
    }

    public ProcessInstanceQuery startedBefore(Instant when) {
        return lt("startTime", when);
    }

    public ProcessInstanceQuery startedAfter(Instant when) {
        return gt("startTime", when);
    }
}