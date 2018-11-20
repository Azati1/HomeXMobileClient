package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import com.bsaldevs.mobileclient.Devices.Component;

import java.util.ArrayList;
import java.util.List;

public class CompositeDevice implements Component {

    private List<Component> components;
    private String name;

    public CompositeDevice(String groupName) {
        components = new ArrayList<>();
        name = groupName;
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

    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getImageResourceID() {
        return 0;
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

    /*public void execute() {
        for (ConnectedDevice device : devices) {
            device.execute();
        }
    }*/
}
