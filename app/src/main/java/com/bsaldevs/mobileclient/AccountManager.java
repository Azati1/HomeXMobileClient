package com.bsaldevs.mobileclient;

import android.content.Context;
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

    private MemoryWorker memoryWorker;
    private MyApplication application;
    private Account currentAccount;

    public AccountManager(Context context) {
        this.application = (MyApplication) context.getApplicationContext();
        this.memoryWorker = new MemoryWorker(context);
    }

    public void init() {

        final Account account = memoryWorker.loadAccountFromMemory();

        if (account == null)
            return;

        setCurrentAccount(account);

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

    public void setCurrentAccount(Account account) {
        this.currentAccount = account;
        memoryWorker.saveAccountInMemory(account);
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
