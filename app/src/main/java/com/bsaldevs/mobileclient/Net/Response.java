package com.bsaldevs.mobileclient.Net;

public class Response {

    private int id;
    private String sender;
    private String address;
    private String funcName;
    private String[] funcArgs;

    public Response(int id, String sender, String address, String funcName, String[] funcArgs) {
        this.id = id;
        this.address = address;
        this.sender = sender;
        this.funcName = funcName;
        this.funcArgs = funcArgs;
    }

    public int getRequestId() {
        return id;
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
}
