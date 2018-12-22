package com.bsaldevs.mobileclient.Devices;

import com.bsaldevs.mobileclient.R;

public interface SmartDeviceDisplay {

    int LAMP_IMG_RES_ID = R.drawable.ic_baseline_highlight_24px;
    int LOCKER_IMG_RES_ID = R.drawable.ic_baseline_lock_24px;
    int CONDITIONER_IMG_RES_ID = R.drawable.ic_air_conditioner;
    int MUSICPLAYER_IMG_RES_ID = R.drawable.ic_music_player;
    int SOCKET_IMG_RES_ID = R.drawable.ic_socket;

    int getImageResourceID();

}
