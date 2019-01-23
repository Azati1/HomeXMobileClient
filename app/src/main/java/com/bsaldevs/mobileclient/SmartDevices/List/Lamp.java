package com.bsaldevs.mobileclient.SmartDevices.List;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.Abilities.IntensityChangeable;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.SmartDevices.Abilities.ColorChangeable;

public class Lamp extends SmartDevice implements ColorChangeable, IntensityChangeable {

    private static final DeviceType deviceType = DeviceType.LAMP;
    private int lightColor;
    private float warmDegrees;
    private float intensity;

    public Lamp(String name, TCPConnection connection) {
        super(deviceType, name, connection);
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
    public Class<?> getDisplayActivity() {
        return LAMP_SETTINGS_ACTIVITY;
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