package com.huy.backendnoithat.dao.v0.Account;

import com.huy.backendnoithat.entity.account.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenDAO extends JpaRepository<RefreshTokenEntity, Long> {
    @Query(value = "SELECT rt FROM RefreshTokenEntity rt " +
            "JOIN rt.accountEntity acc WHERE acc.username = :username")
    List<RefreshTokenEntity> findAllByUsername(String username);
}
