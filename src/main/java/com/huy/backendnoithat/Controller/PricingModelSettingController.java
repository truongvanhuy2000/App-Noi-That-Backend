package com.huy.backendnoithat.Controller;

import com.huy.backendnoithat.DTO.PricingModelDTO;
import com.huy.backendnoithat.Service.PricingModelService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricingModel")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PricingModelSettingController {
    private final PricingModelService pricingModelService;
    @GetMapping("")
    public PricingModelDTO getPricingModel() {
        return pricingModelService.getPricingModel();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void setPricingModel(@RequestBody PricingModelDTO pricingModel) {
        pricingModelService.setPricingModel(pricingModel);
    }
}
