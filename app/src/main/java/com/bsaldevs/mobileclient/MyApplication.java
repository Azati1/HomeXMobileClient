package com.bsaldevs.mobileclient;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.bsaldevs.mobileclient.Devices.DeviceType;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Blind;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Conditioner;
import com.bsaldevs.mobileclient.Devices.SmartDevices.HeatedFloor;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Kettle;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Lamp;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Locker;
import com.bsaldevs.mobileclient.Devices.SmartDevices.MusicPlayer;
import com.bsaldevs.mobileclient.Devices.SmartDevices.SmartDevice;
import com.bsaldevs.mobileclient.Devices.SmartDevices.Socket;
import com.bsaldevs.mobileclient.Fragments.RegistrationFragment;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.Net.Response;
import com.bsaldevs.mobileclient.Net.ServerCallback;
import com.bsaldevs.mobileclient.User.Account;
import com.bsaldevs.mobileclient.User.Client;
import com.bsaldevs.mobileclient.User.Mobile;
import com.bsaldevs.mobileclient.User.MobileClient;
import com.bsaldevs.mobileclient.User.UserDevice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private Gson gson;
    private MobileClient client;
    private TCPConnection connection;
    private List<SmartDevice> devices;
    private List<PlaceGroup> placeGroups;
    private Account account;
    private RequestPoll requestPoll;

    public MyApplication() {
        super();

        gson = new GsonBuilder().setPrettyPrinting().create();
        UserDevice mobile = new Mobile("username");

        String ip100 = "192.168.0.100";
        String ip101 = "192.168.0.101";
        String ip = "18.216.150.250";
        int port = 3346;

        setupClient(new MobileClient(ip, port, mobile));

        client.setOnClientException(new Client.onExceptionListener() {
            @Override
            public void onClientException(TCPConnection connection, Exception e) {
                Log.d("CDA", "onClientExceptionHandler");
            }
        });

        client.setOnRequestReceive(new Client.onRequestReceiveListener() {
            @Override
            public void onReceiveRequest(TCPConnection connection, String stringRequest) {
                Request request = gson.fromJson(stringRequest, Request.class);

                if (request.getFuncName().equals("register")) {
                    String[] args = request.getFuncArgs();
                    if (args[0].equals("ok")) {
                        ShowToast showToast = new ShowToast("Аккаунт успешно создан");
                        showToast.execute();
                    }
                    if (args[0].equals("error")) {
                        ShowToast showToast = new ShowToast("Произошла ошибка при создании аккаунта");
                        showToast.execute();
                    }
                }

                if (request.getFuncName().equals("login")) {
                    String args[] = request.getFuncArgs();
                    if (args[0].equals("ok")) {

                    }
                }
            }
        });

        this.connection = client.getConnection();

        devices = new ArrayList<>();
        placeGroups = new ArrayList<>();

        loadPlaceGroups();
        loadDevices();
    }

    public RequestPoll getRequestPoll() {
        return client.getRequestPoll();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        this.account = new Account();
    }

    public boolean login(final Account account) {

        final boolean[] successful = {false};

        String[] args = new String[2];
        args[0] = account.getEmail();
        args[1] = account.getPassword();

        RequestPoll requestPoll = getRequestPoll();
        Request request = new Request("client", "server", "login", args);
        request.executeWithListener(new ServerCallback() {
            @Override
            public void onComplete(Response response) {

                if (response.getFuncName().equals("login")) {
                    String[] args = response.getFuncArgs();
                    if (args[0].equals("ok")) {
                        successful[0] = true;
                        setAccount(account);
                    }
                    if (args[0].equals("error")) {
                        successful[0] = false;
                    }
                }
            }
        });
        requestPoll.execute(request);

        return successful[0];
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setupClient(MobileClient client) {
        this.client = client;
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
        PlaceGroup placeGroupOffice = new PlaceGroup("Офис", R.drawable.ic_desk);
        PlaceGroup placeGroupLivingRoom = new PlaceGroup("Гостиная", R.drawable.ic_livingroom);
        PlaceGroup placeGroupBathroom = new PlaceGroup("Ванная", R.drawable.ic_bathtub);
        PlaceGroup placeGroupBedroom = new PlaceGroup("Спальня", R.drawable.ic_bed);
        PlaceGroup placeGroupRestroom = new PlaceGroup("Уборная", R.drawable.ic_toilet);
        PlaceGroup placeGroupKitchen = new PlaceGroup("Кухня", R.drawable.ic_coffee_machine);

        addPlaceGroup(placeGroupOffice);
        addPlaceGroup(placeGroupLivingRoom);
        addPlaceGroup(placeGroupBathroom);
        addPlaceGroup(placeGroupBedroom);
        addPlaceGroup(placeGroupRestroom);
        addPlaceGroup(placeGroupKitchen);
    }

    private void loadDevices() {

        List<PlaceGroup> placeGroups = getPlaceGroups();

        Lamp lamp1 = new Lamp("Лампа лампа тумбочка аниме 1", placeGroups.get(0), connection);
        Lamp lamp2 = new Lamp("Лампа лампа тумбочка аниме 2", placeGroups.get(0), connection);
        Lamp lamp3 = new Lamp("Лампа лампа тумбочка аниме 3", placeGroups.get(0), connection);

        addSmartDevice(lamp1);
        addSmartDevice(lamp2);
        addSmartDevice(lamp3);

        Socket socket1 = new Socket("Розетка конфетка ложка картошка 1", placeGroups.get(1), connection);
        Socket socket2 = new Socket("Розетка 2", placeGroups.get(1), connection);
        Socket socket3 = new Socket("Розетка 3", placeGroups.get(1), connection);

        addSmartDevice(socket1);
        addSmartDevice(socket2);
        addSmartDevice(socket3);

        Locker locker1 = new Locker("Замок 1", placeGroups.get(2), connection);
        Locker locker2 = new Locker("Замок 2", placeGroups.get(2), connection);
        Locker locker3 = new Locker("Замок 3", placeGroups.get(2), connection);

        addSmartDevice(locker1);
        addSmartDevice(locker2);
        addSmartDevice(locker3);

        Locker locker4 = new Locker("Замок 4", placeGroups.get(2), connection);
        Locker locker5 = new Locker("Замок 5", placeGroups.get(2), connection);
        Locker locker6 = new Locker("Замок 6", placeGroups.get(2), connection);

        addSmartDevice(locker4);
        addSmartDevice(locker5);
        addSmartDevice(locker6);

        Conditioner conditioner1 = new Conditioner("Кондей 1", placeGroups.get(3), connection);
        Conditioner conditioner2 = new Conditioner("Кондей 2", placeGroups.get(3), connection);
        Conditioner conditioner3 = new Conditioner("Кондей 3", placeGroups.get(3), connection);

        addSmartDevice(conditioner1);
        addSmartDevice(conditioner2);
        addSmartDevice(conditioner3);

        MusicPlayer musicPlayer1 = new MusicPlayer("Плеер 1", placeGroups.get(5), connection);
        MusicPlayer musicPlayer2 = new MusicPlayer("Плеер 2", placeGroups.get(5), connection);
        MusicPlayer musicPlayer3 = new MusicPlayer("Плеер 3", placeGroups.get(5), connection);

        addSmartDevice(musicPlayer1);
        addSmartDevice(musicPlayer2);
        addSmartDevice(musicPlayer3);

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

        addSmartDevice(heatedFloor1);
        addSmartDevice(heatedFloor2);
        addSmartDevice(heatedFloor3);
        addSmartDevice(heatedFloor4);
        addSmartDevice(heatedFloor5);
        addSmartDevice(heatedFloor6);
    }

    private class ShowToast extends AsyncTask<Void, Void, Void> {

        private String value;

        public ShowToast(String value) {
            this.value = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
        }
    }
}
