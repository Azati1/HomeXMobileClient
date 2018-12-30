package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Activities.MusicPlayerSettingsActivity;
import com.bsaldevs.mobileclient.DeviceType;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;

public class Locker extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.LOCKER;

    public Locker(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return LOCKER_IMG_RES_ID;
    }

    @Override
    public Class<?> getDisplayActivity() {
        return LOCKER_SETTINGS_ACTIVITY;
    }
}
