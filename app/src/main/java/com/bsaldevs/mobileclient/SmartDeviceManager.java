package com.bsaldevs.mobileclient;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.SmartDevices.DeviceType;
import com.bsaldevs.mobileclient.SmartDevices.List.Blind;
import com.bsaldevs.mobileclient.SmartDevices.List.Conditioner;
import com.bsaldevs.mobileclient.SmartDevices.List.HeatedFloor;
import com.bsaldevs.mobileclient.SmartDevices.List.Hoover;
import com.bsaldevs.mobileclient.SmartDevices.List.Kettle;
import com.bsaldevs.mobileclient.SmartDevices.List.Lamp;
import com.bsaldevs.mobileclient.SmartDevices.List.Locker;
import com.bsaldevs.mobileclient.SmartDevices.List.MusicPlayer;
import com.bsaldevs.mobileclient.SmartDevices.List.Socket;
import com.bsaldevs.mobileclient.SmartDevices.SmartDevice;
import java.util.ArrayList;
import java.util.List;

public class SmartDeviceManager {

    private List<SmartDevice> smartDevices;
    private List<DeviceGroup> deviceGroups;
    private MyApplication application;

    public SmartDeviceManager(MyApplication application) {
        this.application = application;
        this.deviceGroups = new ArrayList<>();
        this.smartDevices = new ArrayList<>();
    }

    public void addSmartDevice(SmartDevice smartDevice) {
        smartDevices.add(smartDevice);
    }

