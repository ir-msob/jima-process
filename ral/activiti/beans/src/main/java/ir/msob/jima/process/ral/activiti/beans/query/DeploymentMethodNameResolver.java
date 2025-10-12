package ir.msob.jima.process.ral.activiti.beans.query;

final class DeploymentMethodNameResolver implements MethodNameResolver {
    @Override
    public String resolve(String field, String opMarker) {
        if (field == null) return null;
        String prefix = switch (field) {
            case "id" -> "deploymentId";
            case "name" -> "deploymentName";
            case "category" -> "deploymentCategory";
            case "key" -> "deploymentKey";
            case "tenantId" -> "deploymentTenantId";
            case "deploymentTime" -> "deploymentTime";
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
