package com.huy.backendnoithat.Controller.Account;

import com.huy.backendnoithat.Entity.Account;
import com.huy.backendnoithat.Service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/search")
    public Account findByUsername(@RequestParam(value = "username") String username) {
        return accountService.findByUsername(username);
    }
    @PostMapping("/")
    public ResponseEntity<String> save(@RequestBody Account account) {
        accountService.save(account);
        return ResponseEntity.ok("Saved successfully.");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") int id) {
        accountService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully.");
    }
    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activateAccount(@PathVariable(value = "id") int id) {
        accountService.activateAccount(id);
        return ResponseEntity.ok("Activated successfully.");
    }

}