    public void load(TCPConnection connection) {

        loadGroups();

        DeviceGroup deviceGroup0 = deviceGroups.get(0);
        DeviceGroup deviceGroup1 = deviceGroups.get(1);
        DeviceGroup deviceGroup2 = deviceGroups.get(2);
        DeviceGroup deviceGroup3 = deviceGroups.get(3);
        DeviceGroup deviceGroup4 = deviceGroups.get(4);
        DeviceGroup deviceGroup5 = deviceGroups.get(5);
        DeviceGroup deviceGroup6 = deviceGroups.get(6);

        Lamp lamp1 = new Lamp("Настольная лампа 1", connection);
        Lamp lamp2 = new Lamp("Люстра 1", connection);
        Lamp lamp3 = new Lamp("Настольная лампа 2", connection);
        Lamp lamp4 = new Lamp("Люстра 2", connection);
        Lamp lamp5 = new Lamp("Светильник в ванной", connection);
        Lamp lamp6 = new Lamp("Ночник", connection);
        Lamp lamp7 = new Lamp("Настольная лампа 3", connection);
        Lamp lamp8 = new Lamp("Люстра 3", connection);
        Lamp lamp9 = new Lamp("Светильник в уборной", connection);
        Lamp lamp10 = new Lamp("Подсветка на кухе", connection);
        Lamp lamp11 = new Lamp("Люстра 4", connection);
        Lamp lamp12 = new Lamp("Бра в коридоре", connection);

        Socket socket1 = new Socket("Розетка 1", connection);
        Socket socket2 = new Socket("Розетка 2", connection);
        Socket socket3 = new Socket("Розетка 3", connection);
        Socket socket4 = new Socket("Розетка 4", connection);
        Socket socket5 = new Socket("Розетка 5", connection);
        Socket socket6 = new Socket("Розетка 6", connection);
        Socket socket7 = new Socket("Розетка 7", connection);
        Socket socket8 = new Socket("Розетка 8", connection);
        Socket socket9 = new Socket("Розетка 9", connection);
        Socket socket10 = new Socket("Розетка 10", connection);

        Locker locker1 = new Locker("Замок 1", connection);
        Locker locker2 = new Locker("Замок 2", connection);
        Locker locker3 = new Locker("Замок 3", connection);
        Locker locker4 = new Locker("Замок 4", connection);
        Locker locker5 = new Locker("Замок 5", connection);
        Locker locker6 = new Locker("Замок 6", connection);

        Conditioner conditioner1 = new Conditioner("Кондиционер в кабинете", connection);
        Conditioner conditioner2 = new Conditioner("Кондиционер в гостиной", connection);
        Conditioner conditioner3 = new Conditioner("Кондиционер в спальне", connection);
        Conditioner conditioner4 = new Conditioner("Кондиционер на кухне", connection);

        MusicPlayer musicPlayer1 = new MusicPlayer("Музыкальный центр", connection);
        MusicPlayer musicPlayer2 = new MusicPlayer("Музыкальный центр", connection);
        MusicPlayer musicPlayer3 = new MusicPlayer("Музыкальный центр", connection);
        MusicPlayer musicPlayer4 = new MusicPlayer("Музыкальный центр", connection);
        MusicPlayer musicPlayer5 = new MusicPlayer("Музыкальный центр", connection);
        MusicPlayer musicPlayer6 = new MusicPlayer("Музыкальный центр", connection);
        MusicPlayer musicPlayer7 = new MusicPlayer("Музыкальный центр", connection);

        Blind blind1 = new Blind("Шторы 1", connection);
        Blind blind2 = new Blind("Шторы 2", connection);
        Blind blind3 = new Blind("Шторы 3", connection);
        Blind blind4 = new Blind("Шторы 3", connection);

        Kettle kettle1 = new Kettle("Чайник 1", connection);
        Kettle kettle2 = new Kettle("Чайник 2", connection);
        Kettle kettle3 = new Kettle("Чайник 3", connection);

        HeatedFloor heatedFloor1 = new HeatedFloor("Подогрев 1", connection);
        HeatedFloor heatedFloor2 = new HeatedFloor("Подогрев 2", connection);
        HeatedFloor heatedFloor3 = new HeatedFloor("Подогрев 3", connection);
        HeatedFloor heatedFloor4 = new HeatedFloor("Подогрев 4", connection);
        HeatedFloor heatedFloor5 = new HeatedFloor("Подогрев 5", connection);
        HeatedFloor heatedFloor6 = new HeatedFloor("Подогрев 6", connection);
        HeatedFloor heatedFloor7 = new HeatedFloor("Подогрев 7", connection);

        Hoover hoover1 = new Hoover("Пылесос", connection);
        Hoover hoover2 = new Hoover("Пылесос", connection);
        Hoover hoover3 = new Hoover("Пылесос", connection);
        Hoover hoover4 = new Hoover("Пылесос", connection);
        Hoover hoover5 = new Hoover("Пылесос", connection);
        Hoover hoover6 = new Hoover("Пылесос", connection);
        Hoover hoover7 = new Hoover("Пылесос", connection);

        deviceGroup0.addSmartDevice(lamp1);
        deviceGroup0.addSmartDevice(lamp2);
        deviceGroup1.addSmartDevice(lamp3);
        deviceGroup1.addSmartDevice(lamp4);
        deviceGroup2.addSmartDevice(lamp5);
        deviceGroup2.addSmartDevice(lamp6);
        deviceGroup3.addSmartDevice(lamp7);
        deviceGroup3.addSmartDevice(lamp8);
        deviceGroup4.addSmartDevice(lamp9);
        deviceGroup5.addSmartDevice(lamp10);
        deviceGroup6.addSmartDevice(lamp11);
        deviceGroup6.addSmartDevice(lamp12);

        deviceGroup0.addSmartDevice(socket1);
        deviceGroup1.addSmartDevice(socket2);
        deviceGroup2.addSmartDevice(socket3);
        deviceGroup3.addSmartDevice(socket4);
        deviceGroup4.addSmartDevice(socket5);
        deviceGroup5.addSmartDevice(socket6);
        deviceGroup6.addSmartDevice(socket7);
        deviceGroup0.addSmartDevice(socket8);
        deviceGroup1.addSmartDevice(socket9);
        deviceGroup2.addSmartDevice(socket10);

        deviceGroup0.addSmartDevice(locker1);
        deviceGroup1.addSmartDevice(locker2);
        deviceGroup2.addSmartDevice(locker3);
        deviceGroup3.addSmartDevice(locker4);
        deviceGroup4.addSmartDevice(locker5);
        deviceGroup5.addSmartDevice(locker6);

        deviceGroup0.addSmartDevice(conditioner1);
        deviceGroup2.addSmartDevice(conditioner2);
        deviceGroup4.addSmartDevice(conditioner3);
        deviceGroup6.addSmartDevice(conditioner4);

        deviceGroup0.addSmartDevice(musicPlayer1);
        deviceGroup1.addSmartDevice(musicPlayer2);
        deviceGroup2.addSmartDevice(musicPlayer3);
        deviceGroup3.addSmartDevice(musicPlayer4);
        deviceGroup4.addSmartDevice(musicPlayer5);
        deviceGroup5.addSmartDevice(musicPlayer6);
        deviceGroup6.addSmartDevice(musicPlayer7);

        deviceGroup1.addSmartDevice(blind1);
        deviceGroup3.addSmartDevice(blind2);
        deviceGroup5.addSmartDevice(blind3);
        deviceGroup6.addSmartDevice(blind4);

        deviceGroup2.addSmartDevice(kettle1);
        deviceGroup4.addSmartDevice(kettle2);
        deviceGroup6.addSmartDevice(kettle3);

        deviceGroup0.addSmartDevice(heatedFloor1);
        deviceGroup1.addSmartDevice(heatedFloor2);
        deviceGroup2.addSmartDevice(heatedFloor3);
        deviceGroup3.addSmartDevice(heatedFloor4);
        deviceGroup4.addSmartDevice(heatedFloor5);
        deviceGroup5.addSmartDevice(heatedFloor6);
        deviceGroup6.addSmartDevice(heatedFloor7);

        deviceGroup0.addSmartDevice(hoover1);
        deviceGroup1.addSmartDevice(hoover2);
        deviceGroup2.addSmartDevice(hoover3);
        deviceGroup3.addSmartDevice(hoover4);
        deviceGroup4.addSmartDevice(hoover5);
        deviceGroup5.addSmartDevice(hoover6);
        deviceGroup6.addSmartDevice(hoover7);

    }

