package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.model.UserRegistrationRequest;
import com.huy.backendnoithat.model.dto.SubscriptionModelDTO;
import com.huy.backendnoithat.service.v1.RegisterService;
import com.huy.backendnoithat.service.SubscriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("v1Controller")
@RequestMapping("/api/v1/public/register")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V1RegisterController", description = "APIs for account registration and username validation")
public class RegisterController {
    private final RegisterService registerService;
    private final SubscriptionService subscriptionService;

    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest registrationRequest) {
        registerService.register(registrationRequest);
        return ResponseEntity.ok("Registered successfully.");
    }

    @GetMapping("/validate-username")
    public ResponseEntity<String> usernameValidation(@RequestParam(name = "username") String username) {
        if (registerService.usernameValidation(username)) {
            return ResponseEntity.ok("Valid username");
        }
        return ResponseEntity.badRequest().body("Invalid username");
    }

    @GetMapping("/subscription")
    public List<SubscriptionModelDTO> getSubscriptionList() {
        return subscriptionService.getSubscriptionList();
    }
}
