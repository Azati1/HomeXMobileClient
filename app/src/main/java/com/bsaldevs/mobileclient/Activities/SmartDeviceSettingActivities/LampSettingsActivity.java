package com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities;

import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bsaldevs.mobileclient.R;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

public class LampSettingsActivity extends AppCompatActivity {

    private ColorPickerView colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_light_settings);
        getSupportActionBar().hide();
        colorPicker = findViewById(R.id.colorPickerView);
        colorPicker.setPreferenceName("MyColorPickerView");


   colorPicker.setColorListener(new ColorListener() {
        @Override
        public void onColorSelected(ColorEnvelope colorEnvelope) {
            View curCol = findViewById(R.id.current_color_lamp);
            curCol.setBackgroundColor(colorEnvelope.getColor());
        }
    });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        colorPicker.saveData();
    }





}
