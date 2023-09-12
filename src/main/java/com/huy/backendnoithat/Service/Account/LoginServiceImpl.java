package com.huy.backendnoithat.Service.Account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        ObjectMapper objectMapper = new ObjectMapper();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateAccessToken(username);
        Account account = accountService.findByUsername(username);
        ObjectNode responseObject = objectMapper.createObjectNode();
        ObjectNode accountObject = objectMapper.createObjectNode();
        try {
            responseObject.put("account", objectMapper.writeValueAsString(account));
            responseObject.put("token", token);
            return objectMapper.writeValueAsString(responseObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
