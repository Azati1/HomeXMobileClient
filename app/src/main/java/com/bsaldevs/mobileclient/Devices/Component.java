package com.bsaldevs.mobileclient.Devices;

import com.bsaldevs.mobileclient.Devices.Abilities.Controllable;

public interface Component extends Controllable {
    void execute();
    String getName();
    int getImageResourceID();
}
