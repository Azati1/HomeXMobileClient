package com.bsaldevs.mobileclient.Activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.RegistrationFirstStepFragment;

public class RegistrationActivity extends AppCompatActivity implements RegistrationFirstStepFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
