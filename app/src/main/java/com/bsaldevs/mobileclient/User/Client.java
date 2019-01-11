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
    private boolean isConnected = false;
    protected RequestPoll requestPoll;

    protected Client(String ip, int port, UserDevice userDevice) {
        connection = new TCPConnection(this, ip, port, userDevice);
        onExceptionListeners = new ArrayList<>();
        onRequestReceiveListeners = new ArrayList<>();
        userDevice.waitMessage(connection);
        this.requestPoll = RequestPoll.getInstance(connection);
    }

    public RequestPoll getRequestPoll() {
        return requestPoll;
    }

    @Override
    public void onConnectionReady(TCPConnection connection) {
        isConnected = true;
        Log.d("CDA","TCPConnection ready: " + connection);
    }

    @Override
    public void onReceiveString(final TCPConnection connection, final String value) {

        System.out.println(value);

        requestPoll.onReceiveResponse(value);

        for (final onRequestReceiveListener onRequestReceiveListener : onRequestReceiveListeners) {
            onRequestReceiveListener.onReceiveRequest(connection, value);
        }
    }

    @Override
    public void onDisconnect(TCPConnection connection) {
        isConnected = false;
        System.out.println("TCPConnection disconnect: " + connection);
    }

    @Override
    public void onException(TCPConnection connection, Exception e) {
        System.out.println("TCPConnection exception: " + connection + ", " + e);
        if (e.getCause().toString().contains("Connection refused")) {
            //подключение к выключенному/не существующему серверу
        } else if (e.getCause().toString().contains("Connection reset")) {
            //вырубился сервер
        }
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