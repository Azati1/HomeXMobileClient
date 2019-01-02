package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Devices.DeviceType;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;

public class Socket extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.SOCKET;

    public Socket(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return SOCKET_IMG_RES_ID;
    }

    @Override
    public Class<?> getDisplayActivity() {
        return SOCKET_SETTINGS_ACTIVITY;
    }
}
