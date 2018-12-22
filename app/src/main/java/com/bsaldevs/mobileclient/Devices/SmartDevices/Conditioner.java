package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.PlaceGroup;
import com.bsaldevs.mobileclient.R;

public class Conditioner extends SmartDevice {
    public Conditioner(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(name, placeGroup, connection);
    }

    @Override
    public int getImageResourceID() {
        return CONDITIONER_IMG_RES_ID;
    }

}
