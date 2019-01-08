package com.bsaldevs.mobileclient.Devices;

import com.bsaldevs.mobileclient.R;

public enum DeviceType {

    LUMP_BULB("Лампочка", R.drawable.ic_bulb),
    LAMP("Лампа", R.drawable.ic_lamp),
    SOCKET("Розетка", R.drawable.ic_socket),
    LOCKER("Замок", R.drawable.ic_lock),
    CONDITIONER("Кондиционер", R.drawable.ic_air_conditioner),
    HEATERS("Обогрев", R.drawable.ic_thermometer),
    MUSIC_PLAYER("Окружение", R.drawable.ic_sound_system),
    HOOVER("Пылесос", R.drawable.ic_hoover),
    JALOUSIE("Шторы", R.drawable.ic_window),
    KETTLE("Камера", R.drawable.ic_camera),
    CAMERA("Чайник", R.drawable.ic_kettle),
    AIR_FILTER("Воздухоочиститель", R.drawable.ic_wave),
    PRINTER("Принтер", R.drawable.ic_printer),
    OVEN("Духовка", R.drawable.ic_oven),
    WATER_HEATER("Водонагреватель", R.drawable.ic_water_heater),
    COFFEE_MACHINE("Кофеварка", R.drawable.ic_coffee_machine),
    WASHER("Стиральная машина", R.drawable.ic_washing_machine),
    IRON("Утюг", R.drawable.ic_iron),
    FAN("Вентилятор", R.drawable.ic_fan),
    HEATED_FLOORS("Обогрев полов", R.drawable.ic_floor);

    private int deviceImage;
    private String deviceName;

    DeviceType(String deviceName, int deviceImage) {
        this.deviceName = deviceName;
        this.deviceImage = deviceImage;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getDeviceImage() {
        return deviceImage;
    }
}
