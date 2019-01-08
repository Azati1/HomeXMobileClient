package com.bsaldevs.mobileclient.Devices.SmartDevices;

        import com.bsaldevs.mobileclient.Devices.DeviceType;
        import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
        import com.bsaldevs.mobileclient.PlaceGroup;

public class Blind extends SmartDevice {

    private static final DeviceType deviceType = DeviceType.JALOUSIE;

    public Blind(String name, PlaceGroup placeGroup, TCPConnection connection) {
        super(deviceType, name, placeGroup, connection);
    }

    @Override
    public Class<?> getDisplayActivity() {
        return BLIND_SETTINGS_ACTIVITY;
    }
}
