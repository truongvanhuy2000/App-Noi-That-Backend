package com.huy.backendnoithat.dao.v1.noithat;

import com.huy.backendnoithat.entity.sheet.ThongSoEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ThongSoEntityDAO extends JpaRepository<ThongSoEntity, Integer>, PagingAndSortingRepository<ThongSoEntity, Integer> {
    List<ThongSoEntity> findAllByAccountId(int userID);

    Optional<ThongSoEntity> findByIdAndAccountId(int id, int userID);

    Collection<ThongSoEntity> searchByAccount_IdAndVatLieuEntity_Id(int accountId, int vatLieuEntityId);

    @EntityGraph(attributePaths = {"vatLieuEntity"})
    @Query("SELECT t FROM ThongSoEntity t WHERE t.account.id = :adminUserId")
    List<ThongSoEntity> searchByAccount_IdJoinVatLieu(int adminUserId);
}
