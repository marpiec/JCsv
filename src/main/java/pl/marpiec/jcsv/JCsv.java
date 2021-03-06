package pl.marpiec.jcsv;

import pl.marpiec.jcsv.impl.CsvReader;
import pl.marpiec.jcsv.impl.CsvWriter;
import pl.marpiec.jcsv.impl.MapFromObjectBuilder;
import pl.marpiec.jcsv.impl.ObjectFromMapBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JCsv {

    private final char valueSeparator;
    private final String lineSeparator;
    private final Map<Class<?>, TypeConverter> converters = new HashMap<Class<?>, TypeConverter>();

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
        return new CsvWriter(valueSeparator, lineSeparator).write(values);
    }

    public String write(List<Map<String, String>> values, List<String> keys) {
        return new CsvWriter(valueSeparator, lineSeparator).write(values, keys);
    }

    public <T> List<T> readObjects(String csv, Class<T> clazz) {
        return new ObjectFromMapBuilder(converters).build(read(csv), clazz);
    }

    public String writeObjects(List<?> values) {
        final List<Map<String, String>> maps = new MapFromObjectBuilder(converters).buildList((List<Object>) values);
        return new CsvWriter(valueSeparator, lineSeparator).write(maps);
    }

    public String writeObjects(List<?> values, List<String> keys) {
        final List<Map<String, String>> maps = new MapFromObjectBuilder(converters).buildList((List<Object>) values);
        return new CsvWriter(valueSeparator, lineSeparator).write(maps, keys);
    }

    public void addTypeConverter(TypeConverter typeConverter) {
        converters.put(typeConverter.type(), typeConverter);
    }
}
