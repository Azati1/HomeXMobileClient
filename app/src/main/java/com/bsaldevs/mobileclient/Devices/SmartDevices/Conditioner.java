package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Activities.MusicPlayerSettingsActivity;
import com.bsaldevs.mobileclient.DeviceType;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;

public class Conditioner extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.CONDITIONER;

    public Conditioner(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return CONDITIONER_IMG_RES_ID;
    }

    @Override
    public Class<?> getDisplayActivity() {
        return CONDITIONER_SETTINGS_ACTIVITY;
    }

}
