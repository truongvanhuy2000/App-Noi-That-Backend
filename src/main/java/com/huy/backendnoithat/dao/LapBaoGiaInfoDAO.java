package com.huy.backendnoithat.dao;

import com.huy.backendnoithat.entity.LapBaoGiaInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LapBaoGiaInfoDAO extends JpaRepository<LapBaoGiaInfoEntity, Integer> {
    @Query(value = "select lbgi from LapBaoGiaInfoEntity lbgi " +
            "join AccountEntity acc on acc.id = lbgi.account.id " +
            "where acc.username = :username")
    LapBaoGiaInfoEntity findByUsername(@Param("username") String username);
}
