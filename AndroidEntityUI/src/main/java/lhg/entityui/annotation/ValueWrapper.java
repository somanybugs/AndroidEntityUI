package lhg.entityui.annotation;

import java.lang.reflect.Field;

public interface ValueWrapper {
    String get(Field field, Object entity) throws IllegalAccessException;

    void set(Field field, Object entity, String value) throws IllegalAccessException;
}
