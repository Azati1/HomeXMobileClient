package com.bsaldevs.mobileclient.SmartDevices.List;

import com.bsaldevs.mobileclient.DeviceGroup;
import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;

public class Hoover extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.HOOVER;

    public Hoover(String name, TCPConnection connection) {
        super(deviceType, name, connection);
    }

    @Override
    public Class<?> getDisplayActivity() {
        return HOOVER_SETTINGS_ACTIVITY;
    }
}
