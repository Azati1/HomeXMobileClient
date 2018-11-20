package com.bsaldevs.mobileclient.User;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;

public class Mobile extends UserDevice {

    public Mobile(String name) {
        super(name);
    }

    @Override
    public void waitMessage(TCPConnection connection) {

    }
}
