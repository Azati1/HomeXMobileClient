package com.bsaldevs.mobileclient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SmartDeviceManagerTest {

    private SmartDeviceManager smartDeviceManager;

    @Before
    public void setUp() throws Exception {
        smartDeviceManager = new SmartDeviceManager();
        smartDeviceManager.addDeviceGroup(new DeviceGroup("name", 123));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldThrownExceptionWhenNameDoesNotExistTest() {
        try {
            smartDeviceManager.getDeviceGroup("");
            fail("Excepted exception not thrown");
        } catch (Exception e) {

        }
    }

    @Test
    public void getDeviceGroupTest() {
        try {
            smartDeviceManager.getDeviceGroup("name");
        } catch (Exception e) {

        }
    }
}