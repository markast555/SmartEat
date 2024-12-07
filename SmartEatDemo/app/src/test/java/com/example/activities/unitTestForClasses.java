package com.example.activities;

import static org.junit.Assert.assertEquals;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.BreakIterator;
import java.time.LocalDate;

import classes.DatabaseParams;
import classes.Goals;
import classes.PhysicalActivityLevel;
import classes.Sex;
import classes.User;
import classes.UserRepositoryCrud;
import classes.VariableGenerator;

public class unitTestForClasses {

    String url;
    String user; // Имя пользователя
    String password; // Ваш пароль
    UserRepositoryCrud userRepositoryCrud;

    @BeforeEach
    public void init() {
        url = DatabaseParams.getUrl();
        user = DatabaseParams.getUser();
        password = DatabaseParams.getPassword();
        userRepositoryCrud = new UserRepositoryCrud();
        userRepositoryCrud.setConnectionParameters(url, user, password);
    }

    @Test
    @DisplayName("Тестирование соединения с БД")
    public void testSetConnection() throws SQLException, ClassNotFoundException {
        userRepositoryCrud.setConnection();
        Assert.assertNotNull(userRepositoryCrud.getConnection());
    }

    @Test
    @DisplayName("Тестирование прерывания соединения с БД")
    public void testCloseConnection() throws SQLException, ClassNotFoundException {
        userRepositoryCrud.setConnection();
        Assert.assertNotNull(userRepositoryCrud.getConnection());
        userRepositoryCrud.closeConnection();
        Assert.assertNull(userRepositoryCrud.getConnection());
    }

    @Test
    @DisplayName("Тестирование генератора случайного логина для будущих тестов")
    public void testGenerateRandomLogin() {
        String login;
        login = VariableGenerator.generateRandomLogin();
        Assert.assertNotNull(login);
        Assert.assertTrue((login.length() >= 4) && (login.length() <= 16));
    }

    @Test
    @DisplayName("Тестирование генератора случайного адреса электроннай почты телефона для будущих тестов")
    public void testGenerateRandomPhoneNumber() {
        String gmail;
        gmail = VariableGenerator.generateRandomGmail();
        Assert.assertNotNull(gmail);
    }

    @Test
    @DisplayName("Тестирование хеширования пароля (позитивный тест)")
    public void testHashPasswordPositive(){
        String originalPassword = "AnyPassword";
        String hashedPassword = VariableGenerator.hashPassword(originalPassword);
        boolean isMatch = VariableGenerator.checkPassword(originalPassword, hashedPassword);
        Assert.assertTrue(isMatch);
    }

    @Test
    @DisplayName("Тестирование хеширования пароля (негативный тест)")
    public void testHashPasswordNegative(){
        String originalPassword = "AnyPassword";
        String hashedPassword = VariableGenerator.hashPassword(originalPassword);
        boolean isMatch = VariableGenerator.checkPassword("wrongPassword", hashedPassword);
        Assert.assertFalse(isMatch);
    }

    @Test
    @DisplayName("Тестирование создания записи пользователя в табл. users в БД")
    public void testCreateUserInBD() throws SQLException, ClassNotFoundException {
        User user = new User();
        System.out.println(user.toString());
        Assert.assertTrue(userRepositoryCrud.create(user));
    }


    @Test
    @DisplayName("Тестирование нахождения пользователя по логину в табл. users в БД")
    public void testSelectByLoginInBDPositive() throws SQLException, ClassNotFoundException {
        String login = "7SiXuRkzH";
        User user = userRepositoryCrud.selectByLogin(login);
        System.out.println(user.toString());
        Assert.assertNotNull(user);
    }

    @Test
    @DisplayName("Тестирование нахождения пользователя по логину в табл. users в БД")
    public void testSelectByLoginNegative() throws SQLException, ClassNotFoundException {
        String login = "k9Ba0G000000";;
        User user = userRepositoryCrud.selectByLogin(login);
        Assert.assertNull(user);
    }

