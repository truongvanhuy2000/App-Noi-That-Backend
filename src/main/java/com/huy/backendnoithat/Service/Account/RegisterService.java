package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;

public interface RegisterService {
    void register(Account account);
    boolean usernameValidation(String username);
}
