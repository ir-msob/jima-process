package ir.msob.jima.process.ral.activiti.beans.query;

final class ProcessInstanceMethodNameResolver implements MethodNameResolver {
    @Override
    public String resolve(String field, String opMarker) {
        if (field == null) return null;
        String prefix = switch (field) {
            case "id" -> "processInstanceId";
            case "name" -> "processInstanceName";
            case "key" -> "processDefinitionKey";
            case "version" -> "processDefinitionVersion";
            case "deploymentId" -> "deploymentId";
            case "businessKey" -> "processInstanceBusinessKey";
            case "tenantId" -> "processInstanceTenantId";
            case "startUserId" -> "involvedUser";
            case "startTime" ->
                    "started"; // many activiti API methods use 'startedBefore/After' with Date, resolver will return 'started' base and op distinguishes
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