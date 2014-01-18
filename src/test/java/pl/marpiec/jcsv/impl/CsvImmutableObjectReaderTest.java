package pl.marpiec.jcsv.impl;

import org.testng.annotations.Test;
import pl.marpiec.jcsv.JCsv;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvImmutableObjectReaderTest {

    private static class ImmutableUser {
        private final String username;
        private final int age;
        private final String role;

        private ImmutableUser(String username, int age, String role) {
            this.username = username;
            this.age = age;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public int getAge() {
            return age;
        }

        public String getRole() {
            return role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ImmutableUser immutableUser = (ImmutableUser) o;

            if (age != immutableUser.age) return false;
            if (role != null ? !role.equals(immutableUser.role) : immutableUser.role != null) return false;
            if (username != null ? !username.equals(immutableUser.username) : immutableUser.username != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = username != null ? username.hashCode() : 0;
            result = 31 * result + age;
            result = 31 * result + (role != null ? role.hashCode() : 0);
            return result;
        }
    }


    @Test
    public void shouldParseSimpleCsvFileToProperBean() {

        //given
        final String csv = "username,age,role\n" +
                "marcin,30,admin\n" +
                "john,23,admin\n" +
                "mike,70,user\n";

        //when
        final List<ImmutableUser> users = new JCsv().readObjects(csv, ImmutableUser.class);

        //then
        assertThat(users).containsExactly(new ImmutableUser("marcin", 30, "admin"),
                                          new ImmutableUser("john", 23, "admin"),
                                          new ImmutableUser("mike", 70, "user"));
    }


}