package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.R;

public class MusicPlayer extends SmartDevice {
    public MusicPlayer(String name, TCPConnection connection) {
        super(name, connection);
    }

    @Override
    public int getImageResourceID() {
        return R.drawable.ic_music_player;
    }
}
