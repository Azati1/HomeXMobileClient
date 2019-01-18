package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.User.Account;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 4000;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initGUI();

        application = (MyApplication) getApplication();
        initAccount();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginAction();
            }
        }, SPLASH_DISPLAY_TIME);
    }

    private void initGUI() {
        getSupportActionBar().hide();
        setupLoadingAnimation();
    }

    private void setupLoadingAnimation() {
        LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("loading.json");
        lottieAnimationView.loop(false);
        lottieAnimationView.playAnimation();
    }

    private void initAccount() {
        try {
            application.initAccount();
        } catch (Exception e) {
            Log.d("CDA", e.getMessage());
        }
    }

    private void loginAction() {
        if (application.isUserLoggedIn())
            openInteractiveActivity();
        else
            openLoginActivity();
    }

    private void openInteractiveActivity() {
        Intent login = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(login);
    }

    private void openLoginActivity() {
        Intent login = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }

}
