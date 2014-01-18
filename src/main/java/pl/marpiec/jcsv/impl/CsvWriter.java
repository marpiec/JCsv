package pl.marpiec.jcsv.impl;

import java.util.*;

public class CsvWriter {

    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    public String write(Iterable<Map<String, String>> values) {

        Set<String> keys = new HashSet<String>();
        for(Map<String, String> row: values) {
            keys.addAll(row.keySet());
        }

        final ArrayList<String> keysOrdered = new ArrayList<String>(keys);
        Collections.sort(keysOrdered);

        return write(values, keysOrdered);
    }

    public String write(Iterable<Map<String, String>> values, List<String> keys) {

        StringBuilder csv = new StringBuilder();

        writeHeader(csv, keys);

        for(Map<String, String> row: values) {
            writeRow(csv, keys, row);
        }

        return csv.toString();
    }

    private void writeHeader(StringBuilder csv, List<String> keys) {
        boolean notFirst = false;
        for(String key: keys) {
            if(notFirst) {
                csv.append(',');
            }
            notFirst = true;
            csv.append(escapeIfRequired(key));
        }
        csv.append(LINE_SEPARATOR);
    }

    private void writeRow(StringBuilder csv, List<String> keys, Map<String, String> row) {
        boolean notFirst = false;
        for(String key: keys) {
            if(notFirst) {
                csv.append(',');
            }
            notFirst = true;
            csv.append(escapeIfRequired(row.get(key)));
        }
        csv.append(LINE_SEPARATOR);
    }


    private String escapeIfRequired(String key) {
        if(key.indexOf(',') >= 0 || key.indexOf('"') >= 0) {
            return "\""+key.replaceAll("\"", "\"\"\"")+"\"";
        } else {
            return key;
        }
    }
}
