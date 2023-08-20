package com.huy.backendnoithat.Security;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InMemoryTokenStore {
    List<String> tokenList;
    public void addTokenToList(String token) {

    }

    public void removeToken(String token) {

    }
}
