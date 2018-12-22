package com.bsaldevs.mobileclient;

import android.app.Application;

import com.bsaldevs.mobileclient.Devices.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.User.MobileClient;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private MobileClient client;
    private List<SmartDevice> devices;

    public MyApplication() {
        super();
        devices = new ArrayList<>();
    }

    public void setupClient(MobileClient client) {
        this.client = client;
    }

    public MobileClient getClient() {
        return client;
    }

    public void addSmartDevice(SmartDevice device) {
        devices.add(device);
    }

    public void removeSmartDevice(SmartDevice device) {
        devices.remove(device);
    }

    public List<SmartDevice> getSmartDevices() {
        return devices;
    }
}
