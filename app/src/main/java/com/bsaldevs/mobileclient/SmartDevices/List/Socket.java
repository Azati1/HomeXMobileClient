package com.bsaldevs.mobileclient.SmartDevices.List;

import com.bsaldevs.mobileclient.DeviceGroup;
import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;

public class Socket extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.SOCKET;

    public Socket(String name, TCPConnection connection) {
        super(deviceType, name, connection);
    }

    @Override
    public Class<?> getDisplayActivity() {
        return SOCKET_SETTINGS_ACTIVITY;
    }
}
