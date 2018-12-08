package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import com.bsaldevs.mobileclient.Devices.Abilities.Controllable;
import com.bsaldevs.mobileclient.Devices.Component;
import com.bsaldevs.mobileclient.Devices.States.LoadingState;
import com.bsaldevs.mobileclient.Devices.States.State;

import java.util.ArrayList;
import java.util.List;

public class DeviceGroup implements Controllable {

    private List<Component> components;
    private String name;
    private State state;

    public DeviceGroup(String compositeName) {
        components = new ArrayList<>();
        name = compositeName;
        //state = new LoadingState(this, connection);
    }

    public void add(Component device) {
        components.add(device);
    }

    public void remove(Component device) {
        components.remove(device);
    }

    public List<Component> getComponents() {
        return components;
    }

    public String getName() {
        return name;
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
