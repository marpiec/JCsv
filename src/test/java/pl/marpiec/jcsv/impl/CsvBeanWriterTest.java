package pl.marpiec.jcsv.impl;

import org.testng.annotations.Test;
import pl.marpiec.jcsv.JCsv;

import java.util.ArrayList;
import java.util.List;

import static pl.marpiec.tests.TestUtils.list;
import static org.assertj.core.api.Assertions.assertThat;

public class CsvBeanWriterTest {

    private final String LINE_SEPARATOR = System.getProperty("line.separator");

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
    public void shouldWriteSimpleCsvFileFromProperBean() {

        //given
        final List<UserBean> users = list(UserBean.of("marcin", 30, "admin"),
                UserBean.of("john", 23, "admin"),
                UserBean.of("mike", 70, "user"));

        //when

        final String csv = new JCsv().writeObjects(users);

        //then
        assertThat(csv).isEqualTo("age,role,username" + LINE_SEPARATOR +
                "30,admin,marcin" + LINE_SEPARATOR +
                "23,admin,john" + LINE_SEPARATOR +
                "70,user,mike" + LINE_SEPARATOR);
    }

    @Test
    public void shouldWriteSimpleCsvFileFromProperBeanWithSelectedFields() {

        //given
        final List<UserBean> users = list(UserBean.of("marcin", 30, "admin"),
                UserBean.of("john", 23, "admin"),
                UserBean.of("mike", 70, "user"));

        //when

        List<String> keys = new ArrayList<String>();
        keys.add("age");
        keys.add("username");
        final String csv = new JCsv().writeObjects(users, keys);

        //then
        assertThat(csv).isEqualTo("age,username" + LINE_SEPARATOR +
                "30,marcin" +LINE_SEPARATOR +
                "23,john" +LINE_SEPARATOR +
                "70,mike" + LINE_SEPARATOR);
    }

}