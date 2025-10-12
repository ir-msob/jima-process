package ir.msob.jima.process.ral.activiti.beans.query;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

final class SharedReflectionUtil {
    private SharedReflectionUtil() {
    }

    static boolean invokeWithResolvedName(Object target, String methodName, Object value) {
        if (target == null || methodName == null) return false;
        try {
            if (value instanceof Instant) value = Date.from((Instant) value);
            Method m = findCompatibleMethod(target.getClass(), methodName, value);
            if (m == null) return false;
            m.invoke(target, value);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    static Method findCompatibleMethod(Class<?> clazz, String name, Object value) {
        if (clazz == null || name == null) return null;
        for (Method m : clazz.getMethods()) {
            if (!m.getName().equals(name) || m.getParameterCount() != 1) continue;
            Class<?> param = m.getParameterTypes()[0];
            if (value == null) return m;
            if (param.isAssignableFrom(value.getClass())) return m;
            if (Collection.class.isAssignableFrom(param) && Collection.class.isAssignableFrom(value.getClass()))
                return m;
            if (param.isPrimitive()) {
                Class<?> boxed = boxPrimitive(param);
                if (boxed != null && boxed.isAssignableFrom(value.getClass())) return m;
            }
        }
        return null;
    }

    private static Class<?> boxPrimitive(Class<?> primitive) {
        if (primitive == int.class) return Integer.class;
        if (primitive == long.class) return Long.class;
        if (primitive == boolean.class) return Boolean.class;
        if (primitive == double.class) return Double.class;
        if (primitive == float.class) return Float.class;
        if (primitive == short.class) return Short.class;
        if (primitive == byte.class) return Byte.class;
        if (primitive == char.class) return Character.class;
        return null;
    }
}