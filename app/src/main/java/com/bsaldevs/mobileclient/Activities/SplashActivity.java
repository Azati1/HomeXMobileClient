package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;
import com.vk.sdk.VKSdk;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 4000;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (MyApplication) getApplication();

        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("loading.json");
        lottieAnimationView.loop(false);
        lottieAnimationView.playAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (VKSdk.isLoggedIn()) {
                    Intent login = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(login);
                } else {
                    Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_TIME);
    }
}
