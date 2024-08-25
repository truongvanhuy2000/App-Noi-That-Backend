package com.huy.backendnoithat.service.general.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.backendnoithat.dao.PricingModelEntityDAO;
import com.huy.backendnoithat.entity.PricingModelEntity;
import com.huy.backendnoithat.model.dto.PricingModelDTO;
import com.huy.backendnoithat.service.general.PricingModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PricingModelServiceImpl implements PricingModelService {
    private final ObjectMapper objectMapper;
    private final PricingModelEntityDAO pricingModelEntityDAO;

    @Override
    public void setPricingModel(PricingModelDTO pricingModel) {
        try {
            String pricing = objectMapper.writeValueAsString(pricingModel.getPricingModelList());
            Optional<PricingModelEntity> optional = pricingModelEntityDAO.getPricingModelEntity();
            PricingModelEntity pricingModelEntity;
            if (optional.isPresent()) {
                pricingModelEntity = optional.get();
                pricingModelEntity.setPricingModel(pricing);
            } else {
                pricingModelEntity = PricingModelEntity.builder()
                        .pricingModel(pricing)
                        .build();
            }
            pricingModelEntity.setActive(pricingModel.isActive());
            pricingModelEntityDAO.save(pricingModelEntity);
        } catch (IOException e) {
            log.error("Cant write pricing model log");
            throw new RuntimeException(e);
        }
    }

    @Override
    public PricingModelDTO getPricingModel() {
        Optional<PricingModelEntity> optional = pricingModelEntityDAO.getPricingModelEntity();
        try {
            if (optional.isPresent()) {
                PricingModelEntity pricingModelEntity = optional.get();
                List<PricingModelDTO.PricingModel> pricingModels = objectMapper.readValue(pricingModelEntity.getPricingModel(), new TypeReference<>() {});
                return PricingModelDTO.builder()
                        .active(pricingModelEntity.isActive())
                        .pricingModelList(pricingModels)
                        .build();
            } else {
                return PricingModelDTO.builder()
                        .active(false)
                        .pricingModelList(List.of())
                        .build();
            }
        } catch (IOException e) {
            log.error("Cant write pricing model log");
            throw new RuntimeException(e);
        }
    }
}
