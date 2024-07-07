package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;
import com.huy.backendnoithat.DTO.TokenResponse;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BaseServiceImpl implements BaseService{
    private final AccountService accountService;
    private final JwtTokenUtil jwtTokenUtil;


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
