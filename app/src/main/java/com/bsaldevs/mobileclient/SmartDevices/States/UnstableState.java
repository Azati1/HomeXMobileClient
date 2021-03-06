package com.bsaldevs.mobileclient.SmartDevices.States;

import android.util.Log;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;

public class UnstableState extends State {
    public UnstableState(SmartDevice sender) {
        super(sender);
    }

    @Override
    public void turnOn() {
        Log.d("CDA", sender.getName() + "turnOn device from LockedState");
        //super.turnOn();
        //connection.sendString(device.getDeviceName() + "turnOn device from LockedState");
    }

    @Override
    public void turnOff() {
        //Log.d("CDA", device.getDeviceName() + "turnOff device from LockedState");
        //connection.sendString(device.getDeviceName() + "turnOff device from LockedState");
    }

    @Override
    public void reset() {
        //Log.d("CDA", device.getDeviceName() + "reset device from LockedState");
        //connection.sendString(device.getDeviceName() + "reset device from LockedState");
    }

    @Override
    public void block() {
        //Log.d("CDA", device.getDeviceName() + "block device from LockedState");
        //connection.sendString(device.getDeviceName() + "block device from LockedState");
    }
}
