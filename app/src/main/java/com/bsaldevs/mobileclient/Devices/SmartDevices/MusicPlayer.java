package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Devices.DeviceType;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;

public class MusicPlayer extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.PLAYER;

    public MusicPlayer(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return MUSIC_PLAYER_IMG_RES_ID;
    }

    @Override
    public Class<?> getDisplayActivity() {
        return MUSIC_PLAYER_SETTINGS_ACTIVITY;
    }
}
