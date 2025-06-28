package com.huy.backendnoithat.service.v1;

import com.huy.backendnoithat.model.UserRegistrationRequest;

public interface RegisterService {
    boolean usernameValidation(String username);

    void register(UserRegistrationRequest registrationRequest);
}
