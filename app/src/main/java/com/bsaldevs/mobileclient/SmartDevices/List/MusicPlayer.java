package com.bsaldevs.mobileclient.SmartDevices.List;

import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.DeviceGroup;

public class MusicPlayer extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.MUSIC_PLAYER;

    public MusicPlayer(String name, TCPConnection connection) {
        super(deviceType, name, connection);
    }

    @Override
    public Class<?> getDisplayActivity() {
        return MUSIC_PLAYER_SETTINGS_ACTIVITY;
    }
}
