package com.huy.backendnoithat.service.account.impl;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.service.account.AccountService;
import com.huy.backendnoithat.service.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final AccountService accountService;

    @Autowired
    public RegisterServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void register(Account account) {
        account.setEnabled(false);
        account.setActive(false);
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        account.setRoles(roles);
        accountService.save(account);
    }

    @Override
    public boolean usernameValidation(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        Account account;
        try {
            account = accountService.findByUsername(username);
        } catch (Exception e) {
            return true;
        }
        return account == null;
    }
}
