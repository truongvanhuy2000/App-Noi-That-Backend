package com.huy.backendnoithat.dao;

import com.huy.backendnoithat.entity.PricingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PricingModelEntityDAO extends JpaRepository<PricingModelEntity, Integer> {
    @Query(value = "SELECT * FROM pricing p WHERE p.id = 1 LIMIT 1",
            nativeQuery = true)
    Optional<PricingModelEntity> getPricingModelEntity();
}