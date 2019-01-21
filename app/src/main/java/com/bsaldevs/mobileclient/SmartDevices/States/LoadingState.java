package com.bsaldevs.mobileclient.SmartDevices.States;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;

public class LoadingState extends State {

    public LoadingState(SmartDevice sender, TCPConnection connection) {
        super(sender, connection);
        currentState = LOADED;
    }

    @Override
    public void turnOn() {

    }

    @Override
    public void turnOff() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void block() {

    }

}