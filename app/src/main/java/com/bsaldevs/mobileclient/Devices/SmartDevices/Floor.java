package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Devices.DeviceType;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;

public class Floor extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.FLOOR;

    public Floor(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return FLOOR_IMG_RES_ID;
    }

    @Override
    public Class<?> getDisplayActivity() {
        return FLOOR_SETTINGS_ACTIVITY;
    }

}
