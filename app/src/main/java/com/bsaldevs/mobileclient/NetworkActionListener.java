package com.bsaldevs.mobileclient;

public interface NetworkActionListener {

    String REGISTRATION_EXCEPTION_MESSAGE_REFUSED_BY_ALREADY_USED_EMAIL = "The server refused registration: This email already used";
    String REGISTRATION_EXCEPTION_MESSAGE_REFUSED_BY_INCORRECT_DATA = "The server refused registration: Incorrect data";

    void onServerStatusResponse();
}
