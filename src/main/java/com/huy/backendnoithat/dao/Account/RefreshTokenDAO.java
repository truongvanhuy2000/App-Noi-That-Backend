package com.huy.backendnoithat.dao.Account;

import com.huy.backendnoithat.entity.Account.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenDAO extends JpaRepository<RefreshTokenEntity, Long> {
    @Query(value = "SELECT rt FROM RefreshTokenEntity rt " +
            "join RefreshTokenEntity.accountEntity acc WHERE acc.username = :username")
    List<RefreshTokenEntity> findAllByUsername(String username);
}
