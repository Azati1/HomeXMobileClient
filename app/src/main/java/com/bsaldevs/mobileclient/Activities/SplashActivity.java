package com.bsaldevs.mobileclient.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.airbnb.lottie.LottieAnimationView;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnectionListener;
import com.bsaldevs.mobileclient.R;
import com.bsaldevs.mobileclient.Tasks;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 4000;
    private CheckServerStatusWrapper statusWrapper;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initGUI();

        application = (MyApplication) getApplication();

        statusWrapper = new CheckServerStatusWrapper();
        application.getClient().subscribeToTCPListener(statusWrapper);
        application.connect();
    }

    private void initGUI() {
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
        application.initAccount();
    }

    private void loginAction() {
        if (application.isUserLoggedIn())
            openInteractiveActivity();
        else
            openLoginActivity();
    }

    private void openInteractiveActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public class CheckServerStatusWrapper implements TCPConnectionListener {

        @Override
        public void onConnectionReady(TCPConnection connection) {
            Log.d("CDA_SA", "CheckServerStatusWrapper: onConnectionReady");
            initAccount();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    loginAction();
                }
            }, SPLASH_DISPLAY_TIME);
        }

        @Override
        public void onReceiveString(TCPConnection connection, String value) {

        }

        @Override
        public void onDisconnect(TCPConnection connection) {
            Log.d("CDA_SA", "CheckServerStatusWrapper: onDisconnect");
            Tasks.ShowDialog showDialog = new Tasks.ShowDialog("Соединение с сервером было разорвано", SplashActivity.this);
            showDialog.execute();
        }

        @Override
        public void onException(TCPConnection connection, Exception e) {

            String exceptionValue = String.valueOf(e);

            if (exceptionValue.contains("Connection refused")) {
                // подключение к выключенному/не существующему серверу
            } else if (exceptionValue.contains("Connection reset")) {
                // вырубился сервер
            } else if (exceptionValue.contains("Software caused connection abort")) {
                // отключился интернет на телефоне
            } else if (e.getMessage().contains("send string exception, out object is null")) {
                Tasks.ShowDialog showDialog = new Tasks.ShowDialog("Соединение с сервером было разорвано", SplashActivity.this);
                showDialog.execute();
                // объект аутпута еще не создан
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        application.getClient().unsubscribeFromTCPListener(statusWrapper);
    }

}
