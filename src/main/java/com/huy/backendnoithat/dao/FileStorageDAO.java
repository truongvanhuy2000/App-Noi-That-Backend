package com.huy.backendnoithat.dao;

import com.huy.backendnoithat.entity.SavedFileEntity;
import com.huy.backendnoithat.model.enums.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageDAO extends JpaRepository<SavedFileEntity, Integer> {
    @Query("""
        SELECT sf FROM SavedFileEntity sf
        WHERE sf.id = :fileID AND sf.accountEntity.id = :accountID""")
    SavedFileEntity findByIdAndAccountId(@Param("fileID") int fileID, @Param("accountID") int accountID);

    @Query("""
        SELECT sf FROM SavedFileEntity sf
        WHERE sf.accountEntity.id = :accountID""")
    List<SavedFileEntity> findAllByAccountId(@Param("accountID") int accountID);

    @Query("""
        SELECT sf FROM SavedFileEntity sf
        WHERE sf.accountEntity.id = :userID AND sf.fileType = :fileType""")
    List<SavedFileEntity> findAllByAccountIdAndFileType(long userID, FileType fileType);
}
