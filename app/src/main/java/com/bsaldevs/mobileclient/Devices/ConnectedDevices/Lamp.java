package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import android.graphics.Color;

import com.bsaldevs.mobileclient.Devices.Abilities.IntensityChangeable;
import com.bsaldevs.mobileclient.Devices.Abilities.ColorWarmChangeable;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.Abilities.ColorChangeable;
import com.bsaldevs.mobileclient.R;

public class Lamp extends ConnectedDevice implements ColorChangeable, IntensityChangeable {

    private int lightColor;
    private float warmDegrees;
    private float intensity;

    public Lamp(String name, TCPConnection connection) {
        super(name, connection);
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
    public void execute() {

    }

    @Override
    public int getImageResourceID() {
        return R.drawable.ic_baseline_highlight_24px;
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