package com.bsaldevs.mobileclient;

import com.bsaldevs.mobileclient.User.Account;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AccountManagerTest {

    private AccountManager accountManager;

    @Before
    public void setUp() throws Exception {
        accountManager = Mockito.mock(AccountManager.class);
    }

    @Test
    public void initAccountTest() {
    }

    @Test
    public void setCurrentAccount() {
    }

    @Test
    public void getCurrentAccount() {
    }

    @Test
    public void login() {
    }

    @Test
    public void logout() {
    }

    @Test
    public void shouldLoadAccountFromMemory() {
        try {
            accountManager.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}