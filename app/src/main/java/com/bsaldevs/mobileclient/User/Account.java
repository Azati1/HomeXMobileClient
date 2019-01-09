package com.bsaldevs.mobileclient.User;

public class Account {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String urlphoto;
    private int accountId;

    private static int id = 0;

    public Account(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

}
