package com.huy.backendnoithat.service.v0.account;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.AccountManagement.AccountInformation;

import java.util.Map;

public interface BaseService {
    Account getAccountInformation(String token);

    void changePassword(String token, Map<String, String> requestBody);

    void updateInfo(String token, AccountInformation accountInformation);


}
