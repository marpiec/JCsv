package pl.marpiec.jcsv;

public interface TypeConverter {

    String write(Object object);

    <T> T read(String text);

    Class<?> type();

}
