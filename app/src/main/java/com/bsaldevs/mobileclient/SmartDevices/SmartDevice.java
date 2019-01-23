package com.bsaldevs.mobileclient.SmartDevices;

import com.bsaldevs.mobileclient.DeviceGroup;
import com.bsaldevs.mobileclient.SmartDevices.Abilities.Controllable;
import com.bsaldevs.mobileclient.SmartDevices.List.Camera;
import com.bsaldevs.mobileclient.SmartDevices.List.Conditioner;
import com.bsaldevs.mobileclient.SmartDevices.List.Heaters;
import com.bsaldevs.mobileclient.SmartDevices.List.Hoover;
import com.bsaldevs.mobileclient.SmartDevices.List.Jalousie;
import com.bsaldevs.mobileclient.SmartDevices.List.Kettle;
import com.bsaldevs.mobileclient.SmartDevices.List.Lamp;
import com.bsaldevs.mobileclient.SmartDevices.List.Locker;
import com.bsaldevs.mobileclient.SmartDevices.List.MusicPlayer;
import com.bsaldevs.mobileclient.SmartDevices.List.Socket;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.SmartDevices.States.LoadingState;
import com.bsaldevs.mobileclient.SmartDevices.States.LockedState;
import com.bsaldevs.mobileclient.SmartDevices.States.ReadyState;
import com.bsaldevs.mobileclient.SmartDevices.States.SwitchOffState;
import com.bsaldevs.mobileclient.SmartDevices.States.State;

public abstract class SmartDevice implements Controllable, SmartDeviceDisplay {

    private static int counter;

    private DeviceGroup deviceGroup;
    private TCPConnection connection;
    private String name;
    private int id;
    private State state;
    private DeviceType deviceType;

    public SmartDevice(DeviceType deviceType, String name, TCPConnection connection) {
        this.id = counter++;
        this.connection = connection;
        this.name = name;
        this.deviceType = deviceType;
        state = new LoadingState(this);
        load();
    }

    public TCPConnection getConnection() {
        return connection;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    private void setState(State state) {
        this.state = state;
    }

    @Override
    public void turnOn() {
        state.turnOn();
        setState(new ReadyState(this));
    }

    @Override
    public void turnOff() {
        state.turnOff();
        setState(new SwitchOffState(this));
    }

    @Override
    public void reset() {
        state.reset();
    }

    @Override
    public void block() {
        state.block();
        setState(new LockedState(this));
    }

    private void load() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO(Принять сообщение о готовности устройства к работе)
                setState(new ReadyState(SmartDevice.this));
            }
        });
        thread.start();
    }

    public boolean isEnabled() {
        if (state.getState() == State.ENABLED)
            return true;
        else
            return false;
    }

    public static SmartDevice create(DeviceType deviceType, String name, DeviceGroup deviceGroup, TCPConnection connection) throws Exception {

        SmartDevice smartDevice;

        switch (deviceType) {
            case LAMP: {
                smartDevice = new Lamp(name, connection);
                break;
            }

            case CAMERA: {
                smartDevice = new Camera(name, connection);
                break;
            }

            case HOOVER: {
                smartDevice = new Hoover(name, connection);
                break;
            }

            case KETTLE: {
                smartDevice = new Kettle(name, connection);
                break;
            }

            case LOCKER: {
                smartDevice = new Locker(name, connection);
                break;
            }

            case MUSIC_PLAYER: {
                smartDevice = new MusicPlayer(name, connection);
                break;
            }

            case SOCKET: {
                smartDevice = new Socket(name, connection);
                break;
            }

            case HEATERS: {
                smartDevice = new Heaters(name, connection);
                break;
            }

            case JALOUSIE: {
                smartDevice = new Jalousie(name, connection);
                break;
            }

            case CONDITIONER: {
                smartDevice = new Conditioner(name, connection);
                break;
            }

            default: {
                throw new Exception("SmartDevice creating exception");
            }
        }

        return smartDevice;
    }
}