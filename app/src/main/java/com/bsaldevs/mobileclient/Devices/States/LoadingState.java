package com.bsaldevs.mobileclient.Devices.States;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.SmartDevice;

public class LoadingState extends State {

    public LoadingState(SmartDevice sender, TCPConnection connection) {
        super(sender, connection);
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