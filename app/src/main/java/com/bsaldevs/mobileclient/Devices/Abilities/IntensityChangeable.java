package com.bsaldevs.mobileclient.Devices.Abilities;

public interface IntensityChangeable {
    float MAX_INTENSITY = 1.0f;
    float MIN_INTENSITY = 0f;

    void setIntensity(float intensity);
    float getIntensity();
}
