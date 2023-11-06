package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.Exception.AccountExpiredException;
import com.huy.backendnoithat.Exception.AccountIsDisabledException;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountService accountService;
    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountService = accountService;
    }
    @Override
    public String login(String username, String password) {
        Account account = accountService.findByUsername(username);
        if (account.getExpiredDate().isBefore(LocalDate.now())) {
            throw new AccountExpiredException("Account is expired");
        }
        if (!account.isEnabled() || !account.isActive()) {
            throw new AccountIsDisabledException("Account is disabled");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateAccessToken(username);
    }
}
