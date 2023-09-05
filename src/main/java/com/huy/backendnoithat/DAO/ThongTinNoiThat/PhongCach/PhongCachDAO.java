package com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhongCachDAO extends CrudRepository<PhongCachNoiThatEntity, Integer> {
    @Query("FROM PhongCachNoiThatEntity WHERE name = :name")
    PhongCachNoiThatEntity findUsingName(@Param("name") String name);
    @Query("UPDATE PhongCachNoiThatEntity SET name = :name WHERE id = :id")
    void update(@Param("name") String name,@Param("id") int id);
    @Query("FROM PhongCachNoiThatEntity p JOIN FETCH p.noiThatEntity")
    List<PhongCachNoiThatEntity> findAllAndJoinFetch();
    @Query("SELECT DISTINCT a FROM PhongCachNoiThatEntity a "
            + "JOIN FETCH a.noiThatEntity b "
            + "WHERE a.id = :id")
    PhongCachNoiThatEntity findByIdAndJoinFetch(@Param("id") int id);
}
