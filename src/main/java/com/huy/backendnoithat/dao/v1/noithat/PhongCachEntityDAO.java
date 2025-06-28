package com.huy.backendnoithat.dao.v1.noithat;

import com.huy.backendnoithat.entity.BangNoiThat.PhongCachNoiThatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhongCachEntityDAO extends JpaRepository<PhongCachNoiThatEntity, Integer>, PagingAndSortingRepository<PhongCachNoiThatEntity, Integer> {
    List<PhongCachNoiThatEntity> findAllByAccountId(int userID);

    Optional<PhongCachNoiThatEntity> findByIdAndAccountId(int id, int userID);
}
