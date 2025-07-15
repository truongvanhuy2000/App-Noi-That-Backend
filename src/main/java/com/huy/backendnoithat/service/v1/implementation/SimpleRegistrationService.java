package com.huy.backendnoithat.service.v1.implementation;

import com.huy.backendnoithat.dao.SubscriptionModelDAO;
import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.entity.SubscriptionModelEntity;
import com.huy.backendnoithat.model.UserRegistrationRequest;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.enums.UserRole;
import com.huy.backendnoithat.service.v1.AccountManagementService;
import com.huy.backendnoithat.service.v1.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SimpleRegistrationService implements RegisterService {
    private final AccountEntityDAO accountEntityDAO;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionModelDAO subscriptionModelDAO;
    private final AccountManagementService accountManagementService;

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
        int fileLimit = subscriptionModelEntity.getLimitFile();
        if (fileLimit <= 0) {
            log.warn("File limit is not set or invalid, defaulting to 1");
            fileLimit = 10000; // Set to 10000 for safety
        }

        LocalDate expirationDate = LocalDate.now().plusMonths(totalSubscriptionMonth);

        Account account = Account.builder()
            .username(registrationRequest.getUsername())
            .password(passwordEncoder.encode(registrationRequest.getPassword()))
            .accountInformation(registrationRequest.getAccountInformation())
            .enabled(false)
            .active(false)
            .fileLimit(fileLimit)
            .expiredDate(expirationDate)
            .roles(Stream.of(UserRole.USER.value).toList())
            .build();

        accountManagementService.save(account);
    }
}
