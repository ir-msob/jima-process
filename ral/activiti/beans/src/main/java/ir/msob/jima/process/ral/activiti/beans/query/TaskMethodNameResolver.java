package ir.msob.jima.process.ral.activiti.beans.query;

final class TaskMethodNameResolver implements MethodNameResolver {
    @Override
    public String resolve(String field, String opMarker) {
        if (field == null) return null;
        String prefix = switch (field) {
            case "id" -> "taskId";
            case "name" -> "taskName";
            case "description" -> "taskDescription";
            case "priority" -> "taskPriority";
            case "owner" -> "taskOwner";
            case "assignee" -> "taskAssignee";
            case "dueDate" -> "taskDueDate";
            case "category" -> "taskCategory";
            case "parentTaskId" -> "taskParentTaskId";
            case "tenantId" -> "taskTenantId";
            default -> null;
        };
        if (prefix == null) return null;
        return switch (opMarker) {
            case "REGEX" -> prefix + "Like";
            case "IN" -> prefix + "In";
            case "NIN" -> prefix + "NotIn";
            case "NE" -> prefix + "NotEquals";
            default -> prefix;
        };
    }
}
