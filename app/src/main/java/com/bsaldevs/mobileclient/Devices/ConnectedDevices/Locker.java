package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.R;

public class Locker extends SmartDevice {

    public Locker(String name, TCPConnection connection) {
        super(name, connection);
    }

    @Override
    public int getImageResourceID() {
        return R.drawable.ic_lock;
    }
}
