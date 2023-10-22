package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
