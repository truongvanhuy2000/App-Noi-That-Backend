package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;

public interface BaseService {
    Account getAccountInformation(String token);
}
