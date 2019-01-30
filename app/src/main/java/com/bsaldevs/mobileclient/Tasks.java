package com.bsaldevs.mobileclient;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnectionListener;

import java.lang.ref.WeakReference;

public class Tasks {

    public static class ServerStatusCheckThread implements Runnable {

        private Context context;

        public ServerStatusCheckThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            CheckServerStatusWrapper statusWrapper = new CheckServerStatusWrapper(context);
            MyApplication application = (MyApplication) context.getApplicationContext();
            application.getClient().subscribeToTCPListener(statusWrapper);
        }

    }

    public static class CheckServerStatusWrapper implements TCPConnectionListener {

        private Context context;
        private CoordinatorLayout coordinatorLayout;

        public CheckServerStatusWrapper(Context context) {
            this.context = context;
            this.coordinatorLayout = ((Activity) context).getWindow().getDecorView().findViewById(R.id.coordinator_layout);
        }

        @Override
        public void onConnectionReady(TCPConnection connection) {
            Log.d("CDA_T", "CheckServerStatusWrapper: onConnectionReady");
            ShowSnackBar showSnackbar = new ShowSnackBar(coordinatorLayout, context.getString(R.string.coordinator_layout_message_connection_ready), ShowSnackBar.SnackBarType.NOTIFICATION);
            showSnackbar.execute();
        }

        @Override
        public void onReceiveString(TCPConnection connection, String value) {

        }

        @Override
        public void onDisconnect(TCPConnection connection) {
            Log.d("CDA_T", "CheckServerStatusWrapper: onDisconnect");
            ShowSnackBar showSnackbar = new ShowSnackBar(coordinatorLayout, context.getString(R.string.coordinator_layout_message_connection_refused), ShowSnackBar.SnackBarType.ERROR);
            showSnackbar.execute();
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
                // объект аутпута еще не создан
            }
        }

    }

    public static class ShowSnackBar extends AsyncTask<Void, Void, Void> {

        private String message;
        private WeakReference<CoordinatorLayout> weakCoordinatorLayout;
        private SnackBarType snackBarType;

        public enum SnackBarType {
            ERROR, NOTIFICATION
        }

        public ShowSnackBar(CoordinatorLayout coordinatorLayout, String message, SnackBarType snackBarType) {
            this.weakCoordinatorLayout = new WeakReference<>(coordinatorLayout);
            this.message = message;
            this.snackBarType = snackBarType;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("CDA", "onPostExecute: " + message);
            if (weakCoordinatorLayout != null) {
                CoordinatorLayout coordinatorLayout = weakCoordinatorLayout.get();
                Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                if (snackBarType == SnackBarType.ERROR)
                    snackbar.getView().setBackgroundColor(Color.RED);
                if (snackBarType == SnackBarType.NOTIFICATION)
                    snackbar.getView().setBackgroundColor(Color.GREEN);
                snackbar.show();
            }
        }
    }

    public static class ShowDialog extends AsyncTask<Void, Void, Void> {

        private String message;
        private WeakReference<Context> weakContext;

        public ShowDialog(String message, Context context) {
            this.message = message;
            this.weakContext = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (weakContext != null) {
                final Context context = weakContext.get();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Ошибка!")
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("Попробовать еще раз",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        MyApplication application = (MyApplication) context.getApplicationContext();
                                        application.reconnect();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    public static class ShowToast extends AsyncTask<Void, Void, Void> {

        private String value;
        private WeakReference<Context> weakContext;

        public ShowToast(String value, Context context) {
            this.value = value;
            this.weakContext = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (weakContext != null)
                Toast.makeText(weakContext.get(), value, Toast.LENGTH_SHORT).show();
        }
    }


}
