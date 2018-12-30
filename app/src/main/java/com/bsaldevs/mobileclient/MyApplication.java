package com.bsaldevs.mobileclient;

import android.app.Application;

import com.bsaldevs.mobileclient.Devices.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.User.MobileClient;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private MobileClient client;
    private List<SmartDevice> devices;
    private List<PlaceGroup> placeGroups;

    public MyApplication() {
        super();
        devices = new ArrayList<>();
        placeGroups = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
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

    public void addPlaceGroup(PlaceGroup placeGroup) { placeGroups.add(placeGroup); }

    public void removeSmartDevice(SmartDevice device) {
        devices.remove(device);
    }

    public List<SmartDevice> getSmartDevices() {
        return devices;
    }

    public List<SmartDevice> getSmartDevices(DeviceType deviceType) {

        List<SmartDevice> smartDevices = new ArrayList<>();

        switch (deviceType) {
            case LAMP:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.LAMP)
                        smartDevices.add(device);
                }
                break;
            case CONDITIONER:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.CONDITIONER)
                        smartDevices.add(device);
                }
                break;
            case LOCKER:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.LOCKER)
                        smartDevices.add(device);
                }
                break;
            case THERMOMETER:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.THERMOMETER)
                        smartDevices.add(device);
                }
                break;
            case PLAYER:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.PLAYER)
                        smartDevices.add(device);
                }
                break;
            case SOCKET:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.SOCKET)
                        smartDevices.add(device);
                }
                break;
        }

        return smartDevices;
    }

    public List<SmartDevice> getSmartDevices(DeviceType deviceType, PlaceGroup placeGroup) {
        List<SmartDevice> smartDevicesInside = new ArrayList<>();

        for (int i = 0; i < placeGroups.size(); i++) {
            if (placeGroup.getName().equals(placeGroups.get(i).getName())) {
                smartDevicesInside.addAll(placeGroups.get(i).getDevicesInside());
            }
        }

        List<SmartDevice> smartDevicesResult = new ArrayList<>();

        for (int i = 0; i < smartDevicesInside.size(); i++) {
            if (smartDevicesInside.get(i).getDeviceType() == deviceType)
                smartDevicesResult.add(smartDevicesInside.get(i));
        }

        return smartDevicesResult;

    }

    public List<PlaceGroup> getPlaceGroups() {
        return placeGroups;
    }

    public PlaceGroup getPlaceGroup(String placeGroupName) {

        PlaceGroup placeGroupResult = null;

        for (PlaceGroup placeGroup : placeGroups) {
            if (placeGroup.getName().equals(placeGroupName))
                placeGroupResult = placeGroup;
        }

        return placeGroupResult;
    }
}
