package org.simpleframework.core.util;

import com.touch.entity.bo.ShopCategory;

public class ConverterUtil {
    public static Object primitiveNull(Class<?> type) {
        if (type == int.class || type == double.class || type == short.class || type == long.class || type == byte.class || type == float.class) {
            return 0;
        } else if (type == boolean.class) {
            return false;
        } else {
            return null;
        }
    }

    public static Object convert(Class<?> type, String requestValue) {
        if (isPrimitive(type)) {
            if (ValidationUtil.isEmpty(requestValue)) {
                return primitiveNull(type);
            }
            if ((type.equals(int.class)) || type.equals(Integer.class)) {
                return Integer.parseInt(requestValue);
            } else if (type.equals(double.class) || type.equals(Double.class)) {
                return Double.parseDouble(requestValue);
            } else if (type.equals(String.class)) {
                return requestValue;
            } else if (type.equals(Float.class) || type.equals(float.class)) {
                return Float.parseFloat(requestValue);
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.parseLong(requestValue);
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return Boolean.parseBoolean(requestValue);
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                return Short.parseShort(requestValue);
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                return Byte.parseByte(requestValue);
            } else if(type.equals(char.class) || type.equals(Character.class)){
                return requestValue.charAt(0);
            }
            else {
                throw new RuntimeException("Currently not support non-primitive type conversion");
            }
        } else {
            return requestValue;
        }
    }

    private static boolean isPrimitive(Class<?> type) {
        return type == boolean.class
                || type == Boolean.class
                || type == double.class
                || type == Double.class
                || type == float.class
                || type == Float.class
                || type == short.class
                || type == Short.class
                || type == int.class
                || type == Integer.class
                || type == long.class
                || type == Long.class
                || type == String.class
                || type == byte.class
                || type == Byte.class
                || type == char.class
                || type == Character.class;
    }
}
