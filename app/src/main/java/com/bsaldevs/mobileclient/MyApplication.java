package com.bsaldevs.mobileclient;

import android.app.Application;

import com.bsaldevs.mobileclient.Devices.Component;
import com.bsaldevs.mobileclient.User.MobileClient;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private MobileClient client;
    private List<Component> components;

    public MyApplication() {
        super();
        components = new ArrayList<>();
    }

    public void setupClient(MobileClient client) {
        this.client = client;
    }

    public MobileClient getClient() {
        return client;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public List<Component> getComponents() {
        return components;
    }
}
