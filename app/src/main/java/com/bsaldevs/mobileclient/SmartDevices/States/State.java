package com.bsaldevs.mobileclient.SmartDevices.States;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.SmartDevices.Abilities.Controllable;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Net.Request;

public abstract class State implements Controllable {

    public static final int ENABLED = 1;
    public static final int DISABLED = 2;
    public static final int RESETED = 3;
    public static final int BLOCKED = 4;
    public static final int LOADED = 5;

    protected TCPConnection connection;
    protected SmartDevice sender;
    protected int currentState;

    @Override
    public void turnOn() {
        Request request = new Request(sender.getName(), "server", "turnOn", new String[]{});
        connection.sendRequest(request);
        currentState = ENABLED;
    }

    @Override
    public void turnOff() {
        Request request = new Request(sender.getName(), "server", "turnOff", new String[]{});
        connection.sendRequest(request);
        currentState = DISABLED;
    }

    @Override
    public void reset() {
        Request request = new Request(sender.getName(), "server", "reset", new String[]{});
        connection.sendRequest(request);
        currentState = RESETED;
    }

    @Override
    public void block() {
        Request request = new Request(sender.getName(), "server", "block", new String[]{});
        connection.sendRequest(request);
        currentState = BLOCKED;
    }

    public State(SmartDevice smartDevice) {
        this.sender = smartDevice;
        this.connection = smartDevice.getConnection();
    }

    public int getState() {
        return currentState;
    }
}