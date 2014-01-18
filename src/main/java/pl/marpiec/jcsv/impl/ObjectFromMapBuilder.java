package pl.marpiec.jcsv.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectFromMapBuilder {

    private final ObjectConstructionUtil objectConstructionUtil = new ObjectConstructionUtil();

    public <T> List<T> build(List<Map<String, String>> maps, Class<T> clazz) {

        final List<T> resultList = new ArrayList<T>(maps.size());

        for (Map<String, String> row : maps) {
            resultList.add(build(row, clazz));
        }

        return resultList;
    }

    public <T> T build(Map<String, String> map, Class<T> clazz) {
        try {
            final T instance = (T) objectConstructionUtil.createInstance(clazz);

            for (Map.Entry<String, String> entry : map.entrySet()) {
                final Field field = clazz.getDeclaredField(entry.getKey());
                final boolean accessible = field.isAccessible();
                if (!accessible) {
                    field.setAccessible(true);
                }

                final Class<?> fieldType = field.getType();

                field.set(instance, convertToCorrectType(entry.getValue(), fieldType));

                if (!accessible) {
                    field.setAccessible(false);
                }
            }

            return instance;
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private Object convertToCorrectType(String value, Class<?> fieldType) {
        if (fieldType == String.class) {
            return value;
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value);
        } else if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(value);
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (fieldType == float.class || fieldType == Float.class) {
            return Float.parseFloat(value);
        } else if (fieldType == short.class || fieldType == Short.class) {
            return Short.parseShort(value);
        } else if (fieldType == byte.class || fieldType == Byte.class) {
            return Byte.parseByte(value);
        } else {
            throw new IllegalStateException("Only primitive types, their wrappers and String are supported, found+" + fieldType);
        }
    }
}
