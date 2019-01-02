package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Devices.DeviceType;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

public class Camera extends SmartDevice {
    public Camera(DeviceType deviceType, String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return R.drawable.ic_security_cam;
    }

    @Override
    public Class<?> getDisplayActivity() {
        return SOCKET_SETTINGS_ACTIVITY;
    }
}
