package lhg.entityui;

import android.text.TextUtils;

import lhg.entityui.annotation.ValueWrapper;

import java.lang.reflect.Field;

public class DefaultValueWrapper implements ValueWrapper {

    public String get(Field field, Object entity) throws IllegalAccessException {
        Object val = field.get(entity);
        return (val == null ? null : String.valueOf(val));
    }

//    public void set(Field field, Object entity, String value) throws IllegalAccessException {
//        Class type = field.getType();
//        if (CharSequence.class.isAssignableFrom(type)) {
//            field.set(entity, value);
//        }
//        else if (type == int.class || type == Integer.class) {
//            if (!TextUtils.isEmpty(value)) {
//                field.setInt(entity, Integer.parseInt(value));
//            }
//        }
//        else if (type == long.class || type == Long.class) {
//            if (!TextUtils.isEmpty(value)) {
//                field.setLong(entity, Long.parseLong(value));
//            }
//        }
//        else if (type == short.class || type == Short.class) {
//            if (!TextUtils.isEmpty(value)) {
//                field.setShort(entity, Short.parseShort(value));
//            }
//        }
//        else if (type == byte.class || type == Byte.class) {
//            if (!TextUtils.isEmpty(value)) {
//                field.setByte(entity, Byte.parseByte(value));
//            }
//        }
//        else if (type == char.class || type == Character.class) {
//            field.setChar(entity, value.charAt(0));
//        }
//        else if (type == float.class || type == Float.class) {
//            if (!TextUtils.isEmpty(value)) {
//                field.setFloat(entity, Float.parseFloat(value));
//            }
//        }
//        else if (type == double.class || type == Double.class) {
//            if (!TextUtils.isEmpty(value)) {
//                field.setDouble(entity, Double.parseDouble(value));
//            }
//        }
//        else if (type == boolean.class || type == Boolean.class) {
//            if (!TextUtils.isEmpty(value)) {
//                field.setBoolean(entity, Boolean.parseBoolean(value));
//            }
//        }
//        else {
//            throw new RuntimeException("不支持这种格式 " + type.getName());
//        }
//    }
public void set(Field field, Object entity, String value) throws IllegalAccessException {
    Class type = field.getType();
    if (CharSequence.class.isAssignableFrom(type)) {
        field.set(entity, value);
    }
    else if (type == int.class ) {
        if (!TextUtils.isEmpty(value)) {
            field.setInt(entity, Integer.parseInt(value));
        }
    }
    else if (type == long.class) {
        if (!TextUtils.isEmpty(value)) {
            field.setLong(entity, Long.parseLong(value));
        }
    }
    else if (type == short.class) {
        if (!TextUtils.isEmpty(value)) {
            field.setShort(entity, Short.parseShort(value));
        }
    }
    else if (type == byte.class) {
        if (!TextUtils.isEmpty(value)) {
            field.setByte(entity, Byte.parseByte(value));
        }
    }
    else if (type == char.class) {
        field.setChar(entity, value.charAt(0));
    }
    else if (type == float.class) {
        if (!TextUtils.isEmpty(value)) {
            field.setFloat(entity, Float.parseFloat(value));
        }
    }
    else if (type == double.class ) {
        if (!TextUtils.isEmpty(value)) {
            field.setDouble(entity, Double.parseDouble(value));
        }
    }
    else if (type == boolean.class) {
        if (!TextUtils.isEmpty(value)) {
            field.setBoolean(entity, Boolean.parseBoolean(value));
        }
    }
    else if (type == Integer.class ) {
        if (!TextUtils.isEmpty(value)) {
            field.set(entity, Integer.valueOf(value));
        } else {
            field.set(entity, null);
        }
    }
    else if (type == Long.class) {
        if (!TextUtils.isEmpty(value)) {
            field.set(entity, Long.valueOf(value));
        } else {
            field.set(entity, null);
        }
    }
    else if (type == Short.class) {
        if (!TextUtils.isEmpty(value)) {
            field.set(entity, Short.valueOf(value));
        } else {
            field.set(entity, null);
        }
    }
    else if (type == Byte.class) {
        if (!TextUtils.isEmpty(value)) {
            field.set(entity, Byte.valueOf(value));
        } else {
            field.set(entity, null);
        }
    }
    else if (type == Character.class) {
        if (!TextUtils.isEmpty(value)) {
            field.set(entity, Character.valueOf(value.charAt(0)));
        } else {
            field.set(entity, null);
        }
    }
    else if (type == Float.class) {
        if (!TextUtils.isEmpty(value)) {
            field.set(entity, Float.valueOf(value));
        } else {
            field.set(entity, null);
        }
    }
    else if (type == Double.class ) {
        if (!TextUtils.isEmpty(value)) {
            field.set(entity, Double.valueOf(value));
        } else {
            field.set(entity, null);
        }
    }
    else if (type == Boolean.class) {
        if (!TextUtils.isEmpty(value)) {
            field.set(entity, Boolean.valueOf(value));
        } else {
            field.set(entity, null);
        }
    }
    else {
        field.set(entity, value);
    }
}

}
