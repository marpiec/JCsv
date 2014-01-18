package pl.marpiec.jcsv.impl;

import org.testng.annotations.Test;
import pl.marpiec.jcsv.JCsv;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marpiec.tests.TestUtils.map;

public class CsvBeanReaderTest {

    private static class UserBean {
        private String username;
        private int age;
        private String role;

        public static UserBean of(String username, int age, String role) {
            final UserBean userBean = new UserBean();
            userBean.setName(username);
            userBean.setAge(age);
            userBean.setRole(role);
            return userBean;
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

        public void setName(String username) {
            this.username = username;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserBean userBean = (UserBean) o;

            if (age != userBean.age) return false;
            if (role != null ? !role.equals(userBean.role) : userBean.role != null) return false;
            if (username != null ? !username.equals(userBean.username) : userBean.username != null) return false;

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
        final List<UserBean> users = new JCsv().read(csv, UserBean.class);

        //then
        assertThat(users).containsExactly(UserBean.of("marcin",     30, "admin"),
                                          UserBean.of("john",       23, "admin"),
                                          UserBean.of("mike",       70, "user"));
    }


}