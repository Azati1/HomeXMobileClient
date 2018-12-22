package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

public class Locker extends SmartDevice {

    public Locker(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return LOCKER_IMG_RES_ID;
    }
}