    @Test
    @DisplayName("Тестирование на разхеширование пароля из записи, найденной из табл. users в БД по логину")
    public void testCreateUserInBD_And_SelectByLoginIn_And_HashPasswordPositive() throws SQLException, ClassNotFoundException {

        String originalPassword = "password";
        User user = new User();
        user.setPassword(originalPassword);
        user.setPassword(VariableGenerator.hashPassword(user.getPassword()));
        String originalPasswordHashBefore = user.getPassword();
        String login = user.getLogin();

        boolean isMatchBefore = VariableGenerator.checkPassword(originalPassword, originalPasswordHashBefore);
        Assert.assertTrue(isMatchBefore);

        Assert.assertTrue(userRepositoryCrud.create(user));

        User userBD = userRepositoryCrud.selectByLogin(login);
        Assert.assertNotNull(userBD);
        String originalPasswordHashAfter = userBD.getPassword();

        Assert.assertEquals(originalPasswordHashBefore, originalPasswordHashAfter);

        boolean isMatchAfter = VariableGenerator.checkPassword(originalPassword, originalPasswordHashAfter);
        Assert.assertTrue(isMatchAfter);
    }

    @Test
    @DisplayName("Тестирование нахождения записи по определённому уникальному полю в БД (логин)")
    public void testCheckByOneField_Login_Positive() throws SQLException, ClassNotFoundException {
        String login = "12345";;
        boolean result = userRepositoryCrud.checkByOneField(login, "login");
        Assert.assertTrue(result);
    }

    @Test
    @DisplayName("Тестирование нахождения записи по определённому уникальному полю в БД (логин)")
    public void testCheckByOneField_Login_Negative() throws SQLException, ClassNotFoundException {
        String login = "1234000+1";;
        boolean result = userRepositoryCrud.checkByOneField(login, "login");
        Assert.assertFalse(result);
    }

    @Test
    @DisplayName("Тестирование нахождения записи по определённому уникальному полю в БД (email)")
    public void testCheckByOneField_Email_Positive() throws SQLException, ClassNotFoundException {
        String email = "kivanov032@gmail.com";
        boolean result = userRepositoryCrud.checkByOneField(email, "gmail");
        Assert.assertTrue(result);
    }

    @Test
    @DisplayName("Тестирование нахождения записи по определённому уникальному полю в БД (email)")
    public void testCheckByOneField_Email_Negative() throws SQLException, ClassNotFoundException {
        String email = "pivanov032@gmail.com";
        boolean result = userRepositoryCrud.checkByOneField(email, "gmail");
        Assert.assertFalse(result);
    }

    @Test
    @DisplayName("Тестирование изменения данных по пользователю в табл. users в БД")
    public void testUpdate() throws SQLException, ClassNotFoundException {

        String login = "12345";
        User user = userRepositoryCrud.selectByLogin(login);
        System.out.println(user.toString());
        Assert.assertNotNull(user);

        user.setHeight(45);
        user.setWeight(35);
        user.setGoals(Goals.ImprovingHealth);
        //user.setLogin("8d8dd8d");

        int result = userRepositoryCrud.update(user);

        Assert.assertNotEquals(0, result);
    }

    @Test
    @DisplayName("Тестирование изменения данных по пользователю в табл. users в БД")
    public void testUpdate1() throws SQLException, ClassNotFoundException {

        int result = userRepositoryCrud.update1("f44b817e-9735-4fd2-b821-85838cb15ed6", 68, 77.5F);

        Assert.assertNotEquals(0, result);
    }

    @Test
    @DisplayName("Тестирование изменения данных в User")
    public void testNewUser() throws CloneNotSupportedException {

        User user = new User();
        User newUser = (User) user.clone();

        System.out.println(user.getHeight()); // User{height=0}
        System.out.println(newUser.getHeight()); // User{height=0}
        Assert.assertEquals(user.getHeight(), newUser.getHeight());
        Assert.assertEquals(user.hashCode(), newUser.hashCode());

        newUser.setHeight(181);

        System.out.println(user.getHeight()); // User{height=0}
        System.out.println(newUser.getHeight()); // User{height=181}
        Assert.assertNotEquals(user.getHeight(), newUser.getHeight());
        Assert.assertNotEquals(user.hashCode(), newUser.hashCode());
    }

    @Test
    public void testParseInLocalDate() throws SQLException, ClassNotFoundException {
        User user = new User();
        System.out.println(user.toString());

        String str = "7.12.2024";

        String[] parts = str.split("\\.");

        int dayOfMonth = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        System.out.println("Day: " + dayOfMonth);
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);

        user.setDateOfBirth(LocalDate.of(year, month, dayOfMonth));

        System.out.println(user.toString());

        Assert.assertTrue(userRepositoryCrud.create(user));
    }

}
