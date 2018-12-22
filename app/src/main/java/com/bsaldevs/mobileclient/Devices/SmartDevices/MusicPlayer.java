package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

public class MusicPlayer extends SmartDevice {
    public MusicPlayer(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return MUSICPLAYER_IMG_RES_ID;
    }
}
