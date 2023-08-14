package com.example.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsersDataModelTest {

    UsersDataModel user_data_model = new UsersDataModel("test@gmail.com", "Test", "User");

    @Test
    public void getEmail() {
        assertEquals("test@gmail.com", user_data_model.getEmail());
    }


    @Test
    public void getUsername() {
        assertEquals("Test", user_data_model.getUsername());
    }

    @Test
    public void getProfile() {
        assertEquals("User", user_data_model.getProfile());
    }

}