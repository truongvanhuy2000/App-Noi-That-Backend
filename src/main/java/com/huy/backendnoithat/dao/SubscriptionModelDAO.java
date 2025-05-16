package com.huy.backendnoithat.dao;

import com.huy.backendnoithat.entity.SubscriptionModelEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionModelDAO extends JpaRepository<SubscriptionModelEntity, Integer> {
    Optional<SubscriptionModelEntity> findById(int id, Sort tier);
}
