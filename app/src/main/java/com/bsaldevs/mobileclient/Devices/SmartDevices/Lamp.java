package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.DeviceType;
import com.bsaldevs.mobileclient.Devices.Abilities.IntensityChangeable;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.Abilities.ColorChangeable;
import com.bsaldevs.mobileclient.PlaceGroup;

public class Lamp extends SmartDevice implements ColorChangeable, IntensityChangeable {

    private static final DeviceType deviceType = DeviceType.LAMP;
    private int lightColor;
    private float warmDegrees;
    private float intensity;

    public Lamp(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
        lightColor = 0;
        warmDegrees = 0;
        intensity = 0;
    }

    @Override
    public void changeColor(int color) {
        lightColor = color;
    }

    @Override
    public int getColor() {
        return lightColor;
    }

    @Override
    public int getImageResourceID() {
        return LAMP_IMG_RES_ID;
    }

    @Override
    public void setWarmDegrees(float degrees) {
        this.warmDegrees = degrees;
    }

    @Override
    public float getWarmDegrees() {
        return warmDegrees;
    }

    @Override
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    @Override
    public float getIntensity() {
        return intensity;
    }
}