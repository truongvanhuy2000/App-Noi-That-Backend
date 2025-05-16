package com.huy.backendnoithat.service.v0.account;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;

public interface RegisterService {
    void register(Account account);

    boolean usernameValidation(String username);
}
