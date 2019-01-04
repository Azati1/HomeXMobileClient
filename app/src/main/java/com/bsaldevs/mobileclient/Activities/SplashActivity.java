package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.User.Account;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 4000;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (MyApplication) getApplication();
        initUser();

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

    private boolean initUser() {
        VKRequest request = VKApi.users().get();
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {

                    JSONArray jsonArray = response.json.getJSONArray("response");

                    for (int i = 0; i < 1; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String first_name = jsonObject.getString("first_name");
                        String last_name = jsonObject.getString("last_name");
                        Log.d("CDA", first_name + " " + last_name);// Пользователь успешно авторизовался
                        login(new Account(first_name, last_name));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return true;
    }

    private void login(Account account) {
        application.login(account);
        Log.d("CDA", "account successfully created");
    }
}
