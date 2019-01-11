package com.bsaldevs.mobileclient.User;

public class Account {

    private String name = "#empty";
    private String email = "#empty";;
    private String password = "#empty";;
    private String urlphoto = "#empty";;
    private int accountId;
    private String loggedBy = "simple";

    private static int id = 0;

    public Account() {
        this.accountId = id++;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoggedBy() {
        return loggedBy;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setLoggedBy(String loggedBy) {
        this.loggedBy = loggedBy;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
