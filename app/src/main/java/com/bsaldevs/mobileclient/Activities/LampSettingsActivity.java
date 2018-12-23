package com.bsaldevs.mobileclient.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bsaldevs.mobileclient.R;

public class LampSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_light_settings);
        getSupportActionBar().hide();
    }

}