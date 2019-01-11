package com.bsaldevs.mobileclient.User;

public class Account {

    private String name;
    private String email;
    private String password;
    private String urlphoto;
    private int accountId;

    private static int id = 0;

    public Account(String name) {
        this.name = name;
        this.accountId = id++;
    }

    public void setUrlPhoto(String photo){
        urlphoto=photo;
    }

    public String getUrlphoto() {
        return urlphoto;
    }

    public String getName() {
        return name;
    }


}
