package com.bsaldevs.mobileclient.Devices.SmartDevices;

import com.bsaldevs.mobileclient.Devices.DeviceType;
import com.bsaldevs.mobileclient.Devices.Abilities.Controllable;
import com.bsaldevs.mobileclient.Devices.SmartDeviceDisplay;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Devices.States.LoadingState;
import com.bsaldevs.mobileclient.Devices.States.LockedState;
import com.bsaldevs.mobileclient.Devices.States.ReadyState;
import com.bsaldevs.mobileclient.Devices.States.SwitchOffState;
import com.bsaldevs.mobileclient.Devices.States.State;
import com.bsaldevs.mobileclient.PlaceGroup;

public abstract class SmartDevice implements Controllable, SmartDeviceDisplay {

    private String name;
    private int id;
    private State state;
    private TCPConnection connection;
    private static int counter;
    private PlaceGroup place;
    private DeviceType deviceType;
    private int currentState;

    public SmartDevice(DeviceType deviceType, String name, PlaceGroup placeGroup, final TCPConnection connection) {
        this.id = counter++;
        this.name = name;
        this.place = placeGroup;
        this.deviceType = deviceType;
        this.connection = connection;
        state = new LoadingState(this, connection);
        load();
        placeGroup.addSmartDevice(this);
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
                changeState(new ReadyState(SmartDevice.this, connection));
            }
        });
        thread.start();
    }

    public int getState() {
        return currentState;
    }

    public PlaceGroup placedIn() {
        return place;
    }

    public boolean isEnabled() {
        if (state.getState() == State.ENABLED)
            return true;
        else
            return false;
    }

    @Override
    public int getImageResourceID() {
        return deviceType.getDeviceImage();
    }

    public static SmartDevice create(DeviceType deviceType, String name, PlaceGroup placeGroup, TCPConnection connection) throws Exception {

        SmartDevice smartDevice = null;

        switch (deviceType) {
            case LAMP: {
                smartDevice = new Lamp(name, placeGroup, connection);
                break;
            }

            case CAMERA: {
                smartDevice = new Camera(name, placeGroup, connection);
                break;
            }

            case HOOVER: {
                smartDevice = new Hoover(name, placeGroup, connection);
                break;
            }

            case KETTLE: {
                smartDevice = new Kettle(name, placeGroup, connection);
                break;
            }

            case LOCKER: {
                smartDevice = new Locker(name, placeGroup, connection);
                break;
            }

            case MUSIC_PLAYER: {
                smartDevice = new MusicPlayer(name, placeGroup, connection);
                break;
            }

            case SOCKET: {
                smartDevice = new Socket(name, placeGroup, connection);
                break;
            }

            case HEATERS: {
                smartDevice = new Heaters(name, placeGroup, connection);
                break;
            }

            case JALOUSIE: {
                smartDevice = new Jalousie(name, placeGroup, connection);
                break;
            }

            case CONDITIONER: {
                smartDevice = new Conditioner(name, placeGroup, connection);
                break;
            }

            default: {
                throw new Exception("SmartDevice creating exception");
            }
        }

        return smartDevice;
    }
}