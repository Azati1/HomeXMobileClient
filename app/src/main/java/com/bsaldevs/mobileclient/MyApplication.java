package com.bsaldevs.mobileclient;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.User.Account;
import com.bsaldevs.mobileclient.User.Mobile;
import com.bsaldevs.mobileclient.User.MobileClient;
import com.bsaldevs.mobileclient.User.UserDevice;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static final String APP_PREFERENCES = "USER_DATA";

    private MobileClient client;
    private TCPConnection connection;
    private AccountManager accountManager;
    private SmartDeviceManager smartDeviceManager;
    private SharedPreferences sharedPreferences;

    public MyApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        this.sharedPreferences = getSharedPreferences(APP_PREFERENCES, getApplicationContext().MODE_PRIVATE);
        this.accountManager = new AccountManager(this, sharedPreferences);
        this.smartDeviceManager = new SmartDeviceManager(this);
        smartDeviceManager.load(connection);
        setupClient();
        this.connection = client.getConnection();
        Log.d("CDA_MA", "onCreate MyApplication");
    }

    public void setupClient() {
        UserDevice mobile = new Mobile("username");

        String ip100 = "192.168.0.100";
        String ip101 = "192.168.0.101";
        String ip = "18.216.150.250";
        int port = 3346;

        this.client = new MobileClient(ip, port, mobile);

        Log.d("CDA_MA", "setupClient");
    }

    public void connect() {
        client.connect();
    }

    public RequestPoll getRequestPoll() {
        return client.getRequestPoll();
    }

    public boolean isUserLoggedIn() {
        Account account = accountManager.getCurrentAccount();
        if (account == null)
            return false;
        return true;
    }

    public Account getAccount() {
        return accountManager.getCurrentAccount();
    }

    public MobileClient getClient() {
        return client;
    }

    public void login(Account account) {
        accountManager.login(account);
    }

    public void logout() {
        accountManager.logout();
    }

    public void initAccount() throws Exception {
        accountManager.init();
    }

    public void reconnect() {
        connect();
    }

    public DeviceGroup getDeviceGroup(String groupName) throws Exception {
        return smartDeviceManager.getDeviceGroup(groupName);
    }

    public List<DeviceGroup> getGroups() {
        return smartDeviceManager.getDeviceGroups();
    }

    public List<SmartDevice> getSmartDevices(DeviceType deviceType, DeviceGroup deviceGroup) {
        return smartDeviceManager.getSmartDevices(deviceType, deviceGroup);
    }

    public void addSmartDevice(SmartDevice smartDevice) {
        smartDeviceManager.addSmartDevice(smartDevice);
    }

}
