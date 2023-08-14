package com.example.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class dataUserTest {

    dataUser data_user = new dataUser("אחד העם 17", "באר שבע" , "13:00", "יום רביעי, יוני 22, 2022", "Shira", "");

    @Test
    public void getAddress() {
        assertEquals("אחד העם 17", data_user.getAddress());
    }

    @Test
    public void getCity() {
        assertEquals("באר שבע", data_user.getCity() );
    }

    @Test
    public void getTime() {
        assertEquals("13:00", data_user.getTime());
    }

    @Test
    public void getDate() {
        assertEquals("יום רביעי, יוני 22, 2022", data_user.getDate());
    }

    @Test
    public void getName() {
        assertEquals("Shira", data_user.getName());
    }

    @Test
    public void getID() {
        assertEquals("", data_user.getID());
    }

}