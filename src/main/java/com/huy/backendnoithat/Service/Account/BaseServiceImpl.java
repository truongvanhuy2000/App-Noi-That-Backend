package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService{
    AccountService accountService;
    @Autowired
    public BaseServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }
    @Override
    public Account getAccountInformation(String username) {
        Account account = accountService.findByUsername(username);
        account.setPassword("");
        return account;
    }
}
