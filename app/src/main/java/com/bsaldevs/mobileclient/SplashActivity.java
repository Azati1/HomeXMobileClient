package com.bsaldevs.mobileclient;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bsaldevs.mobileclient.Activities.FirstConnectionActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 5000;
    private AnimationDrawable animationDrawable;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        layout = findViewById(R.id.load_container);

        animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login = new Intent(SplashActivity.this, FirstConnectionActivity.class);
                startActivity(login);
                finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }
}
