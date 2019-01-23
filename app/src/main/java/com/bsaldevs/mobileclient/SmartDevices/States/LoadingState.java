package com.bsaldevs.mobileclient.SmartDevices.States;

import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;

public class LoadingState extends State {

    public LoadingState(SmartDevice sender) {
        super(sender);
        currentState = LOADED;
    }

    @Override
    public void turnOn() {

    }

    @Override
    public void turnOff() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void block() {

    }

}