package com.bsaldevs.mobileclient;

import android.content.SharedPreferences;
import android.util.Log;
import com.bsaldevs.mobileclient.Net.Request;
import com.bsaldevs.mobileclient.Net.RequestPoll;
import com.bsaldevs.mobileclient.Net.Response;
import com.bsaldevs.mobileclient.Net.ServerCallback;
import com.bsaldevs.mobileclient.User.Account;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    private static final String APP_PREFERENCES_EMAIL = "EMAIL";
    private static final String APP_PREFERENCES_PASSWORD = "PASSWORD";
    private static final String APP_PREFERENCES_LOGIN_TYPE = "LOGIN_TYPE";

    private SharedPreferences sharedPreferences;
    private MyApplication application;
    private Account currentAccount;

    public AccountManager(MyApplication application, SharedPreferences sharedPreferences) {
        this.application = application;
        this.sharedPreferences = sharedPreferences;
    }

    public void init() throws Exception {

        final Account account = loadAccountFromMemory();

        switch (account.getLoggedBy()) {

            case Account.LOGGED_BY_APPLICATION: {
                initAccountFromApplication();
                break;
            }
            case Account.LOGGED_BY_VKONTAKTE: {
                initAccountFromVKontakte();
                break;
            }
            case Account.LOGGED_BY_FACEBOOK: {
                initAccountFromFacebook();
                break;
            }
            case Account.LOGGED_BY_GOOGLEPLUS: {
                initAccountFromGooglePlus();
                break;
            }
        }

    }

    private void initAccountFromApplication() {
        String[] args = new String[2];
        args[0] = currentAccount.getEmail();
        args[1] = currentAccount.getPassword();

        RequestPoll requestPoll = application.getRequestPoll();
        Request request = new Request("client", "server", "login", args);
        request.executeWithListener(new ServerCallback() {
            @Override
            public void onComplete(Response response) {

                if (response.getFuncName().equals("login")) {
                    String[] args = response.getFuncArgs();

                    if (args[0].equals("ok")) {
                        String name = args[1];
                        currentAccount.setName(name);
                        currentAccount.setLoggedBy(Account.LOGGED_BY_APPLICATION);
                    }

                    if (args[0].equals("error")) {
                        Log.d("CDA", "server send error login code (incorrect user data)");
                    }
                }
            }
        });
        requestPoll.execute(request);
    }

    private void initAccountFromVKontakte() {
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_50"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {

                    JSONArray jsonArray = response.json.getJSONArray("response");

                    for (int i = 0; i < 1; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String first_name = jsonObject.getString("first_name");
                        String url_photo = jsonObject.getString("photo_50");
                        Log.d("CDA", first_name);// Пользователь успешно авторизовался

                        Account account = new Account();
                        account.setName(first_name);
                        account.setUrlPhoto(url_photo);
                        account.setLoggedBy(Account.LOGGED_BY_VKONTAKTE);

                        setCurrentAccount(account);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initAccountFromFacebook () {
        //TODO(Сделать подгрузку данных пользователя с фейсбука)
    }

    private void initAccountFromGooglePlus() {
        //TODO(Сделать подгрузку данных пользователя с гугл-плюс)
    }

    private void saveAccountInMemory() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(APP_PREFERENCES_EMAIL);
        editor.remove(APP_PREFERENCES_PASSWORD);
        editor.remove(APP_PREFERENCES_LOGIN_TYPE);
        editor.putString(APP_PREFERENCES_EMAIL, currentAccount.getEmail());
        editor.putString(APP_PREFERENCES_PASSWORD, currentAccount.getPassword());
        editor.putInt(APP_PREFERENCES_LOGIN_TYPE, currentAccount.getLoggedBy());
        editor.apply();
    }

    private Account loadAccountFromMemory() throws Exception {

        Account account = null;

        String email = sharedPreferences.getString(APP_PREFERENCES_EMAIL, "");
        String password = sharedPreferences.getString(APP_PREFERENCES_PASSWORD, "");
        int loggedBy = sharedPreferences.getInt(APP_PREFERENCES_LOGIN_TYPE, Account.NOT_LOGGED);

        if (loggedBy != Account.NOT_LOGGED) {
            account = new Account();
            account.setEmail(email);
            account.setPassword(password);
            account.setLoggedBy(loggedBy);
            setCurrentAccount(account);
        }

        if (account == null)
            throw new Exception("An error occurred while creating an account: required data not found");

        return account;
    }

    public void setCurrentAccount(Account account) {
        this.currentAccount = account;
        saveAccountInMemory();
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void login(Account account) {
        setCurrentAccount(account);
    }

    public void logout() {
        Account account = new Account();
        account.setLoggedBy(Account.NOT_LOGGED);
        setCurrentAccount(account);
    }

}
