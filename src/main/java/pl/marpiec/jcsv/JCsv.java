package pl.marpiec.jcsv;

import pl.marpiec.jcsv.impl.CsvReader;
import pl.marpiec.jcsv.impl.CsvWriter;

import java.util.List;
import java.util.Map;

public class JCsv {

    private final char valueSeparator;
    private final String lineSeparator;

    public JCsv() {
        this.valueSeparator = ',';
        lineSeparator = System.getProperty("line.separator");
    }

    public JCsv(char valueSeparator, String lineSeparator) {
        this.valueSeparator = valueSeparator;
        this.lineSeparator = lineSeparator;
    }

    public JCsv(char valueSeparator) {
        this.valueSeparator = valueSeparator;
        lineSeparator = System.getProperty("line.separator");
    }

    public JCsv(String lineSeparator) {
        this.lineSeparator = lineSeparator;
        this.valueSeparator = ',';
    }

    public List<Map<String, String>> read(String csv) {
        return new CsvReader(valueSeparator).read(csv);
    }

    public String write(List<Map<String, String>> values) {
        return new CsvWriter(valueSeparator , lineSeparator).write(values);
    }

    public String write(List<Map<String, String>> values, List<String> keys) {
        return new CsvWriter(valueSeparator , lineSeparator).write(values, keys);
    }

}
