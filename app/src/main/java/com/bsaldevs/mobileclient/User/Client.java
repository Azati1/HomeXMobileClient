package com.bsaldevs.mobileclient.User;

import android.util.Log;

import com.bsaldevs.mobileclient.Net.Connection.TCPConnection;
import com.bsaldevs.mobileclient.Net.Connection.TCPConnectionListener;
import com.bsaldevs.mobileclient.Net.RequestPoll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Client implements TCPConnectionListener {

    protected TCPConnection connection;
    protected List<onExceptionListener> onExceptionListeners;
    protected List<onRequestReceiveListener> onRequestReceiveListeners;
    protected List<TCPConnectionListener> listeners = new ArrayList<>();
    private boolean isConnected = false;
    protected RequestPoll requestPoll;

    private String ip;
    private int port;
    private UserDevice userDevice;

    protected Client(String ip, int port, UserDevice userDevice) {
        this.ip = ip;
        this.port = port;
        this.userDevice = userDevice;
        onExceptionListeners = new ArrayList<>();
        onRequestReceiveListeners = new ArrayList<>();
        userDevice.waitMessage(connection);
    }

    public void connect() {
        connection = new TCPConnection(this, ip, port, userDevice);
        this.requestPoll = RequestPoll.getInstance(connection);
    }

    public RequestPoll getRequestPoll() {
        return requestPoll;
    }

    @Override
    public void onConnectionReady(TCPConnection connection) {
        Log.d("CDA_C", "onConnectionReady, notify subscribers: " + listeners.size() + "count");
        for (TCPConnectionListener listener : listeners)
            listener.onConnectionReady(connection);
        isConnected = true;
        Log.d("CDA","TCPConnection ready in client class: " + connection);
    }

    @Override
    public void onReceiveString(final TCPConnection connection, final String value) {

        for (TCPConnectionListener listener : listeners)
            listener.onReceiveString(connection, value);

        System.out.println(value);

        requestPoll.onReceiveResponse(value);

        for (final onRequestReceiveListener onRequestReceiveListener : onRequestReceiveListeners) {
            onRequestReceiveListener.onReceiveRequest(connection, value);
        }
    }

    @Override
    public void onDisconnect(TCPConnection connection) {
        for (TCPConnectionListener listener : listeners)
            listener.onDisconnect(connection);
        isConnected = false;
        System.out.println("TCPConnection disconnect: " + connection);
    }

    @Override
    public void onException(TCPConnection connection, Exception e) {
        for (TCPConnectionListener listener : listeners)
            listener.onException(connection, e);

        System.out.println("TCPConnection exception: " + connection + ", " + e);

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

    public void subscribeToTCPListener(TCPConnectionListener listener) {
        listeners.add(listener);
        Log.d("CDA_C", listener + " - successfully subscribed");
    }

    public void unsubscribeFromTCPListener(TCPConnectionListener listener) {
        listeners.remove(listener);
    }

    public final void setOnClientException(Client.onExceptionListener listener) {
        onExceptionListeners.add(listener);
    }

    public final void setOnRequestReceive(Client.onRequestReceiveListener listener) {
        onRequestReceiveListeners.add(listener);
    }

    public interface onExceptionListener {
        void onClientException(TCPConnection connection, Exception e);
    }

    public interface onRequestReceiveListener {
        void onReceiveRequest(TCPConnection connection, String request);
    }

    public TCPConnection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return isConnected;
    }

}