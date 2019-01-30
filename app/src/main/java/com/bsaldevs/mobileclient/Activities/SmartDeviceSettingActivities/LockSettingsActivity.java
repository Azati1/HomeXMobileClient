package com.bsaldevs.mobileclient.Activities.SmartDeviceSettingActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.bsaldevs.mobileclient.R;

public class LockSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_settings);

        /*final ImageButton backar = findViewById(R.id.backar);
        backar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backar.setColorFilter(Color.parseColor("red"));
            }
        });*/
    }

}
