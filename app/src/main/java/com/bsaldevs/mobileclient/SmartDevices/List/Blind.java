package com.bsaldevs.mobileclient.SmartDevices.List;

        import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
        import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
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
