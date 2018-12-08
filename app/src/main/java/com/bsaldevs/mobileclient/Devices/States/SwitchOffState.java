package com.bsaldevs.mobileclient.Devices.States;

import android.util.Log;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.SmartDevice;

public class SwitchOffState extends State {

    public SwitchOffState(SmartDevice sender, TCPConnection connection) {
        super(sender, connection);
    }

    @Override
    public void turnOn() {
        Log.d("CDA", sender.getName() + "turnOn device from SwitchOffState");
        super.turnOn();
    }

    @Override
    public void turnOff() {
        Log.d("CDA", sender.getName() + "already turnOff");
    }

    @Override
    public void reset() {
        Log.d("CDA", sender.getName() + "reset device from SwitchOffState");
    }

    @Override
    public void block() {
        Log.d("CDA", sender.getName() + "block device from SwitchOffState");
        super.block();
    }

}