package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.R;

public class Locker extends ConnectedDevice {

    public Locker(String name, TCPConnection connection) {
        super(name, connection);
    }

    @Override
    public void execute() {

    }

    @Override
    public int getImageResourceID() {
        return R.drawable.ic_baseline_lock_24px;
    }
}
