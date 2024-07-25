package com.huy.backendnoithat.dao;

import com.huy.backendnoithat.entity.SavedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageDAO extends JpaRepository<SavedFileEntity, Integer> {
    @Query(value = "SELECT * FROM saved_file sf WHERE sf.id = :fileID AND sf.account_id = :accountID", nativeQuery = true)
    SavedFileEntity findByIdAndAccountId(int fileID, int accountID);

    @Query(value = "SELECT * FROM saved_file sf WHERE sf.account_id = :accountID", nativeQuery = true)
    List<SavedFileEntity> findAllByAccountId(int accountID);
}
