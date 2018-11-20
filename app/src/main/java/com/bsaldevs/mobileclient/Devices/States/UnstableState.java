package com.bsaldevs.mobileclient.Devices.States;

import android.util.Log;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.ConnectedDevice;

public class UnstableState extends State {
    public UnstableState(ConnectedDevice sender, TCPConnection connection) {
        super(sender, connection);
    }

    @Override
    public void turnOn() {
        Log.d("CDA", sender.getName() + "turnOn device from LockedState");
        //super.turnOn();
        //connection.sendString(device.getName() + "turnOn device from LockedState");
    }

    @Override
    public void turnOff() {
        //Log.d("CDA", device.getName() + "turnOff device from LockedState");
        //connection.sendString(device.getName() + "turnOff device from LockedState");
    }

    @Override
    public void reset() {
        //Log.d("CDA", device.getName() + "reset device from LockedState");
        //connection.sendString(device.getName() + "reset device from LockedState");
    }

    @Override
    public void block() {
        //Log.d("CDA", device.getName() + "block device from LockedState");
        //connection.sendString(device.getName() + "block device from LockedState");
    }
}
