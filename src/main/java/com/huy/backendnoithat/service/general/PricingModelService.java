package com.huy.backendnoithat.service.general;

import com.huy.backendnoithat.model.dto.PricingModelDTO;

public interface PricingModelService {
    void setPricingModel(PricingModelDTO pricingModel);

    PricingModelDTO getPricingModel();
}
