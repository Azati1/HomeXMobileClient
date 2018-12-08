package com.bsaldevs.mobileclient.Devices.States;

import com.bsaldevs.mobileclient.Net.Command;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.Abilities.Controllable;
import com.bsaldevs.mobileclient.Devices.ConnectedDevices.SmartDevice;
import com.bsaldevs.mobileclient.Net.Request;

public abstract class State implements Controllable {

    protected SmartDevice sender;
    protected TCPConnection connection;

    @Override
    public void turnOn() {
        Command command = new Command("turnOn", new String[]{""});
        Request request = new Request(sender.getName(), sender.getName(), command);
        connection.sendRequest(request);
    }

    @Override
    public void turnOff() {
        Command command = new Command("turnOff", new String[]{""});
        Request request = new Request(sender.getName(), sender.getName(), command);
        connection.sendRequest(request);
    }

    @Override
    public void reset() {
        Command command = new Command("reset", new String[]{""});
        Request request = new Request(sender.getName(), sender.getName(), command);
        connection.sendRequest(request);
    }

    @Override
    public void block() {
        Command command = new Command("block", new String[]{""});
        Request request = new Request(sender.getName(), sender.getName(), command);
        connection.sendRequest(request);
    }

    public State(SmartDevice sender, TCPConnection connection) {
        this.sender = sender;
        this.connection = connection;
    }
}