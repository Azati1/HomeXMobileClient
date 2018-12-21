package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Fragments.RegistrationFragment;

public class LoginActivity extends AppCompatActivity implements RegistrationFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login_activity);

        FragmentManager manager = getSupportFragmentManager();
        RegistrationFragment registrationFragment = new RegistrationFragment();
        manager.beginTransaction().replace(R.id.bottomRegistrationSheet, registrationFragment).commit();

        TextView title = findViewById(R.id.textView10);
        EditText editLogin = findViewById(R.id.editText2);
        EditText editPassword = findViewById(R.id.editText4);
        Button login = findViewById(R.id.button9);
        TextView titleLoginBy = findViewById(R.id.textView9);
        ImageButton loginByFacebookButton = findViewById(R.id.imageButtonFacebook);
        ImageButton loginByGooglePlusButton = findViewById(R.id.imageButtonGoogle);
        ImageButton loginByVKButton = findViewById(R.id.imageButtonVK);

        View sheet = findViewById(R.id.bottomRegistrationSheet);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(sheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Log.d("CDA", "bottom sheet onStateChanged");
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                Log.d("CDA", "bottom sheet onSlide");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(login);
            }
        });

        loginByFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by facebook", Toast.LENGTH_SHORT).show();
            }
        });

        loginByGooglePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by google plus", Toast.LENGTH_SHORT).show();
            }
        });

        loginByVKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login by vk", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
