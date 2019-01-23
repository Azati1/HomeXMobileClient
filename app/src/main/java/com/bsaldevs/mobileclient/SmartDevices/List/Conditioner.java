package com.bsaldevs.mobileclient.SmartDevices.List;

import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.DeviceGroup;

public class Conditioner extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.CONDITIONER;

    public Conditioner(String name, TCPConnection connection) {
        super(deviceType, name, connection);
    }

    @Override
    public Class<?> getDisplayActivity() {
        return CONDITIONER_SETTINGS_ACTIVITY;
    }

}
