package com.bsaldevs.mobileclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class StartAppActivity extends AppCompatActivity {

    private ImageView imageView;
    private int animationId = R.drawable.loading_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);
        imageView = findViewById(R.id.imageView7);
        loadAnimation();
    }

    private void loadAnimation() {
        Glide
                .with(StartAppActivity.this)
                .load(animationId)
                .into(imageView);
    }
}
