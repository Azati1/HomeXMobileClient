package com.bsaldevs.mobileclient.Devices.States;

import android.util.Log;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.SmartDevice;

public class WorkingState extends State {

    public WorkingState(SmartDevice sender, TCPConnection connection) {
        super(sender, connection);
    }

    @Override
    public void turnOn() {
        Log.d("CDA", sender.getName() + "turnOn device from WorkingState");
    }

    @Override
    public void turnOff() {
        Log.d("CDA", sender.getName() + "turnOff device from WorkingState");
    }

    @Override
    public void reset() {
        Log.d("CDA", sender.getName() + "reset device from WorkingState");
    }

    @Override
    public void block() {
        Log.d("CDA", sender.getName() + "block device from WorkingState");
    }

}