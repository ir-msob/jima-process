package ir.msob.jima.process.ral.activiti.beans.query;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.filter.BaseFilterQuery;
import ir.msob.jima.core.commons.filter.Filter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Utility class providing reflection-based query population for Activiti engine queries.
 * <p>
 * This helper bridges application-level {@link BaseCriteria} objects with
 * Activiti's query APIs (e.g., {@code DeploymentQuery}, {@code TaskQuery},
 * {@code ProcessInstanceQuery}). It dynamically inspects all {@link BaseFilterQuery}
 * fields inside criteria classes and applies them to Activiti query objects using
 * reflection-based method resolution.
 * <p>
 * Designed to be lightweight, reusable, and backward-compatible with different query types.
 *
 * <h3>Supported Filters</h3>
 * <ul>
 *   <li>eq / ne — equals / not equals</li>
 *   <li>regex — pattern matching</li>
 *   <li>gt / gte / lt / lte — comparison filters</li>
 *   <li>exists — field existence</li>
 *   <li>in / nin — inclusion and exclusion filters</li>
 *   <li>or — recursive nested filters</li>
 * </ul>
 *
 * <h3>Implementation Details</h3>
 * Uses {@link SharedReflectionUtil} to invoke resolved methods on Activiti queries,
 * determined by the provided {@link MethodNameResolver} implementations.
 *
 * @see BaseCriteria
 * @see BaseFilterQuery
 * @see MethodNameResolver
 * @see SharedReflectionUtil
 */
public final class ActivitiQueryUtils {

    private ActivitiQueryUtils() {
    }

    /**
     * Applies filtering criteria to an {@link org.activiti.engine.repository.DeploymentQuery}.
     *
     * @param query    the Activiti deployment query instance
     * @param criteria the filtering criteria
     * @param <C>      the type of criteria extending {@link BaseCriteria}
     */
    public static <C extends BaseCriteria<?>> void applyCriteriaToQuery(
            org.activiti.engine.repository.DeploymentQuery query, C criteria) {
        applyCriteriaToQueryInternal(query, criteria, new DeploymentMethodNameResolver());
    }

    /**
     * Applies filtering criteria to an {@link org.activiti.engine.task.TaskQuery}.
     *
     * @param query    the Activiti task query instance
     * @param criteria the filtering criteria
     * @param <C>      the type of criteria extending {@link BaseCriteria}
     */
    public static <C extends BaseCriteria<?>> void applyCriteriaToQuery(
            org.activiti.engine.task.TaskQuery query, C criteria) {
        applyCriteriaToQueryInternal(query, criteria, new TaskMethodNameResolver());
    }

    /**
     * Applies filtering criteria to an {@link org.activiti.engine.runtime.ProcessInstanceQuery}.
     *
     * @param query    the Activiti process instance query instance
     * @param criteria the filtering criteria
     * @param <C>      the type of criteria extending {@link BaseCriteria}
     */
    public static <C extends BaseCriteria<?>> void applyCriteriaToQuery(
            org.activiti.engine.runtime.ProcessInstanceQuery query, C criteria) {
        applyCriteriaToQueryInternal(query, criteria, new ProcessInstanceMethodNameResolver());
    }

    /**
     * Recursively collects all declared fields from a given object's class hierarchy,
     * excluding {@link Object}.
     *
     * @param obj the target object
     * @return a collection of all declared fields across superclasses
     */
    private static Collection<Field> getAllFields(Object obj) {
        List<Field> fields = new ArrayList<>();
        Class<?> type = obj.getClass();
        while (type != null && type != Object.class) {
            fields.addAll(Arrays.asList(type.getDeclaredFields()));
            type = type.getSuperclass();
        }
        return fields;
    }

    /**
     * Internal logic to apply {@link BaseCriteria} values to a specific Activiti query instance.
     * Uses reflection to dynamically resolve and invoke matching methods on the query.
     *
     * @param engineQuery the Activiti query instance
     * @param criteria    the filtering criteria
     * @param resolver    the resolver mapping field names to Activiti method names
     * @param <C>         the type of criteria extending {@link BaseCriteria}
     */
    private static <C extends BaseCriteria<?>> void applyCriteriaToQueryInternal(
            Object engineQuery, C criteria, MethodNameResolver resolver) {

        if (criteria == null) return;

        for (Field field : getAllFields(criteria)) {
            try {
                field.setAccessible(true);
                if (!BaseFilterQuery.class.isAssignableFrom(field.getType())) continue;

                BaseFilterQuery<?> filter = (BaseFilterQuery<?>) field.get(criteria);
                if (filter == null) continue;

                applyFilter(engineQuery, field.getName(), filter, resolver);
            } catch (Throwable ignored) {
                // Silently skip inaccessible or invalid fields
            }
        }
    }

    /**
     * Applies a {@link BaseFilterQuery} for a single field to the given Activiti query,
     * by resolving and invoking corresponding query methods.
     *
     * @param engineQuery the Activiti query instance
     * @param fieldName   the name of the field being filtered
     * @param filter      the filter definition for that field
     * @param resolver    the resolver mapping filter types to method names
     */
    private static void applyFilter(Object engineQuery, String fieldName, BaseFilterQuery<?> filter, MethodNameResolver resolver) {
        if (filter.getEq() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "EQ"), filter.getEq());
        if (filter.getNe() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "NE"), filter.getNe());
        if (filter.getRegex() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "REGEX"), filter.getRegex());
        if (filter.getGt() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "GT"), filter.getGt());
        if (filter.getGte() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "GTE"), filter.getGte());
        if (filter.getLt() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "LT"), filter.getLt());
        if (filter.getLte() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "LTE"), filter.getLte());
        if (filter.getExists() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "EXISTS"), filter.getExists());
        if (filter.getIn() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "IN"), filter.getIn());
        if (filter.getNin() != null)
            SharedReflectionUtil.invokeWithResolvedName(engineQuery, resolver.resolve(fieldName, "NIN"), filter.getNin());

        // Recursive OR conditions
        if (filter instanceof Filter<?> f && f.getOr() != null)
            applyFilter(engineQuery, fieldName, f.getOr(), resolver);
    }
}
