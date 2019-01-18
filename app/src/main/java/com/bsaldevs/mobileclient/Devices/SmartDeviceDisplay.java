package com.bsaldevs.mobileclient.Devices;

import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.BlindSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.ConditionerSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.FloorSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.HoverSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.KettleSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.LampSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.LockSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.MusicPlayerSettingsActivity;
import com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities.SocketSettingsActivity;
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
    Class<HoverSettingsActivity> HOOVER_SETTINGS_ACTIVITY = HoverSettingsActivity.class;

    Class<?> getDisplayActivity();
}
