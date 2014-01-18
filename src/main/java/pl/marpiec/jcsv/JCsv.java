package pl.marpiec.jcsv;

import pl.marpiec.jcsv.impl.CsvReader;
import pl.marpiec.jcsv.impl.CsvWriter;

import java.util.List;
import java.util.Map;

public class JCsv {

    public List<Map<String, String>> read(String csv) {
        return new CsvReader().read(csv);
    }

    public String write(List<Map<String, String>> values) {
        return new CsvWriter().write(values);
    }

    public String write(List<Map<String, String>> values, List<String> keys) {
        return new CsvWriter().write(values, keys);
    }

}
