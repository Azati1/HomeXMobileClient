package com.bsaldevs.mobileclient.SmartDevices.Abilities;

public interface IntensityChangeable {
    float MAX_INTENSITY = 1.0f;
    float MIN_INTENSITY = 0f;

    void setIntensity(float intensity);
    float getIntensity();
}
