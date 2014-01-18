package pl.marpiec.jcsv.impl;

import org.testng.annotations.Test;
import pl.marpiec.jcsv.JCsv;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marpiec.tests.TestUtils.list;

public class CsvObjectTypesReaderWriterTest {

    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    private enum UserRole {
        user("User"), admin("Admin");

        private final String label;

        UserRole(String label) {
            this.label = label;
        }
    }

    private static class TestType {
        private String stringValue;
        private int primitiveInt;
        private Integer wrappedInt;
        private long primitiveLong;
        private Long wrappedLong;
        private double primitiveDouble;
        private Double wrappedDouble;
        private float primitiveFloat;
        private Float wrappedFloat;
        private byte primitiveByte;
        private Byte wrappedByte;
        private short primitiveShort;
        private Short wrappedShort;
        private boolean primitiveBoolean;
        private Boolean wrappedBoolean;
        private UserRole enumValue;

        private TestType(String stringValue, int primitiveInt, Integer wrappedInt, long primitiveLong, Long wrappedLong, double primitiveDouble, Double wrappedDouble, float primitiveFloat, Float wrappedFloat, byte primitiveByte, Byte wrappedByte, short primitiveShort, Short wrappedShort, boolean primitiveBoolean, Boolean wrappedBoolean, UserRole enumValue) {
            this.stringValue = stringValue;

            this.primitiveInt = primitiveInt;
            this.wrappedInt = wrappedInt;
            this.primitiveLong = primitiveLong;
            this.wrappedLong = wrappedLong;
            this.primitiveDouble = primitiveDouble;
            this.wrappedDouble = wrappedDouble;
            this.primitiveFloat = primitiveFloat;
            this.wrappedFloat = wrappedFloat;
            this.primitiveByte = primitiveByte;
            this.wrappedByte = wrappedByte;
            this.primitiveShort = primitiveShort;
            this.wrappedShort = wrappedShort;
            this.primitiveBoolean = primitiveBoolean;
            this.wrappedBoolean = wrappedBoolean;
            this.enumValue = enumValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestType testType = (TestType) o;

            if (primitiveBoolean != testType.primitiveBoolean) return false;
            if (primitiveByte != testType.primitiveByte) return false;
            if (Double.compare(testType.primitiveDouble, primitiveDouble) != 0) return false;
            if (Float.compare(testType.primitiveFloat, primitiveFloat) != 0) return false;
            if (primitiveInt != testType.primitiveInt) return false;
            if (primitiveLong != testType.primitiveLong) return false;
            if (primitiveShort != testType.primitiveShort) return false;
            if (enumValue != testType.enumValue) return false;
            if (stringValue != null ? !stringValue.equals(testType.stringValue) : testType.stringValue != null)
                return false;
            if (wrappedBoolean != null ? !wrappedBoolean.equals(testType.wrappedBoolean) : testType.wrappedBoolean != null)
                return false;
            if (wrappedByte != null ? !wrappedByte.equals(testType.wrappedByte) : testType.wrappedByte != null)
                return false;
            if (wrappedDouble != null ? !wrappedDouble.equals(testType.wrappedDouble) : testType.wrappedDouble != null)
                return false;
            if (wrappedFloat != null ? !wrappedFloat.equals(testType.wrappedFloat) : testType.wrappedFloat != null)
                return false;
            if (wrappedInt != null ? !wrappedInt.equals(testType.wrappedInt) : testType.wrappedInt != null)
                return false;
            if (wrappedLong != null ? !wrappedLong.equals(testType.wrappedLong) : testType.wrappedLong != null)
                return false;
            if (wrappedShort != null ? !wrappedShort.equals(testType.wrappedShort) : testType.wrappedShort != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = stringValue != null ? stringValue.hashCode() : 0;
            result = 31 * result + primitiveInt;
            result = 31 * result + (wrappedInt != null ? wrappedInt.hashCode() : 0);
            result = 31 * result + (int) (primitiveLong ^ (primitiveLong >>> 32));
            result = 31 * result + (wrappedLong != null ? wrappedLong.hashCode() : 0);
            temp = Double.doubleToLongBits(primitiveDouble);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + (wrappedDouble != null ? wrappedDouble.hashCode() : 0);
            result = 31 * result + (primitiveFloat != +0.0f ? Float.floatToIntBits(primitiveFloat) : 0);
            result = 31 * result + (wrappedFloat != null ? wrappedFloat.hashCode() : 0);
            result = 31 * result + (int) primitiveByte;
            result = 31 * result + (wrappedByte != null ? wrappedByte.hashCode() : 0);
            result = 31 * result + (int) primitiveShort;
            result = 31 * result + (wrappedShort != null ? wrappedShort.hashCode() : 0);
            result = 31 * result + (primitiveBoolean ? 1 : 0);
            result = 31 * result + (wrappedBoolean != null ? wrappedBoolean.hashCode() : 0);
            result = 31 * result + (enumValue != null ? enumValue.hashCode() : 0);
            return result;
        }
    }


    @Test
    public void shouldWriteAllSupportedTypesToCsv() {

        //given
        final List<TestType> objects = list(new TestType("marcin", 1, 2, 3000L, 4000L, 1.2, 2.3, 11.1f, 14.6f, (byte) 7, Byte.valueOf((byte) 8), (short) 9, Short.valueOf((short) 10), true, false, UserRole.user));

        //when

        final String csv = new JCsv().writeObjects(objects);

        //then
        assertThat(csv).isEqualTo("primitiveBoolean,primitiveByte,primitiveDouble,primitiveFloat,primitiveInt,primitiveLong,primitiveShort,stringValue,wrappedBoolean,wrappedByte,wrappedDouble,wrappedFloat,wrappedInt,wrappedLong,wrappedShort,enumValue" + LINE_SEPARATOR +
                "true,7,1.2,11.1,1,3000,9,marcin,false,8,2.3,14.6,2,4000,10,user" + LINE_SEPARATOR);

    }


    @Test
    public void shouldReadAllSupportedTypes() {

        //given
        final String csv = "primitiveBoolean,primitiveByte,primitiveDouble,primitiveFloat,primitiveInt,primitiveLong,primitiveShort,stringValue,wrappedBoolean,wrappedByte,wrappedDouble,wrappedFloat,wrappedInt,wrappedLong,wrappedShort,enumValue" + LINE_SEPARATOR +
                "true,7,1.2,11.1,1,3000,9,marcin,false,8,2.3,14.6,2,4000,10,admin" + LINE_SEPARATOR;

        //when

        final List<TestType> objects = new JCsv().readObjects(csv, TestType.class);

        //then
        assertThat(objects).containsExactly(new TestType("marcin", 1, 2, 3000L, 4000L, 1.2, 2.3, 11.1f, 14.6f, (byte) 7, Byte.valueOf((byte) 8), (short) 9, Short.valueOf((short) 10), true, false, UserRole.admin));

    }

}