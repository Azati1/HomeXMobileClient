package com.bsaldevs.mobileclient.Devices.Abilities;

public interface ColorChangeable extends ColorWarmChangeable {
    void changeColor(int color);
    int getColor();
}
