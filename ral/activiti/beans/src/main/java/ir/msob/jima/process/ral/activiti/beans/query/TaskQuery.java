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
public class TaskQuery extends BaseActivitiQuery<org.activiti.engine.task.TaskQuery, TaskQuery> {

    public TaskQuery(org.activiti.engine.task.TaskQuery query) {
        super(query, new TaskMethodNameResolver());
    }

    public TaskQuery byId(String id) {
        return is("id", id);
    }

    public TaskQuery byAssignee(String assignee) {
        return is("assignee", assignee);
    }

    public TaskQuery byOwner(String owner) {
        return is("owner", owner);
    }

    public TaskQuery dueBefore(Instant when) {
        return lt("dueDate", when);
    }

    public TaskQuery dueAfter(Instant when) {
        return gt("dueDate", when);
    }
}