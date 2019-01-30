package com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.bsaldevs.mobileclient.R;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

public class LampSettingsActivity extends AppCompatActivity {

    private ColorPickerView colorPicker;
    private String smartDeviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_light_settings);

        smartDeviceName = getIntent().getStringExtra(getString(R.string.smart_device_bundle_name));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(smartDeviceName);
        toolbar.setLogo(R.drawable.ic_bulb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final LinearLayout linearLayout = findViewById(R.id.linear_layout_settings_container);

        colorPicker = findViewById(R.id.colorPickerView);
        colorPicker.setPreferenceName("MyColorPickerView");

        colorPicker.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                linearLayout.setBackgroundColor(colorEnvelope.getColor());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        colorPicker.saveData();
    }

}
