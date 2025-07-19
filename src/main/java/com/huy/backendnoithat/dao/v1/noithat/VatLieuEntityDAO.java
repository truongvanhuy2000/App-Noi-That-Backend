package com.huy.backendnoithat.dao.v1.noithat;

import com.huy.backendnoithat.entity.sheet.VatLieuEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface VatLieuEntityDAO extends JpaRepository<VatLieuEntity, Integer>, PagingAndSortingRepository<VatLieuEntity, Integer> {
    Collection<VatLieuEntity> findAllByAccountId(int userID);

    Optional<VatLieuEntity> findByIdAndAccountId(int id, int userID);

    @EntityGraph(attributePaths = {"thongSoEntity"})
    Collection<VatLieuEntity> searchByAccount_IdAndHangMucEntity_Id(int accountId, int hangMucEntityId);

    @EntityGraph(attributePaths = {"hangMucEntity"})
    @Query("SELECT v FROM VatLieuEntity v WHERE v.account.id = :adminUserId")
    List<VatLieuEntity> searchByAccount_IdJoinHangMuc(int adminUserId);
}

