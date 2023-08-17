package com.huy.backendnoithat.Controller.Account;

import com.huy.backendnoithat.Entity.Account;
import com.huy.backendnoithat.Service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/")
    public List<Account> findAll() {
        return accountService.findAll();
    }
    @GetMapping("/{id}")
    public Account findById(@PathVariable(value = "id") int id) {
        return accountService.findById(id);
    }


}
