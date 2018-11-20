package com.bsaldevs.mobileclient.Devices.States;

import android.util.Log;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.ConnectedDevice;

public class ReadyState extends State {

    public ReadyState(ConnectedDevice sender, TCPConnection connection) {
        super(sender, connection);
    }

    @Override
    public void turnOn() {
        Log.d("CDA", sender.getName() + "already turnOn");
    }

    @Override
    public void turnOff() {
        Log.d("CDA", sender.getName() + "turnOff device from ReadyState");
        super.turnOff();
    }

    @Override
    public void reset() {
        Log.d("CDA", sender.getName() + "reset device from ReadyState");
        super.reset();
    }

    @Override
    public void block() {
        Log.d("CDA", sender.getName() + "block device from ReadyState");
        super.block();
    }

}