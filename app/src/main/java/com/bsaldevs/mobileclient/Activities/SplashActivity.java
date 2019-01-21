package com.bsaldevs.mobileclient.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bsaldevs.mobileclient.MyApplication;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnectionListener;
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

        CheckServerStatusWrapper statusWrapper = new CheckServerStatusWrapper();
        application.getClient().subscribeToTCPListener(statusWrapper);
        application.connect();
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
            Log.d("CDA_SA", "init account exception: " + e.getMessage());
        }
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
            ConnectionListener connectionListener = new ConnectionListener();
            connectionListener.execute();
        }

        @Override
        public void onReceiveString(TCPConnection connection, String value) {

        }

        @Override
        public void onDisconnect(TCPConnection connection) {
            Log.d("CDA_SA", "CheckServerStatusWrapper: onDisconnect");
            CheckServerStatusWrapper.ShowDialog showDialog = new CheckServerStatusWrapper.ShowDialog("Соединение с сервером было разорвано");
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
                CheckServerStatusWrapper.ShowDialog showDialog = new CheckServerStatusWrapper.ShowDialog("Соединение с сервером было разорвано");
                showDialog.execute();
                // объект аутпута еще не создан
            }
        }

        public class ConnectionListener extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                initAccount();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loginAction();
                    }
                }, SPLASH_DISPLAY_TIME);
            }
        }

        public class ShowToast extends AsyncTask<Void, Void, Void> {

            private String value;

            public ShowToast(String value) {
                this.value = value;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        }

        public class ShowDialog extends AsyncTask<Void, Void, Void> {

            private String message;

            public ShowDialog(String message) {
                this.message = message;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Ошибка!")
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("Попробовать еще раз",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        application.reconnect();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

    }

}
