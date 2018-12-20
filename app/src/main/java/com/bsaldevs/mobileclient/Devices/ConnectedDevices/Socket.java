package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.R;

public class Socket extends SmartDevice {
    public Socket(String name, TCPConnection connection) {
        super(name, connection);
    }

    @Override
    public int getImageResourceID() {
        return R.drawable.ic_socket;
    }
}
