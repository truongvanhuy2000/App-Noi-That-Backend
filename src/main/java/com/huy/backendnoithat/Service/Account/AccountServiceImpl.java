package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.Entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;
@Service(value = "defaultAccountService")
public class AccountServiceImpl implements AccountService {
    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account findById(int id) {
        return null;
    }

    @Override
    public Account findByUsername(String username) {
        return null;
    }

    @Override
    public void save(Account account) {
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void activateAccount(int id) {

    }
}
