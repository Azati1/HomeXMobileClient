package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.R;

public class Conditioner extends SmartDevice {
    public Conditioner(String name, TCPConnection connection) {
        super(name, connection);
    }

    @Override
    public int getImageResourceID() {
        return R.drawable.ic_air_conditioner;
    }
}
