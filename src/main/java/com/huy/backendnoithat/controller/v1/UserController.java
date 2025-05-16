package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.model.PasswordChangeRequest;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.AccountManagement.AccountInformation;
import com.huy.backendnoithat.service.v1.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {
    private final UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/info")
    public Account info() {
        return userService.getAccountInformation();
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(passwordChangeRequest);
        return ResponseEntity.ok("Change password successfully");
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<String> updateInfo(@RequestBody AccountInformation accountInformation) {
        userService.updateInfo(accountInformation);
        return ResponseEntity.ok("Updated successfully");
    }
}
