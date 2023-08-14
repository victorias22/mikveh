package com.example.app;

import static org.junit.Assert.assertEquals;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

// @Config(sdk = 29, packageName="com.example.app")
// @RunWith(RobolectricTestRunner.class)
// public class OwnerMenuAppActivityTestIntegration {
//     @Before
//     public void setUp() throws Exception {

//     }

//     @Test
//     public void clickingAddMikveh() {

//         OwnerMenuAppActivity activity = Robolectric.setupActivity(OwnerMenuAppActivity.class);
//         activity.findViewById(R.id.own_add_btn).performClick();

//         Intent expectedIntent = new Intent(activity, NewMikvehActivity.class);
//         Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();

// //        Log.d("wfff", actualIntent.getComponent().toString());
//         assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
//     }
// }
