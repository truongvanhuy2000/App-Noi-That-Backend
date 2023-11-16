package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;
import com.huy.backendnoithat.DTO.TokenResponse;

import java.util.Map;

public interface BaseService {
    Account getAccountInformation(String token);

    void changePassword(String token, Map<String, String> requestBody);

    void updateInfo(String token, AccountInformation accountInformation);


}
