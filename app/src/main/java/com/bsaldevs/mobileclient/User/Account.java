package com.bsaldevs.mobileclient.User;

public class Account {

    public static final int NOT_LOGGED = 0;
    public static final int LOGGED_BY_APPLICATION = 1;
    public static final int LOGGED_BY_VKONTAKTE = 2;
    public static final int LOGGED_BY_FACEBOOK = 3;
    public static final int LOGGED_BY_GOOGLEPLUS = 4;

    private static int id = 0;

    private String name;
    private String email;
    private String password;
    private String urlPhoto;
    private int accountId;
    private int loggedBy;

    public Account() {
        this.accountId = id++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public int getLoggedBy() {
        return loggedBy;
    }

    public void setLoggedBy(int loggedBy) {
        this.loggedBy = loggedBy;
    }
}
