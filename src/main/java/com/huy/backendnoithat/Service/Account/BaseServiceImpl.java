package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BaseServiceImpl implements BaseService{
    private final AccountService accountService;
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    public BaseServiceImpl(AccountService accountService, JwtTokenUtil jwtTokenUtil) {
        this.accountService = accountService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Account getAccountInformation(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        account.setPassword("");
        return account;
    }

    @Override
    public void changePassword(String token, Map<String, String> requestBody) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String oldPassword = requestBody.get("oldPassword");
        String newPassword = requestBody.get("newPassword");
        if (oldPassword == null || newPassword == null) {
            throw new IllegalArgumentException("Invalid request");
        }
        accountService.changePassword(username, oldPassword, newPassword);
    }

    @Override
    public void updateInfo(String token, AccountInformation accountInformation) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        accountService.updateInfo(username, accountInformation);
    }
}
