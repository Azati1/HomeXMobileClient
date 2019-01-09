package com.bsaldevs.mobileclient.Devices;

import com.bsaldevs.mobileclient.Activities.BlindSettingsActivity;
import com.bsaldevs.mobileclient.Activities.ConditionerSettingsActivity;
import com.bsaldevs.mobileclient.Activities.FloorSettingsActivity;
import com.bsaldevs.mobileclient.Activities.KettleSettingsActivity;
import com.bsaldevs.mobileclient.Activities.LampSettingsActivity;
import com.bsaldevs.mobileclient.Activities.LockSettingsActivity;
import com.bsaldevs.mobileclient.Activities.MusicPlayerSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SocketSettingsActivity;
import com.bsaldevs.mobileclient.R;

public interface SmartDeviceDisplay {

    Class<LampSettingsActivity> LAMP_SETTINGS_ACTIVITY = LampSettingsActivity.class;
    Class<LockSettingsActivity> LOCKER_SETTINGS_ACTIVITY = LockSettingsActivity.class;
    Class<ConditionerSettingsActivity> CONDITIONER_SETTINGS_ACTIVITY = ConditionerSettingsActivity.class;
    Class<MusicPlayerSettingsActivity> MUSIC_PLAYER_SETTINGS_ACTIVITY = MusicPlayerSettingsActivity.class;
    Class<BlindSettingsActivity> BLIND_SETTINGS_ACTIVITY = BlindSettingsActivity.class;
    Class<SocketSettingsActivity> SOCKET_SETTINGS_ACTIVITY = SocketSettingsActivity.class;
    Class<KettleSettingsActivity> KETTLE_SETTINGS_ACTIVITY = KettleSettingsActivity.class;
    Class<FloorSettingsActivity> FLOOR_SETTINGS_ACTIVITY = FloorSettingsActivity.class;

    int getImageResourceID();
    Class<?> getDisplayActivity();

}
