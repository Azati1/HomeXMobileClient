package com.bsaldevs.mobileclient;

import com.bsaldevs.mobileclient.User.Account;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.Assert.*;

public class MemoryWorkerTest {

    private MemoryWorker memoryWorker;
    private Account mockAccount;

    @Before
    public void setUp() throws Exception {
        memoryWorker = Mockito.mock(MemoryWorker.class);

        Account account = new Account();
        account.setEmail("email");
        account.setPassword("password");
        account.setLoggedBy(Account.NOT_LOGGED);

        mockAccount = Mockito.mock(Account.class);
    }

    @Test
    public void shouldSaveAndLoadAccountFromMemory() {
        memoryWorker.saveAccountInMemory(mockAccount);

        try {
            Account account = Mockito.when(memoryWorker.loadAccountFromMemory()).thenReturn(mockAccount).getMock();
            assertEquals(account.getEmail(), mockAccount.getEmail());
            assertEquals(account.getPassword(), mockAccount.getPassword());
            assertEquals(account.getLoggedBy(), mockAccount.getLoggedBy());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}