package com.bsaldevs.mobileclient;

import com.bsaldevs.mobileclient.User.Account;

public interface AccountManagerListener {
    void onUserLogged(Account account);
    void onUserUnLogged();
}
