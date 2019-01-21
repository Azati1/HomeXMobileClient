package com.bsaldevs.mobileclient.Net.Connection;

import android.os.AsyncTask;
import android.util.Log;

import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.User.UserDevice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
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
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), 1000);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
                    eventListener.onConnectionReady(TCPConnection.this);
                    while (!thread.isInterrupted()) {
                        char[] buffer = new char[4096];
                        String msg = null;
                        int size = in.read(buffer);
                        msg = new String(buffer, 0, size);
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
        System.out.println(value);
        try {
            if (out != null) {
                out.write(value);
                out.flush();
            } else
                eventListener.onException(TCPConnection.this, new IOException("send string exception, out object is null"));
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void sendRequest(Request request) {
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

    private class MessageWriter extends AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... values) {
            Request request = values[0];
            try {
                String message = request.toString();
                Log.d("CDA", message);
                out.write(message + "\r\n");
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
