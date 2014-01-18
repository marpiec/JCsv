package pl.marpiec.tests;

import java.util.*;

public class TestUtils {

    public static Map<String, String> map(String... keysVals) {
        if (keysVals.length % 2 == 1) {
            throw new IllegalArgumentException("List of key s and values needs to have even length, it was " + keysVals.length);
        }

        Map<String, String> resultMap = new HashMap<String, String>();

        for (int i = 0; i < keysVals.length; i += 2) {
            resultMap.put(keysVals[i], keysVals[i + 1]);
        }

        return resultMap;
    }

    public static <T> List<T> list(T... values) {
        List<T> resultList = new ArrayList<T>(values.length);
        Collections.addAll(resultList, values);
        return resultList;
    }
}