    public List<SmartDevice> getSmartDevices(DeviceType deviceType) {

        List<SmartDevice> smartDevices = new ArrayList<>();

        switch (deviceType) {
            case LAMP:
                for (SmartDevice device : smartDevices) {
                    if (device.getDeviceType() == DeviceType.LAMP)
                        smartDevices.add(device);
                }
                break;
            case CONDITIONER:
                for (SmartDevice device : smartDevices) {
                    if (device.getDeviceType() == DeviceType.CONDITIONER)
                        smartDevices.add(device);
                }
                break;
            case LOCKER:
                for (SmartDevice device : smartDevices) {
                    if (device.getDeviceType() == DeviceType.LOCKER)
                        smartDevices.add(device);
                }
                break;
            case HEATERS:
                for (SmartDevice device : smartDevices) {
                    if (device.getDeviceType() == DeviceType.HEATERS)
                        smartDevices.add(device);
                }
                break;
            case MUSIC_PLAYER:
                for (SmartDevice device : smartDevices) {
                    if (device.getDeviceType() == DeviceType.MUSIC_PLAYER)
                        smartDevices.add(device);
                }
                break;
            case SOCKET:
                for (SmartDevice device : smartDevices) {
                    if (device.getDeviceType() == DeviceType.SOCKET)
                        smartDevices.add(device);
                }
                break;
        }

        return smartDevices;
    }

    public List<SmartDevice> getSmartDevices(DeviceType deviceType, DeviceGroup deviceGroup) {

        List<SmartDevice> smartDevicesInside = new ArrayList<>();

        for (int i = 0; i < deviceGroups.size(); i++) {
            if (deviceGroup.getName().equals(deviceGroups.get(i).getName())) {
                smartDevicesInside.addAll(deviceGroups.get(i).getDevicesInside());
            }
        }

        List<SmartDevice> smartDevicesResult = new ArrayList<>();

        for (int i = 0; i < smartDevicesInside.size(); i++) {
            if (smartDevicesInside.get(i).getDeviceType() == deviceType)
                smartDevicesResult.add(smartDevicesInside.get(i));
        }

        return smartDevicesResult;

    }

    private void loadGroups() {
        DeviceGroup deviceGroupOffice = new DeviceGroup("Кабинет", R.drawable.ic_desk);
        DeviceGroup deviceGroupLivingRoom = new DeviceGroup("Гостиная", R.drawable.ic_livingroom);
        DeviceGroup deviceGroupBathroom = new DeviceGroup("Ванная", R.drawable.ic_bathtub);
        DeviceGroup deviceGroupBedroom = new DeviceGroup("Спальня", R.drawable.ic_bed);
        DeviceGroup deviceGroupRestroom = new DeviceGroup("Уборная", R.drawable.ic_toilet);
        DeviceGroup deviceGroupKitchen = new DeviceGroup("Кухня", R.drawable.ic_coffee_machine);
        DeviceGroup deviceGroupHall = new DeviceGroup("Коридор", R.drawable.ic_shelf);

        addDeviceGroup(deviceGroupOffice);
        addDeviceGroup(deviceGroupLivingRoom);
        addDeviceGroup(deviceGroupBathroom);
        addDeviceGroup(deviceGroupBedroom);
        addDeviceGroup(deviceGroupRestroom);
        addDeviceGroup(deviceGroupKitchen);
        addDeviceGroup(deviceGroupHall);
    }

    public void addDeviceGroup(DeviceGroup deviceGroup) { deviceGroups.add(deviceGroup); }

    public List<DeviceGroup> getDeviceGroups() {
        return deviceGroups;
    }

    /**
     * This throws exception if deviceGroup with groupName doesn't exist*/

    public DeviceGroup getDeviceGroup(String groupName) throws Exception {

        DeviceGroup deviceGroupResult = null;

        for (DeviceGroup deviceGroup : deviceGroups) {
            if (deviceGroup.getName().equals(groupName))
                deviceGroupResult = deviceGroup;
        }

        if (deviceGroupResult == null)
            throw new Exception("Device group with name[" + groupName + "] doesn't exist");

        return deviceGroupResult;
    }

}
