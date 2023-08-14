package com.example.app;

import static org.junit.Assert.*;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

@Config(sdk = 29, packageName="com.example.app")
@RunWith(RobolectricTestRunner.class)
public class SignUpActivityTestIntegration {
    @Test
    public void clickingAlreadySigned() {
        SignInActivity activityB = Robolectric.setupActivity(SignInActivity.class);

        SignUpActivity activity = Robolectric.setupActivity(SignUpActivity.class);
        activity.findViewById(R.id.sign_in_option).performClick();

        Intent expectedIntent = new Intent(activity, SignInActivity.class);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}