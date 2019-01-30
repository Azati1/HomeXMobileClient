package com.bsaldevs.mobileclient;

import android.content.Context;
import android.content.SharedPreferences;

import com.bsaldevs.mobileclient.User.Account;

public class MemoryWorker {

    private static final String APP_PREFERENCES = "USER_DATA";

    private static final String APP_PREFERENCES_EMAIL = "EMAIL";
    private static final String APP_PREFERENCES_PASSWORD = "PASSWORD";
    private static final String APP_PREFERENCES_LOGIN_TYPE = "LOGIN_TYPE";

    private SharedPreferences sharedPreferences;

    public MemoryWorker(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveAccountInMemory(Account account) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(APP_PREFERENCES_EMAIL);
        editor.remove(APP_PREFERENCES_PASSWORD);
        editor.remove(APP_PREFERENCES_LOGIN_TYPE);
        editor.putString(APP_PREFERENCES_EMAIL, account.getEmail());
        editor.putString(APP_PREFERENCES_PASSWORD, account.getPassword());
        editor.putInt(APP_PREFERENCES_LOGIN_TYPE, account.getLoggedBy());
        editor.apply();
    }

    public Account loadAccountFromMemory() {

        Account account = null;

        String email = sharedPreferences.getString(APP_PREFERENCES_EMAIL, "");
        String password = sharedPreferences.getString(APP_PREFERENCES_PASSWORD, "");
        int loggedBy = sharedPreferences.getInt(APP_PREFERENCES_LOGIN_TYPE, Account.NOT_LOGGED);

        if (loggedBy != Account.NOT_LOGGED) {
            account = new Account();
            account.setEmail(email);
            account.setPassword(password);
            account.setLoggedBy(loggedBy);
        }

        return account;
    }

}
