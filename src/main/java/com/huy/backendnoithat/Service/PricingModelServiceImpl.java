package com.huy.backendnoithat.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.backendnoithat.DTO.PricingModelDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PricingModelServiceImpl implements PricingModelService {
    private final ObjectMapper objectMapper;
    @Value("${pricingModel.path}")
    private String PRICING_MODEL_PATH;
    @Override
    public void setPricingModel(PricingModelDTO pricingModel) {
        try {
            objectMapper.writeValue(new File(PRICING_MODEL_PATH), pricingModel);
        } catch (IOException e) {
            log.error("Cant write pricing model log");
            throw new RuntimeException(e);
        }
    }

    @Override
    public PricingModelDTO getPricingModel() {
        try {
            return objectMapper.readValue(new File(PRICING_MODEL_PATH), PricingModelDTO.class);
        } catch (IOException e) {
            log.error("Cant write pricing model log");
            throw new RuntimeException(e);
        }
    }
}
