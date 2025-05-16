package com.huy.backendnoithat.service.v1.implementation;

import com.huy.backendnoithat.dao.SubscriptionModelDAO;
import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.Account.AccountInformationEntity;
import com.huy.backendnoithat.entity.Account.RoleEntity;
import com.huy.backendnoithat.entity.SubscriptionModelEntity;
import com.huy.backendnoithat.model.UserRegistrationRequest;
import com.huy.backendnoithat.service.v1.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SimpleRegistrationService implements RegisterService {
    private final AccountEntityDAO accountEntityDAO;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionModelDAO subscriptionModelDAO;

    @Override
    public boolean usernameValidation(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        Optional<AccountEntity> accountEntity = accountEntityDAO.findByUsername(username);
        return accountEntity.isEmpty();
    }

    @Override
    public void register(UserRegistrationRequest registrationRequest) {
        if (registrationRequest == null) {
            throw new IllegalArgumentException("Registration request cannot be null");
        }
        if (registrationRequest.getUsername() == null || registrationRequest.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (registrationRequest.getPassword() == null || registrationRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (registrationRequest.getAccountInformation() == null) {
            throw new IllegalArgumentException("Account information cannot be null");
        }
        if (registrationRequest.getSubscriptionId() == null) {
            throw new IllegalArgumentException("Subscription ID cannot be null");
        }
        SubscriptionModelEntity subscriptionModelEntity = subscriptionModelDAO.findById(registrationRequest.getSubscriptionId()).orElseThrow();
        Optional<AccountEntity> existingAccount = accountEntityDAO.findByUsername(registrationRequest.getUsername());
        if (existingAccount.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        long totalSubscriptionMonth = subscriptionModelEntity.getDurationMonth() + subscriptionModelEntity.getBonusMonth();
        long expirationTime = System.currentTimeMillis() + TimeUnit.DAYS.toMillis((totalSubscriptionMonth) * 30L);

        AccountEntity accountEntity = new AccountEntity();
        AccountInformationEntity accountInformationEntity = new AccountInformationEntity(registrationRequest.getAccountInformation());
        accountEntity.setUsername(registrationRequest.getUsername());
        accountEntity.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        accountEntity.setAccountInformationEntity(accountInformationEntity);
        accountEntity.setEnabled(false);
        accountEntity.setActive(false);
        accountEntity.setRoleEntity(Stream.of("ROLE_USER").map(item -> new RoleEntity(0, accountEntity, item)).toList());
        accountEntity.setExpiredDate(new Date(expirationTime));
        accountEntityDAO.save(accountEntity);
    }
}
