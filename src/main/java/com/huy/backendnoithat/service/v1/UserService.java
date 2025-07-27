package com.huy.backendnoithat.service.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.backendnoithat.model.PasswordChangeRequest;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.AccountManagement.AccountInformation;
import com.huy.backendnoithat.service.CryptoService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;
    private final CryptoService cryptoService;

    public Account getAccountInformation() {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Long userID)) {
            log.error("User ID not found in security context");
            throw new RuntimeException("User not found");
        }
        Account account = accountService.findById(userID.intValue());
        account.setPassword("");
        return account;
    }

    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        long userID = SecurityUtils.getUserFromContext();
        Account account = accountService.findById((int) userID);
        String oldPassword = passwordChangeRequest.getOldPassword();
        String newPassword = passwordChangeRequest.getNewPassword();
        if (oldPassword == null || newPassword == null) {
            throw new IllegalArgumentException("Invalid request");
        }
        accountService.changePassword(account.getUsername(), oldPassword, newPassword);
    }

    public void updateInfo(AccountInformation accountInformation) {
        long userID = SecurityUtils.getUserFromContext();
        Account account = accountService.findById((int) userID);
        accountService.updateInfo(account.getUsername(), accountInformation);
    }

    public String generateDigitalSignature(int userID) {
        Account account = accountService.findById(userID);
        if (account == null) {
            log.error("User with ID {} not found", userID);
            throw new RuntimeException("User not found");
        }
        String refreshToken = jwtTokenService.generateRefreshToken(account.getUsername());
        return cryptoService.encrypt(refreshToken);
    }
}
