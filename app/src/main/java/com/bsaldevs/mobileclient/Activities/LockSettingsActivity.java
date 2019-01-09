package com.bsaldevs.mobileclient.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.bsaldevs.mobileclient.R;

public class LockSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_settings);
        getSupportActionBar().hide();
    }

}
