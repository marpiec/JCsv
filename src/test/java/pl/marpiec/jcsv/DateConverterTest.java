package pl.marpiec.jcsv;

import org.junit.Test;
import pl.marpiec.jcsv.converter.DateConverter;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marpiec.tests.TestUtils.list;

public class DateConverterTest {

    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static class TestClass {
        private final Date time;

        private TestClass(Date time) {
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestClass testClass = (TestClass) o;

            if (time != null ? !time.equals(testClass.time) : testClass.time != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return time != null ? time.hashCode() : 0;
        }
    }


    @Test
    public void shouldWriteDateType() {
        //given
        final List<TestClass> objects = list(new TestClass(new Date(1389799850000L)));

        //when
        final JCsv jCsv = new JCsv();
        jCsv.addTypeConverter(new DateConverter());
        final String csv = jCsv.writeObjects(objects);

        //then
        assertThat(csv).isEqualTo("time" + LINE_SEPARATOR +
                "\",2014-01-15T16:30:50+01:00\"" + LINE_SEPARATOR);


    }

    @Test
    public void shouldReadDateType() {
        //given
        final String csv = "time" + LINE_SEPARATOR +
                "\",2014-01-15T16:30:50+01:00\"\n\r";

        //when
        final JCsv jCsv = new JCsv();
        jCsv.addTypeConverter(new DateConverter());
        final List<TestClass> objects = jCsv.readObjects(csv, TestClass.class);

        //then
        assertThat(objects).isEqualTo(list(new TestClass(new Date(1389799850000L))));
    }

}