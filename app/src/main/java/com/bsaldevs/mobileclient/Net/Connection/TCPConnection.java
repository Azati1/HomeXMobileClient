package com.bsaldevs.mobileclient.Net.Connection;

import android.os.AsyncTask;
import android.util.Log;

import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.User.UserDevice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {

    private Socket socket;
    private final Thread thread;
    private BufferedReader in;
    private BufferedWriter out;
    private final TCPConnectionListener eventListener;
    private final UserDevice sender;

    public TCPConnection(final TCPConnectionListener eventListener, final String ip, final int port, UserDevice userDevice) {
        this.eventListener = eventListener;
        this.sender = userDevice;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread intr = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            if (socket == null)
                                Log.d("CDA", "Can't connect to server ip - " + ip + ":" + port);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                intr.start();

                try {
                    socket = new Socket(ip, port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
                    eventListener.onConnectionReady(TCPConnection.this);
                    while (!thread.isInterrupted()) {
                        String msg = in.readLine();
                        eventListener.onReceiveString(TCPConnection.this, msg);
                    }
                } catch (IOException e) {
                    eventListener.onException(TCPConnection.this, e);
                } finally {
                    eventListener.onDisconnect(TCPConnection.this);
                }
            }
        });
        thread.start();
    }

    public synchronized void sendString(String value) {

        String deviceName = sender.getName();

        try {
            out.write(deviceName + ":" + value + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void sendRequest(Request request) {
        /*try {
            out.write(request.toString()+ "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }*/
        MessageWriter task = new MessageWriter();
        task.execute(request);
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
        }
    }

    public synchronized void connect() {
        if (!thread.isAlive())
            thread.start();
    }

    private class MessageWriter extends AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... values) {
            Request request = values[0];
            try {
                out.write(request.toString()+ "\r\n");
                out.flush();
            } catch (IOException e) {
                eventListener.onException(TCPConnection.this, e);
                disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
