package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.Net.Response;
import com.bsaldevs.mobileclient.Net.ServerCallback;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.User.Account;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
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

        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("loading.json");
        lottieAnimationView.loop(false);
        lottieAnimationView.playAnimation();

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initUser();
            }
        });
        thread.start();*/

        initUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (VKSdk.isLoggedIn()) {
                    Intent login = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(login);
                } else if (AccessToken.getCurrentAccessToken() != null) {
                    Intent login = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(login);
                } else if (application.isUserLoggedIn()) {
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

    private void initUser() {

        final Account account = application.loadUserData();

        if (account.getLoggedBy() == null)
            return;

        if (application.getAccount().getLoggedBy().equals("simple")) {

            String[] args = new String[2];
            args[0] = account.getEmail();
            args[1] = account.getPassword();

            RequestPoll requestPoll = application.getRequestPoll();
            Request request = new Request("client", "server", "loginWithUserData", args);
            request.executeWithListener(new ServerCallback() {
                @Override
                public void onComplete(Response response) {

                    if (response.getFuncName().equals("loginWithUserData")) {
                        String[] args = response.getFuncArgs();

                        if (args[0].equals("ok")) {
                            String name = args[1];
                            account.setName(name);
                            account.setLoggedBy("simple");
                            application.setAccount(account);
                        }

                        if (args[0].equals("error")) {

                        }
                    }
                }
            });
            requestPoll.execute(request);
        }

        if (application.getAccount().getLoggedBy().equals("VK")) {

            VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_50"));
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    try {

                        JSONArray jsonArray = response.json.getJSONArray("response");

                        for (int i = 0; i < 1; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String first_name = jsonObject.getString("first_name");
                            String url_photo = jsonObject.getString("photo_50");
                            Log.d("CDA", first_name);// Пользователь успешно авторизовался

                            Account account = new Account();
                            account.setName(first_name);
                            account.setUrlPhoto(url_photo);
                            account.setLoggedBy("VK");

                            application.setAccount(account);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        if (application.getAccount().getLoggedBy().equals("FB")) {
            //TODO(Сделать подгрузку данных пользователя с фейсбука)
        }

    }
}
