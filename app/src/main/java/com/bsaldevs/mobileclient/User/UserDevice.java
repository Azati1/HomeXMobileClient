package com.bsaldevs.mobileclient.User;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;

public abstract class UserDevice {

    protected String name;

    public abstract void waitMessage(TCPConnection connection);

    public UserDevice(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}