package com.huy.backendnoithat.usecase.account.registration.v0;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;

public interface RegisterService {
    void register(Account account);

    boolean usernameValidation(String username);
}
