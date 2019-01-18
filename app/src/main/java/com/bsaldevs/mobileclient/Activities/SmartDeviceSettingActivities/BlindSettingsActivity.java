package com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities;

import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.bsaldevs.mobileclient.R;

public class BlindSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_blind_settings);
       /* SeekBar seekBar = findViewById(R.id.fragment_blind_settings);*/
       /* seekBar.getProgressDrawable().setColorFilter(getResources().getCo‌​lor(R.color.your_color‌​), PorterDuff.Mode.SRC_ATOP);*/
    }
}
