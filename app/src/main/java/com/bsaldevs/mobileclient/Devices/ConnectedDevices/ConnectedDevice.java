package com.bsaldevs.mobileclient.Devices.ConnectedDevices;

import com.bsaldevs.mobileclient.Devices.Component;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.Abilities.Controllable;
import com.bsaldevs.mobileclient.Devices.States.LoadingState;
import com.bsaldevs.mobileclient.Devices.States.LockedState;
import com.bsaldevs.mobileclient.Devices.States.ReadyState;
import com.bsaldevs.mobileclient.Devices.States.SwitchOffState;
import com.bsaldevs.mobileclient.Devices.States.State;

public abstract class ConnectedDevice implements Component {

    private String name;
    private int id;
    private State state;
    private TCPConnection connection;
    private static int counter;

    public ConnectedDevice(String name, final TCPConnection connection) {
        this.id = counter++;
        this.name = name;
        this.connection = connection;
        state = new LoadingState(this, connection);
        load();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    private void changeState(State state) {
        this.state = state;
    }

    @Override
    public void turnOn() {
        state.turnOn();
        changeState(new ReadyState(this, connection));
    }

    @Override
    public void turnOff() {
        state.turnOff();
        changeState(new SwitchOffState(this, connection));
    }

    @Override
    public void reset() {
        state.reset();
    }

    @Override
    public void block() {
        state.block();
        changeState(new LockedState(this, connection));
    }

    private void load() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO(Принять сообщение о готовности устройства к работе)
                changeState(new ReadyState(ConnectedDevice.this, connection));
            }
        });
        thread.start();
    }
}