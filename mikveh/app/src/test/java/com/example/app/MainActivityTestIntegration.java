package com.example.app;

import static org.junit.Assert.assertEquals;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

@Config(sdk = 29, packageName="com.example.app")
@RunWith(RobolectricTestRunner.class)
public class MainActivityTestIntegration {
    @Test
    public void clickingGetStarted() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.startButton).performClick();

        Intent expectedIntent = new Intent(activity, SignUpActivity.class);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());


    }
}