package com.bsaldevs.mobileclient;

import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;

import java.util.ArrayList;
import java.util.List;

public class DeviceGroup {

    private String name;
    private int imageResourceID;
    private List<SmartDevice> devicesInside;

    public DeviceGroup(String name, int imageResourceID) {
        this.name = name;
        this.imageResourceID = imageResourceID;
        this.devicesInside = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public List<SmartDevice> getDevicesInside() {
        return devicesInside;
    }

    public void addSmartDevice(SmartDevice device) {
        devicesInside.add(device);
    }

    public void addSmartDevices(List<SmartDevice> devices) {
        devicesInside.addAll(devices);
    }

    public void removeSmartDevice(SmartDevice device) {
        devicesInside.remove(device);
    }

    public void removeSmartDevices(List<SmartDevice> devices) {
        devicesInside.removeAll(devices);
    }

}