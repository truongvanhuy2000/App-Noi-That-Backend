package com.huy.backendnoithat.dao.v1.noithat;

import com.huy.backendnoithat.entity.sheet.NoiThatEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoiThatEntityDAO extends JpaRepository<NoiThatEntity, Integer>, PagingAndSortingRepository<NoiThatEntity, Integer> {
    List<NoiThatEntity> findAllByAccountId(int userID);

    Optional<NoiThatEntity> findByIdAndAccountId(int id, int userID);

    List<NoiThatEntity> searchByAccount_IdAndPhongCachNoiThatEntity_Id(int accountId, int phongCachNoiThatEntityId);

    List<NoiThatEntity> searchByAccount_Id(int accountId);

    @EntityGraph(attributePaths = {"phongCachNoiThatEntity"})
    @Query("SELECT n FROM NoiThatEntity n WHERE n.account.id = :adminUserId")
    List<NoiThatEntity> searchByAccount_IdJoinPhongCach(int adminUserId);
}
