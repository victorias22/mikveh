package com.example.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class MikvehTest {

    Mikveh m = new Mikveh("Habad",
            "Jerusalem",
            "Baka",
            "Trumpledore",
            "",
            "20:00-00:00",
            "20:00-00:00",
            "until 12:00",
            "",
            "Nothing",
            "",
            "Very clean and comfortable",
            "",
            "");
    @Test
    public void getReligious_Council() {
        String c = m.getReligious_Council();
        assertEquals("Habad", c);
    }

    @Test
    public void getCity() {
        assertEquals("Jerusalem", m.getCity());
    }

    @Test
    public void setNeighborhood() {
        m.setNeighborhood("Redon");
        assertEquals("Redon", m.getNeighborhood());

    }

    @Test
    public void getMikve_Address() {
        assertEquals("Trumpledore", m.getMikve_Address());
    }


    @Test
    public void setPhone() {
        m.setPhone("0584792591");
        assertEquals("0584792591", m.getPhone());
    }

    @Test
    public void getOpening_Hours_Summer() {
        assertEquals("20:00-00:00", m.getOpening_Hours_Summer());
    }


    @Test
    public void setOpening_Hours_Winter() {
        m.setOpening_Hours_Winter("2:00-3:00");
        assertNotEquals("20:00-00:00", m.getOpening_Hours_Winter());
    }

    @Test
    public void getOpening_Hours_Holiday_Eve_Shabat_Eve() {
        assertEquals("until 12:00", m.getOpening_Hours_Holiday_Eve_Shabat_Eve());
    }

    @Test
    public void setOpening_Hours_Saturday_Night_Good_Day() {
        m.setOpening_Hours_Saturday_Night_Good_Day("Closed");
        assertNotEquals("Open", m.getOpening_Hours_Saturday_Night_Good_Day());
    }

    @Test
    public void getAccessibility() {
        assertEquals("Nothing", m.getAccessibility());
    }


    @Test
    public void setSchedule_Appointment() {
        m.setSchedule_Appointment("Option available");
        assertEquals("Option available", m.getSchedule_Appointment());
    }

    @Test
    public void getNotes() {
        assertEquals("Very clean and comfortable", m.getNotes());
    }

}