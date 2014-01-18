package pl.marpiec.jcsv.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFromObjectBuilder {

    private final Map<Class<?>, Field[]> fieldCache = new HashMap<Class<?>, Field[]>();

    public List<Map<String, String>> buildList(List<Object> objects) {
        final List<Map<String, String>> resultList = new ArrayList<Map<String, String>>(objects.size());

        for (Object object : objects) {
            resultList.add(build(object));
        }

        return resultList;
    }

    public Map<String, String> build(Object object) {
        try {
            final Field[] fields = extractFields(object.getClass());
            final Map<String, String> resultMap = new HashMap<String, String>(fields.length);
            for (Field field : fields) {
                final boolean accessible = field.isAccessible();
                if (!accessible) {
                    field.setAccessible(true);
                }

                final Object value = field.get(object);

                if (!accessible) {
                    field.setAccessible(false);
                }
                resultMap.put(field.getName(), value.toString());
            }
            return resultMap;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private Field[] extractFields(Class<?> clazz) {
        final Field[] cached = fieldCache.get(clazz);
        if (cached == null) {
            final Field[] fields = clazz.getDeclaredFields();
            fieldCache.put(clazz, fields);
            return fields;
        } else {
            return cached;
        }
    }

}
