package com.bsaldevs.mobileclient.Devices;

import com.bsaldevs.mobileclient.R;

public interface SmartDeviceDisplay {

    int LAMP_IMG_RES_ID = R.drawable.lamp_on;
    int LOCKER_IMG_RES_ID = R.drawable.ic_lock;
    int CONDITIONER_IMG_RES_ID = R.drawable.ic_air_conditioner;
    int MUSICPLAYER_IMG_RES_ID = R.drawable.ic_music_player;
    int SOCKET_IMG_RES_ID = R.drawable.ic_socket;

    int getImageResourceID();

}
