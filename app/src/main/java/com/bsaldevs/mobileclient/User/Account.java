package com.bsaldevs.mobileclient.User;

public class Account {

    private static int id = 0;

    private String name = "#empty";
    private String email = "#empty";
    private String password = "#empty";
    private String urlPhoto = "#empty";
    private int accountId;
    private String loggedBy;

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

    public void setUrlPhoto(String url){
        urlPhoto = url;
    }

    public String getUrlPhoto() {
        return urlPhoto;
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
