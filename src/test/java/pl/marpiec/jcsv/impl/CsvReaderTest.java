package pl.marpiec.jcsv.impl;

import org.testng.annotations.Test;
import pl.marpiec.jcsv.JCsv;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marpiec.tests.TestUtils.map;

public class CsvReaderTest {

    @Test
    public void shouldParseSimpleCsvFile() {

        //given
        final String csv = "username,age,role\n" +
                "marcin,30,admin\n" +
                "john,23,admin\n" +
                "mike,70,user\n";

        //when
        final List<Map<String, String>> users = new JCsv().read(csv);

        //then
        assertThat(users).containsExactly(map("username", "marcin", "age", "30", "role", "admin"),
                map("username", "john", "age", "23", "role", "admin"),
                map("username", "mike", "age", "70", "role", "user"));
    }


    @Test
    public void shouldParseCsvFileWithCommasInValues() {

        //given
        final String csv = "username,age,role\n" +
                "marcin,30,\"admin, user\"\n" +
                "john,23,\"admin, user\"\n" +
                "mike,70,user\n";

        //when
        final List<Map<String, String>> users = new JCsv().read(csv);

        //then
        assertThat(users).containsExactly(map("username", "marcin", "age", "30", "role", "admin, user"),
                map("username", "john", "age", "23", "role", "admin, user"),
                map("username", "mike", "age", "70", "role", "user"));
    }


    @Test
    public void shouldParseCsvFileWithQuotesInValues() {

        //given
        final String csv = "username,age,role\n" +
                "marcin,30,\"admin, user\"\n" +
                "\"john \"\"\"bond,rambo\"\"\"\",23,\"admin, user\"\n" +
                "mike,70,user\n";

        //when
        final List<Map<String, String>> users = new JCsv().read(csv);

        //then
        assertThat(users).containsExactly(map("username", "marcin", "age", "30", "role", "admin, user"),
                map("username", "john \"bond,rambo\"", "age", "23", "role", "admin, user"),
                map("username", "mike", "age", "70", "role", "user"));
    }

    @Test
    public void shouldParseSimpleCsvFileWithDifferentFileEndingsReturn() {

        //given
        final String csv = "username,age,role\n" +
                "marcin,30,admin\r" +
                "john,23,admin\r\n" +
                "mike,70,user\n\r";

        //when
        final List<Map<String, String>> users = new JCsv().read(csv);

        //then
        assertThat(users).containsExactly(map("username", "marcin", "age", "30", "role", "admin"),
                map("username", "john", "age", "23", "role", "admin"),
                map("username", "mike", "age", "70", "role", "user"));
    }

    @Test
    public void shouldParseSimpleCsvFileWithoutNewLineAtTheEnd() {

        //given
        final String csv = "username,age,role\n" +
                "marcin,30,admin\r\n" +
                "john,23,admin\n\r" +
                "mike,70,user";

        //when
        final List<Map<String, String>> users = new JCsv().read(csv);

        //then
        assertThat(users).containsExactly(map("username", "marcin", "age", "30", "role", "admin"),
                map("username", "john", "age", "23", "role", "admin"),
                map("username", "mike", "age", "70", "role", "user"));
    }

}