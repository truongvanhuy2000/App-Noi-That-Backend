package com.huy.backendnoithat.controller.v1.admin;

import com.huy.backendnoithat.model.dto.SubscriptionModelDTO;
import com.huy.backendnoithat.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/subscription")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<SubscriptionModelDTO> getSubscription() {
        return subscriptionService.getSubscriptionList();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public SubscriptionModelDTO createSubscription(@RequestBody SubscriptionModelDTO subscriptionModelDTO) {
        return subscriptionService.createSubscription(subscriptionModelDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteSubscription(@PathVariable("id") int id) {
        subscriptionService.deleteSubscriptionModel(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public SubscriptionModelDTO updateSubscription(
        @PathVariable("id") int id,
        @RequestBody SubscriptionModelDTO subscriptionModelDTO
    ) {
        return subscriptionService.updateSubscription(id, subscriptionModelDTO);
    }
}
