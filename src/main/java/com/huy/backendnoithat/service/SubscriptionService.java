package com.huy.backendnoithat.service;

import com.huy.backendnoithat.dao.SubscriptionModelDAO;
import com.huy.backendnoithat.entity.SubscriptionModelEntity;
import com.huy.backendnoithat.model.dto.SubscriptionModelDTO;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SubscriptionService {
    private final SubscriptionModelDAO subscriptionModelDAO;

    public SubscriptionModelDTO getSubscriptionModelById(int id) {
        SubscriptionModelEntity subscriptionModelEntity = subscriptionModelDAO.findById(id, Sort.by(Sort.Direction.ASC, "tier")).orElseThrow();
        return SubscriptionModelDTO.builder()
            .id(subscriptionModelEntity.getId())
            .title(subscriptionModelEntity.getTitle())
            .description(subscriptionModelEntity.getDescription())
            .durationMonth(subscriptionModelEntity.getDurationMonth())
            .bonusMonth(subscriptionModelEntity.getBonusMonth())
            .limitFile(subscriptionModelEntity.getLimitFile())
            .price(subscriptionModelEntity.getPrice())
            .tier(subscriptionModelEntity.getTier())
            .build();
    }

    public List<SubscriptionModelDTO> getSubscriptionList() {
        List<SubscriptionModelEntity> subscriptionModelEntities = subscriptionModelDAO.findAll();
        return subscriptionModelEntities.stream()
            .map(subscriptionModelEntity -> SubscriptionModelDTO.builder()
                .id(subscriptionModelEntity.getId())
                .title(subscriptionModelEntity.getTitle())
                .description(subscriptionModelEntity.getDescription())
                .durationMonth(subscriptionModelEntity.getDurationMonth())
                .bonusMonth(subscriptionModelEntity.getBonusMonth())
                .limitFile(subscriptionModelEntity.getLimitFile())
                .price(subscriptionModelEntity.getPrice())
                .tier(subscriptionModelEntity.getTier())
                .build())
            .toList();
    }

    public SubscriptionModelDTO createSubscription(SubscriptionModelDTO subscriptionModelDTO) {
        if (StringUtils.isEmpty(subscriptionModelDTO.getTitle())) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (StringUtils.isEmpty(subscriptionModelDTO.getDescription())) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (subscriptionModelDTO.getDurationMonth() <= 0) {
            throw new IllegalArgumentException("Duration month must be greater than 0");
        }
        if (subscriptionModelDTO.getBonusMonth() < 0) {
            throw new IllegalArgumentException("Bonus month cannot be negative");
        }
        if (subscriptionModelDTO.getLimitFile() <= 0) {
            throw new IllegalArgumentException("Limit file must be greater than 0");
        }
        SubscriptionModelEntity subscriptionModelEntity = new SubscriptionModelEntity();
        subscriptionModelEntity.setTitle(subscriptionModelDTO.getTitle());
        subscriptionModelEntity.setDescription(subscriptionModelDTO.getDescription());
        subscriptionModelEntity.setDurationMonth(subscriptionModelDTO.getDurationMonth());
        subscriptionModelEntity.setBonusMonth(subscriptionModelDTO.getBonusMonth());
        subscriptionModelEntity.setLimitFile(subscriptionModelDTO.getLimitFile());
        subscriptionModelEntity.setTier(subscriptionModelDTO.getTier());

        subscriptionModelDAO.save(subscriptionModelEntity);
        return SubscriptionModelDTO.builder()
            .id(subscriptionModelEntity.getId())
            .title(subscriptionModelEntity.getTitle())
            .description(subscriptionModelEntity.getDescription())
            .durationMonth(subscriptionModelEntity.getDurationMonth())
            .bonusMonth(subscriptionModelEntity.getBonusMonth())
            .limitFile(subscriptionModelEntity.getLimitFile())
            .price(subscriptionModelEntity.getPrice())
            .tier(subscriptionModelEntity.getTier())
            .build();
    }

    public void deleteSubscriptionModel(int id) {
        SubscriptionModelEntity subscriptionModelEntity = subscriptionModelDAO.findById(id).orElseThrow();
        subscriptionModelDAO.delete(subscriptionModelEntity);
    }

    public SubscriptionModelDTO updateSubscription(int id, SubscriptionModelDTO subscriptionModelDTO) {
        SubscriptionModelEntity subscriptionModelEntity = subscriptionModelDAO.findById(id).orElseThrow();
        if (StringUtils.isNotEmpty(subscriptionModelDTO.getTitle())) {
            subscriptionModelEntity.setTitle(subscriptionModelDTO.getTitle());
        }
        if (StringUtils.isNotEmpty(subscriptionModelDTO.getDescription())) {
            subscriptionModelEntity.setDescription(subscriptionModelDTO.getDescription());
        }
        if (subscriptionModelDTO.getDurationMonth() > 0) {
            subscriptionModelEntity.setDurationMonth(subscriptionModelDTO.getDurationMonth());
        }
        if (subscriptionModelDTO.getBonusMonth() >= 0) {
            subscriptionModelEntity.setBonusMonth(subscriptionModelDTO.getBonusMonth());
        }
        if (subscriptionModelDTO.getLimitFile() > 0) {
            subscriptionModelEntity.setLimitFile(subscriptionModelDTO.getLimitFile());
        }
        if (subscriptionModelDTO.getPrice() != null) {
            subscriptionModelEntity.setPrice(subscriptionModelDTO.getPrice());
        }
        if (subscriptionModelDTO.getTier() != null) {
            subscriptionModelEntity.setTier(subscriptionModelDTO.getTier());
        }

        subscriptionModelDAO.save(subscriptionModelEntity);
        return SubscriptionModelDTO.builder()
            .id(subscriptionModelEntity.getId())
            .title(subscriptionModelEntity.getTitle())
            .description(subscriptionModelEntity.getDescription())
            .durationMonth(subscriptionModelEntity.getDurationMonth())
            .bonusMonth(subscriptionModelEntity.getBonusMonth())
            .limitFile(subscriptionModelEntity.getLimitFile())
            .price(subscriptionModelEntity.getPrice())
            .tier(subscriptionModelEntity.getTier())
            .build();
    }
}
