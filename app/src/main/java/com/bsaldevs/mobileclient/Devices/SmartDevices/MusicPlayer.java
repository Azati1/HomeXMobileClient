package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.DeviceType;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;

public class MusicPlayer extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.PLAYER;

    public MusicPlayer(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return MUSICPLAYER_IMG_RES_ID;
    }

    @Override
    public Class<?> getDisplayActivity() {
        return MUSICPLAYER_SETTINGS_ACTIVITY;
    }
}
