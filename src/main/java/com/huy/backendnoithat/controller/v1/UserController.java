package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.model.PasswordChangeRequest;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.AccountManagement.AccountInformation;
import com.huy.backendnoithat.model.dto.TokenResponse;
import com.huy.backendnoithat.service.v1.UserService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import com.huy.backendnoithat.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(passwordChangeRequest);
        return ResponseEntity.ok("Change password successfully");
    }

    @PutMapping("/update-info")
    public ResponseEntity<String> updateInfo(@RequestBody AccountInformation accountInformation) {
        userService.updateInfo(accountInformation);
        return ResponseEntity.ok("Updated successfully");
    }

    @GetMapping("/digital-signature")
    public ResponseEntity<String> digitalSignature() {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        String digitalSignature = userService.generateDigitalSignature(userID);
        return ResponseEntity.ok(digitalSignature);
    }
}
