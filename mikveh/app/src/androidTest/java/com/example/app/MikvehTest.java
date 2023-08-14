package com.example.app;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
public class MikvehTest {
    private Mikveh mikveh;

    @Before
    public void setUp() throws Exception {
        Context ctx = InstrumentationRegistry.getContext();
        mikveh = new Mikveh();
        mikveh.geoUtils(ctx);
    }

    @Test
    public void getCurrentZipCode() throws Exception{
        String zipcode = mikveh.getCurrentZipCode(36.139017,-86.796924);
        //String zipcode = mikveh.getCurrentZipCode(31.2522458, 34.7947656);
        Log.d("Zip", zipcode);
        assertEquals("37212", zipcode);
    }
}