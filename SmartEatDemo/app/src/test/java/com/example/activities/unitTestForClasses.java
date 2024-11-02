package com.example.activities;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import classes.DatabaseParams;
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
    public void testSetConnection() {
        userRepositoryCrud.setConnection();
        Assert.assertNotNull(userRepositoryCrud.getConnection());
    }

    @Test
    @DisplayName("Тестирование прерывания соединения с БД")
    public void testCloseConnection() {
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
    @DisplayName("Тестирование генератора случайного номера телефона для будущих тестов")
    public void testGenerateRandomPhoneNumber() {
        String phoneNumber;
        phoneNumber = VariableGenerator.generateRandomPhoneNumber();
        Assert.assertNotNull(phoneNumber);
        Assert.assertEquals(16, phoneNumber.length());
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
    @DisplayName("Тестирование создания кортежа пользователя в табл. user_se в БД")
    public void testCreateUserInBD() {
        User user = new User();
        System.out.println(user.toString());
        Assert.assertTrue(userRepositoryCrud.create(user));
    }

    @Test
    @DisplayName("Тестирование нахождения пользователя по логину в табл. user_se в БД")
    public void testSelectByLoginInBD() {
        String login = "hB0RI4z";
        User user = userRepositoryCrud.selectByLogin(login);
        Assert.assertNotNull(user);
    }



//    @Test
//    @DisplayName("Тестирование хеширования пароля (негативный тест)")
//    public void testHashPasswordNegative(){
//        String originalPassword = "AnyPassword";
//        String hashedPassword = VariableGenerator.hashPassword(originalPassword);
//        boolean isMatch = VariableGenerator.checkPassword("wrongPassword", hashedPassword);
//        Assert.assertFalse(isMatch);
//    }


}
