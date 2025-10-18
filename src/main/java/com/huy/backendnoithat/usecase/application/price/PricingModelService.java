package com.huy.backendnoithat.usecase.application.price;

import com.huy.backendnoithat.model.dto.PricingModelDTO;

public interface PricingModelService {
    void setPricingModel(PricingModelDTO pricingModel);

    PricingModelDTO getPricingModel();
}
