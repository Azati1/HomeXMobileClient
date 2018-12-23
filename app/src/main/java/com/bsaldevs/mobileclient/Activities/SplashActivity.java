package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.bsaldevs.mobileclient.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 4000;
    private AnimationDrawable animationDrawable;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        /*layout = findViewById(R.id.load_container);

        animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();*/


        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("loading.json");
        lottieAnimationView.loop(false);
        lottieAnimationView.playAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }
}
