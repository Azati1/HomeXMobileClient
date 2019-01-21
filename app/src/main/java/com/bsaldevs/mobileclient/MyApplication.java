package com.bsaldevs.mobileclient;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.List.Blind;
import com.bsaldevs.mobileclient.SmartDevices.List.Conditioner;
import com.bsaldevs.mobileclient.SmartDevices.List.HeatedFloor;
import com.bsaldevs.mobileclient.SmartDevices.List.Hoover;
import com.bsaldevs.mobileclient.SmartDevices.List.Kettle;
import com.bsaldevs.mobileclient.SmartDevices.List.Lamp;
import com.bsaldevs.mobileclient.SmartDevices.List.Locker;
import com.bsaldevs.mobileclient.SmartDevices.List.MusicPlayer;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.SmartDevices.List.Socket;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.User.Account;
import com.bsaldevs.mobileclient.User.Client;
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
    private List<SmartDevice> devices;
    private List<PlaceGroup> placeGroups;
    private AccountManager accountManager;
    private SharedPreferences sharedPreferences;

    public MyApplication() {
        super();
        setupClient();

        this.connection = client.getConnection();

        devices = new ArrayList<>();
        placeGroups = new ArrayList<>();

        loadPlaceGroups();
        loadDevices();
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

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        this.sharedPreferences = getSharedPreferences(APP_PREFERENCES, getApplicationContext().MODE_PRIVATE);
        this.accountManager = new AccountManager(this, sharedPreferences);
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

    public void addSmartDevice(SmartDevice device) {
        devices.add(device);
    }

    public void addPlaceGroup(PlaceGroup placeGroup) { placeGroups.add(placeGroup); }

    public void removeSmartDevice(SmartDevice device) {
        devices.remove(device);
    }

    public List<SmartDevice> getSmartDevices() {
        return devices;
    }

    public List<SmartDevice> getSmartDevices(DeviceType deviceType) {

        List<SmartDevice> smartDevices = new ArrayList<>();

        switch (deviceType) {
            case LAMP:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.LAMP)
                        smartDevices.add(device);
                }
                break;
            case CONDITIONER:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.CONDITIONER)
                        smartDevices.add(device);
                }
                break;
            case LOCKER:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.LOCKER)
                        smartDevices.add(device);
                }
                break;
            case HEATERS:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.HEATERS)
                        smartDevices.add(device);
                }
                break;
            case MUSIC_PLAYER:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.MUSIC_PLAYER)
                        smartDevices.add(device);
                }
                break;
            case SOCKET:
                for (SmartDevice device : devices) {
                    if (device.getDeviceType() == DeviceType.SOCKET)
                        smartDevices.add(device);
                }
                break;
        }

        return smartDevices;
    }

    public List<SmartDevice> getSmartDevices(DeviceType deviceType, PlaceGroup placeGroup) {
        List<SmartDevice> smartDevicesInside = new ArrayList<>();

        for (int i = 0; i < placeGroups.size(); i++) {
            if (placeGroup.getName().equals(placeGroups.get(i).getName())) {
                smartDevicesInside.addAll(placeGroups.get(i).getDevicesInside());
            }
        }

        List<SmartDevice> smartDevicesResult = new ArrayList<>();

        for (int i = 0; i < smartDevicesInside.size(); i++) {
            if (smartDevicesInside.get(i).getDeviceType() == deviceType)
                smartDevicesResult.add(smartDevicesInside.get(i));
        }

        return smartDevicesResult;

    }

    public List<PlaceGroup> getPlaceGroups() {
        return placeGroups;
    }

    public PlaceGroup getPlaceGroup(String placeGroupName) {

        PlaceGroup placeGroupResult = null;

        for (PlaceGroup placeGroup : placeGroups) {
            if (placeGroup.getName().equals(placeGroupName))
                placeGroupResult = placeGroup;
        }

        return placeGroupResult;
    }

    private void loadPlaceGroups() {
        PlaceGroup placeGroupOffice = new PlaceGroup("Кабинет", R.drawable.ic_desk);
        PlaceGroup placeGroupLivingRoom = new PlaceGroup("Гостиная", R.drawable.ic_livingroom);
        PlaceGroup placeGroupBathroom = new PlaceGroup("Ванная", R.drawable.ic_bathtub);
        PlaceGroup placeGroupBedroom = new PlaceGroup("Спальня", R.drawable.ic_bed);
        PlaceGroup placeGroupRestroom = new PlaceGroup("Уборная", R.drawable.ic_toilet);
        PlaceGroup placeGroupKitchen = new PlaceGroup("Кухня", R.drawable.ic_coffee_machine);
        PlaceGroup placeGroupHall = new PlaceGroup("Коридор", R.drawable.ic_shelf);

        addPlaceGroup(placeGroupOffice);
        addPlaceGroup(placeGroupLivingRoom);
        addPlaceGroup(placeGroupBathroom);
        addPlaceGroup(placeGroupBedroom);
        addPlaceGroup(placeGroupRestroom);
        addPlaceGroup(placeGroupKitchen);
        addPlaceGroup(placeGroupHall);
    }

    private void loadDevices() {

        Lamp lamp1 = new Lamp("Настольная лампа 1", placeGroups.get(0), connection);
        Lamp lamp2 = new Lamp("Люстра 1", placeGroups.get(0), connection);
        Lamp lamp3 = new Lamp("Настольная лампа 2", placeGroups.get(1), connection);
        Lamp lamp4 = new Lamp("Люстра 2", placeGroups.get(1), connection);
        Lamp lamp5 = new Lamp("Светильник в ванной", placeGroups.get(2), connection);
        Lamp lamp6 = new Lamp("Ночник", placeGroups.get(3), connection);
        Lamp lamp7 = new Lamp("Настольная лампа 3", placeGroups.get(3), connection);
        Lamp lamp8 = new Lamp("Люстра 3", placeGroups.get(3), connection);
        Lamp lamp9 = new Lamp("Светильник в уборной", placeGroups.get(4), connection);
        Lamp lamp10 = new Lamp("Подсветка на кухе", placeGroups.get(5), connection);
        Lamp lamp11 = new Lamp("Люстра 4", placeGroups.get(5), connection);
        Lamp lamp12 = new Lamp("Бра в коридоре", placeGroups.get(6), connection);

        addSmartDevice(lamp1);
        addSmartDevice(lamp2);
        addSmartDevice(lamp3);
        addSmartDevice(lamp4);
        addSmartDevice(lamp5);
        addSmartDevice(lamp6);
        addSmartDevice(lamp7);
        addSmartDevice(lamp8);
        addSmartDevice(lamp9);
        addSmartDevice(lamp10);
        addSmartDevice(lamp11);
        addSmartDevice(lamp12);

        Socket socket1 = new Socket("Розетка 1", placeGroups.get(0), connection);
        Socket socket2 = new Socket("Розетка 2", placeGroups.get(1), connection);
        Socket socket3 = new Socket("Розетка 3", placeGroups.get(2), connection);
        Socket socket4 = new Socket("Розетка 4", placeGroups.get(2), connection);
        Socket socket5 = new Socket("Розетка 5", placeGroups.get(3), connection);
        Socket socket6 = new Socket("Розетка 6", placeGroups.get(4), connection);
        Socket socket7 = new Socket("Розетка 7", placeGroups.get(4), connection);
        Socket socket8 = new Socket("Розетка 8", placeGroups.get(5), connection);
        Socket socket9 = new Socket("Розетка 9", placeGroups.get(6), connection);
        Socket socket10 = new Socket("Розетка 10", placeGroups.get(6), connection);

        addSmartDevice(socket1);
        addSmartDevice(socket2);
        addSmartDevice(socket3);
        addSmartDevice(socket4);
        addSmartDevice(socket5);
        addSmartDevice(socket6);
        addSmartDevice(socket7);
        addSmartDevice(socket8);
        addSmartDevice(socket9);
        addSmartDevice(socket10);

        Locker locker1 = new Locker("Замок 1", placeGroups.get(0), connection);
        Locker locker2 = new Locker("Замок 2", placeGroups.get(1), connection);
        Locker locker3 = new Locker("Замок 3", placeGroups.get(2), connection);
        Locker locker4 = new Locker("Замок 4", placeGroups.get(3), connection);
        Locker locker5 = new Locker("Замок 5", placeGroups.get(4), connection);
        Locker locker6 = new Locker("Замок 6", placeGroups.get(5), connection);

        addSmartDevice(locker1);
        addSmartDevice(locker2);
        addSmartDevice(locker3);
        addSmartDevice(locker4);
        addSmartDevice(locker5);
        addSmartDevice(locker6);

        Conditioner conditioner1 = new Conditioner("Кондиционер в кабинете", placeGroups.get(0), connection);
        Conditioner conditioner2 = new Conditioner("Кондиционер в гостиной", placeGroups.get(1), connection);
        Conditioner conditioner3 = new Conditioner("Кондиционер в спальне", placeGroups.get(3), connection);
        Conditioner conditioner4 = new Conditioner("Кондиционер на кухне", placeGroups.get(5), connection);

        addSmartDevice(conditioner1);
        addSmartDevice(conditioner2);
        addSmartDevice(conditioner3);
        addSmartDevice(conditioner4);

        MusicPlayer musicPlayer1 = new MusicPlayer("Музыкальный центр", placeGroups.get(0), connection);
        MusicPlayer musicPlayer2 = new MusicPlayer("Музыкальный центр", placeGroups.get(1), connection);
        MusicPlayer musicPlayer3 = new MusicPlayer("Музыкальный центр", placeGroups.get(2), connection);
        MusicPlayer musicPlayer4 = new MusicPlayer("Музыкальный центр", placeGroups.get(3), connection);
        MusicPlayer musicPlayer5 = new MusicPlayer("Музыкальный центр", placeGroups.get(4), connection);
        MusicPlayer musicPlayer6 = new MusicPlayer("Музыкальный центр", placeGroups.get(5), connection);
        MusicPlayer musicPlayer7 = new MusicPlayer("Музыкальный центр", placeGroups.get(6), connection);

        addSmartDevice(musicPlayer1);
        addSmartDevice(musicPlayer2);
        addSmartDevice(musicPlayer3);
        addSmartDevice(musicPlayer4);
        addSmartDevice(musicPlayer5);
        addSmartDevice(musicPlayer6);
        addSmartDevice(musicPlayer7);

        Blind Blind1 = new Blind("Шторы 1", placeGroups.get(0), connection);
        Blind Blind2 = new Blind("Шторы 2", placeGroups.get(1), connection);
        Blind Blind3 = new Blind("Шторы 3", placeGroups.get(3), connection);
        Blind Blind4 = new Blind("Шторы 3", placeGroups.get(5), connection);

        addSmartDevice(Blind1);
        addSmartDevice(Blind2);
        addSmartDevice(Blind3);
        addSmartDevice(Blind4);

        Kettle Kettle1 = new Kettle("Чайник 1", placeGroups.get(0), connection);
        Kettle Kettle2 = new Kettle("Чайник 2", placeGroups.get(3), connection);
        Kettle Kettle3 = new Kettle("Чайник 3", placeGroups.get(5), connection);

        addSmartDevice(Kettle1);
        addSmartDevice(Kettle2);
        addSmartDevice(Kettle3);

        HeatedFloor heatedFloor1 = new HeatedFloor("Подогрев 1", placeGroups.get(0), connection);
        HeatedFloor heatedFloor2 = new HeatedFloor("Подогрев 2", placeGroups.get(1), connection);
        HeatedFloor heatedFloor3 = new HeatedFloor("Подогрев 3", placeGroups.get(2), connection);
        HeatedFloor heatedFloor4 = new HeatedFloor("Подогрев 4", placeGroups.get(3), connection);
        HeatedFloor heatedFloor5 = new HeatedFloor("Подогрев 5", placeGroups.get(4), connection);
        HeatedFloor heatedFloor6 = new HeatedFloor("Подогрев 6", placeGroups.get(5), connection);
        HeatedFloor heatedFloor7 = new HeatedFloor("Подогрев 7", placeGroups.get(6), connection);

        addSmartDevice(heatedFloor1);
        addSmartDevice(heatedFloor2);
        addSmartDevice(heatedFloor3);
        addSmartDevice(heatedFloor4);
        addSmartDevice(heatedFloor5);
        addSmartDevice(heatedFloor6);
        addSmartDevice(heatedFloor7);

        Hoover hoover1 = new Hoover("Пылесос", placeGroups.get(0), connection);
        Hoover hoover2 = new Hoover("Пылесос", placeGroups.get(1), connection);
        Hoover hoover3 = new Hoover("Пылесос", placeGroups.get(2), connection);
        Hoover hoover4 = new Hoover("Пылесос", placeGroups.get(3), connection);
        Hoover hoover5 = new Hoover("Пылесос", placeGroups.get(4), connection);
        Hoover hoover6 = new Hoover("Пылесос", placeGroups.get(5), connection);
        Hoover hoover7 = new Hoover("Пылесос", placeGroups.get(6), connection);

        addSmartDevice(hoover1);
        addSmartDevice(hoover2);
        addSmartDevice(hoover3);
        addSmartDevice(hoover4);
        addSmartDevice(hoover5);
        addSmartDevice(hoover6);
        addSmartDevice(hoover7);

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
}
