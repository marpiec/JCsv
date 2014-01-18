package pl.marpiec.jcsv.converter;

import pl.marpiec.jcsv.TypeConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConverter implements TypeConverter {

    @Override
    public String write(Object object) {
        return ","+javax.xml.bind.DatatypeConverter.printDateTime(toCalendar((Date) object));
    }

    public static Calendar toCalendar(Date date){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    @Override
    public Date read(String text) {
        return javax.xml.bind.DatatypeConverter.parseDateTime(text.substring(1)).getTime();
    }

    @Override
    public Class<Date> type() {
        return Date.class;
    }
}
