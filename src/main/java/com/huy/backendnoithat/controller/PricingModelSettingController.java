package com.huy.backendnoithat.controller;

import com.huy.backendnoithat.model.dto.PricingModelDTO;
import com.huy.backendnoithat.service.general.PricingModelService;
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
