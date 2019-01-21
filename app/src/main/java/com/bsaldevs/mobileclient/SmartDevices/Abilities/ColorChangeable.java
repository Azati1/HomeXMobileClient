package com.bsaldevs.mobileclient.SmartDevices.Abilities;

public interface ColorChangeable extends ColorWarmChangeable {
    void changeColor(int color);
    int getColor();
}
