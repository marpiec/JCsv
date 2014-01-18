package pl.marpiec.jcsv.impl;

import org.testng.annotations.Test;
import pl.marpiec.jcsv.JCsv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marpiec.tests.TestUtils.list;
import static pl.marpiec.tests.TestUtils.map;

public class CsvWriterTest {

    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Test
    public void shouldWriteSimpleCsvFile() {
        //given

        final List<Map<String, String>> users = list(map("username", "marcin",     "age", "30",    "role", "admin"),
                                                     map("username", "john",       "age", "23",    "role", "admin"),
                                                     map("username", "mike",       "age", "70",    "role", "user"));

        //when
        final String csv = new JCsv().write(users);

        //then
        assertThat(csv).isEqualTo("age,role,username" + LINE_SEPARATOR +
                "30,admin,marcin" + LINE_SEPARATOR +
                "23,admin,john" + LINE_SEPARATOR +
                "70,user,mike" + LINE_SEPARATOR);
    }

    @Test
    public void shouldWriteCsvFileWithCommasInValues() {
        //given

        final List<Map<String, String>> users = list(map("username", "marcin",     "age", "30",    "role", "admin,user"),
                                                     map("username", "john,jr",       "age", "23",    "role", "admin"),
                                                     map("username", "mike",       "age", "70",    "role", "user"));

        //when
        final String csv = new JCsv().write(users);

        //then
        assertThat(csv).isEqualTo("age,role,username" + LINE_SEPARATOR +
                "30,\"admin,user\",marcin" + LINE_SEPARATOR +
                "23,admin,\"john,jr\"" + LINE_SEPARATOR +
                "70,user,mike" + LINE_SEPARATOR);
    }

    @Test
    public void shouldWriteCsvFileWithQuotesInValues() {
        //given

        final List<Map<String, String>> users = list(map("username", "marcin",     "age", "30",    "role", "admin,user"),
                map("username", "john \"bond,rambo\"",       "age", "23",    "role", "admin"),
                map("username", "mike",       "age", "70",    "role", "user"));

        //when
        final String csv = new JCsv().write(users);

        //then
        assertThat(csv).isEqualTo("age,role,username" + LINE_SEPARATOR +
                "30,\"admin,user\",marcin" + LINE_SEPARATOR +
                "23,admin,\"john \"\"\"bond,rambo\"\"\"\"" + LINE_SEPARATOR +
                "70,user,mike" + LINE_SEPARATOR);
    }


    @Test
    public void shouldWriteSimpleCsvFileWithLimitedOrderedKeys() {
        //given

        final List<Map<String, String>> users = list(map("username", "marcin",     "age", "30",    "role", "admin"),
                map("username", "john",       "age", "23",    "role", "admin"),
                map("username", "mike",       "age", "70",    "role", "user"));

        //when
        final String csv = new JCsv().write(users, list("role", "username"));

        //then
        assertThat(csv).isEqualTo("role,username" + LINE_SEPARATOR +
                "admin,marcin" + LINE_SEPARATOR +
                "admin,john" + LINE_SEPARATOR +
                "user,mike" + LINE_SEPARATOR);
    }

    @Test
    public void shouldWriteSimpleCsvFileWithNonExistingValues() {
        //given

        final List<Map<String, String>> users = list(map("username", "marcin",     "age", "30",    "role", "admin"),
                map("username", "john",         "role", "admin"),
                map("username", "mike"));

        //when
        final String csv = new JCsv().write(users, list("role", "username"));

        //then
        assertThat(csv).isEqualTo("role,username" + LINE_SEPARATOR +
                "admin,marcin" + LINE_SEPARATOR +
                "admin,john" + LINE_SEPARATOR +
                ",mike" + LINE_SEPARATOR);
    }

    @Test
    public void shouldWriteCsvFileWithAlteredSeparator() {
        //given

        final List<Map<String, String>> users = list(map("username", "marcin",     "age", "30",    "role", "admin|user"),
                                                    map("username", "john",       "age", "23",    "role", "admin"),
                                                    map("username", "mike",       "age", "70",    "role", "user"));

        //when
        final String csv = new JCsv('|').write(users);

        //then
        assertThat(csv).isEqualTo("age|role|username" + LINE_SEPARATOR +
                                    "30|\"admin|user\"|marcin" + LINE_SEPARATOR +
                                    "23|admin|john" + LINE_SEPARATOR +
                                    "70|user|mike" + LINE_SEPARATOR);
    }
}