package com.bsaldevs.mobileclient.Devices;

import com.bsaldevs.mobileclient.Activities.BlindSettingsActivity;
import com.bsaldevs.mobileclient.Activities.ConditionerSettingsActivity;
import com.bsaldevs.mobileclient.Activities.LampSettingsActivity;
import com.bsaldevs.mobileclient.Activities.MusicPlayerSettingsActivity;
import com.bsaldevs.mobileclient.R;

public interface SmartDeviceDisplay {

    int LAMP_IMG_RES_ID = R.drawable.lamp_on;
    int LOCKER_IMG_RES_ID = R.drawable.ic_lock;
    int CONDITIONER_IMG_RES_ID = R.drawable.ic_air_conditioner;
    int MUSIC_PLAYER_IMG_RES_ID = R.drawable.ic_music_player;
    int BLIND_IMG_RES_ID = R.drawable.ic_window;
    int SOCKET_IMG_RES_ID = R.drawable.ic_socket;

    Class<LampSettingsActivity> LAMP_SETTINGS_ACTIVITY = LampSettingsActivity.class;
    Class<LampSettingsActivity> LOCKER_SETTINGS_ACTIVITY = LampSettingsActivity.class;
    Class<ConditionerSettingsActivity> CONDITIONER_SETTINGS_ACTIVITY = ConditionerSettingsActivity.class;
    Class<MusicPlayerSettingsActivity> MUSIC_PLAYER_SETTINGS_ACTIVITY = MusicPlayerSettingsActivity.class;
    Class<BlindSettingsActivity> BLIND_SETTINGS_ACTIVITY = BlindSettingsActivity.class;
    Class<LampSettingsActivity> SOCKET_SETTINGS_ACTIVITY = LampSettingsActivity.class;

    int getImageResourceID();
    Class<?> getDisplayActivity();

}
