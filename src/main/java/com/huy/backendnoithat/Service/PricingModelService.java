package com.huy.backendnoithat.Service;

import com.huy.backendnoithat.DTO.PricingModelDTO;

public interface PricingModelService {
    void setPricingModel(PricingModelDTO pricingModel);

    PricingModelDTO getPricingModel();
}
