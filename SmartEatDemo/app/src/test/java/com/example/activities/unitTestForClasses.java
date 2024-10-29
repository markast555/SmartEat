package com.example.activities;

import org.junit.Test;

import static org.junit.Assert.*;

import classes.User;
import classes.UserRepositoryCrud;

public class unitTestForClasses {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void TestSetConnection(){
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "19198";

        UserRepositoryCrud userRepositoryCrud = new UserRepositoryCrud();

        userRepositoryCrud.setConnectionParameters(url, user, password);
        User user1 = new User();

        userRepositoryCrud.setConnection();

        //Assert.assertNotNull(userRepositoryCrud.getConnection());
    }

}