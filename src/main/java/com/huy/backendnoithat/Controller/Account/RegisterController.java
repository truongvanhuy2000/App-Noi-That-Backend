package com.huy.backendnoithat.Controller.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.Service.Account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {
    RegisterService registerService;
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Account Account) {
        registerService.register(Account);
        return ResponseEntity.ok("Registered successfully.");
    }
    @GetMapping("/register/usernameValidation")
    public ResponseEntity<String> usernameValidation(@RequestParam(name="username") String username) {
        if (registerService.usernameValidation(username)) {
            return ResponseEntity.ok("Valid username");
        }
        return ResponseEntity.badRequest().body("Invalid username");
    }
}
