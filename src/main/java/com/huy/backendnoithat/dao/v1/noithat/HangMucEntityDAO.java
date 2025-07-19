package com.huy.backendnoithat.dao.v1.noithat;

import com.huy.backendnoithat.entity.sheet.HangMucEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HangMucEntityDAO extends JpaRepository<HangMucEntity, Integer>, PagingAndSortingRepository<HangMucEntity, Integer> {
    List<HangMucEntity> findAllByAccountId(int userID);

    Optional<HangMucEntity> findByIdAndAccountId(int id, int userID);

    List<HangMucEntity> searchByAccount_IdAndNoiThatEntity_Id(int accountId, int noiThatEntityId);

    @EntityGraph(attributePaths = {"noiThatEntity"})
    @Query("SELECT h FROM HangMucEntity h WHERE h.account.id = :adminUserId")
    List<HangMucEntity> searchByAccount_IdJoinNoiThat(int adminUserId);
}
