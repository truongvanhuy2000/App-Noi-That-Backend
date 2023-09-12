package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService{
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
}
