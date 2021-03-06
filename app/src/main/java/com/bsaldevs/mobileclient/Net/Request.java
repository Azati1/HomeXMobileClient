package com.bsaldevs.mobileclient.Net;

public class Request {

    private String sender;
    private String address;
    private String funcName;
    private String[] funcArgs;
    private static int idCounter;
    private int id;
    private ServerCallback serverCallback;

    public Request(String sender, String address, String funcName, String[] funcArgs) {
        this.address = address;
        this.sender = sender;
        this.funcName = funcName;
        this.funcArgs = funcArgs;
        this.id = idCounter++;
    }

    public String getSender() {
        return sender;
    }

    public String getAddress() {
        return address;
    }

    public String getFuncName() {
        return funcName;
    }

    public String[] getFuncArgs() {
        return funcArgs;
    }

    public int getId() {
        return id;
    }

    public void executeWithListener(ServerCallback serverCallback) {
        this.serverCallback = serverCallback;
    }

    public ServerCallback getServerCallback() {
        return serverCallback;
    }
}