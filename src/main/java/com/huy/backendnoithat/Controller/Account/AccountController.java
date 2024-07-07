package com.huy.backendnoithat.Controller.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
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
    @GetMapping("") // Chi list nhung acc da enable roi
    public List<Account> findAll() {
        return accountService.findAll();
    }
    @GetMapping("/{id}")
    public Account findById(@PathVariable(value = "id") int id) {
        return accountService.findById(id);
    }
    @GetMapping("/search")
    public Account findByUsername(@RequestParam(name="username") String username) {
        return accountService.findByUsername(username);
    }
    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody Account Account) {
        accountService.save(Account);
        return ResponseEntity.ok("Saved successfully.");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody Account Account) {
        accountService.update(Account);
        return ResponseEntity.ok("Updated successfully.");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") int id) {
        accountService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully.");
    }
    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateAccount(@PathVariable(value = "id") int id) {
        accountService.activateAccount(id);
        return ResponseEntity.ok("Activated successfully.");
    }
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable(value = "id") int id) {
        accountService.deactivateAccount(id);
        return ResponseEntity.ok("Deactivated successfully.");
    }
    @GetMapping("/notEnabled") // Api de list nhung tai khoan chua duoc enable
    public List<Account> findAllNotEnabledAccount() {
        return accountService.findAllNotEnabled();
    }
    @GetMapping("/enabled") // Api de list nhung tai khoan chua duoc enable
    public List<Account> findAllEnabledAccount() {
        return accountService.findAllEnabled();
    }
    @PutMapping("/enable/{id}")
    public ResponseEntity<String> enableAccount(@PathVariable(value = "id") int id) {
        accountService.enableAccount(id);
        return ResponseEntity.ok("Enabled successfully.");
    }
    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disableAccount(@PathVariable(value = "id") int id) {
        accountService.disableAccount(id);
        return ResponseEntity.ok("Disabled successfully.");
    }

}
