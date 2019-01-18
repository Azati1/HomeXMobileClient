package com.bsaldevs.mobileclient.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.bsaldevs.mobileclient.R;

public class AboutOfProgramActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_of_program);

        initGUI();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }

    private void initGUI() {
        getSupportActionBar().hide();
        initAnimation();
    }

    private void initAnimation() {
        LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("about.json");
        lottieAnimationView.loop(false);
        lottieAnimationView.playAnimation();
    }
}
