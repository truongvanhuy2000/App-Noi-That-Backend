package com.huy.backendnoithat.controller.account;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.service.account.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Account Account) {
        registerService.register(Account);
        return ResponseEntity.ok("Registered successfully.");
    }

    @GetMapping("/register/usernameValidation")
    public ResponseEntity<String> usernameValidation(@RequestParam(name = "username") String username) {
        if (registerService.usernameValidation(username)) {
            return ResponseEntity.ok("Valid username");
        }
        return ResponseEntity.badRequest().body("Invalid username");
    }
}
