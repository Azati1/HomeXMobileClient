package com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.bsaldevs.mobileclient.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static org.junit.Assert.*;

public class LampSettingsActivityTest {

    @Rule
    public ActivityTestRule<LampSettingsActivity> activityActivityTestRule = new ActivityTestRule<LampSettingsActivity>(LampSettingsActivity.class);

    private LampSettingsActivity activity = null;

    @Before
    public void setUp() throws Exception {
        activity = activityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = activity.findViewById(R.id.button2);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}