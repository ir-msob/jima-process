package ir.msob.jima.process.ral.activiti.beans.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.repository.BaseQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseActivitiQuery<Q, SELF extends BaseActivitiQuery<Q, SELF>> implements BaseQuery {
    protected final Q query;
    private final MethodNameResolver resolver;
    private Pageable pageable;

    protected BaseActivitiQuery(Q query, MethodNameResolver resolver) {
        this.query = query;
        this.resolver = resolver;
    }

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }

    protected void apply(String field, String op, Object value) {
        String methodName = resolver.resolve(field, op);
        SharedReflectionUtil.invokeWithResolvedName(query, methodName, value);
    }

    public SELF is(String field, Object value) {
        apply(field, "EQ", value);
        return self();
    }

    public SELF ne(String field, Object value) {
        apply(field, "NE", value);
        return self();
    }

    public SELF regex(String field, Object value) {
        apply(field, "REGEX", value);
        return self();
    }

    public SELF gt(String field, Object value) {
        apply(field, "GT", value);
        return self();
    }

    public SELF gte(String field, Object value) {
        apply(field, "GTE", value);
        return self();
    }

    public SELF lt(String field, Object value) {
        apply(field, "LT", value);
        return self();
    }

    public SELF lte(String field, Object value) {
        apply(field, "LTE", value);
        return self();
    }

    public SELF exists(String field, Boolean val) {
        apply(field, "EXISTS", val);
        return self();
    }

    public SELF in(String field, java.util.Collection<?> vals) {
        apply(field, "IN", vals);
        return self();
    }

    public SELF nin(String field, java.util.Collection<?> vals) {
        apply(field, "NIN", vals);
        return self();
    }
}