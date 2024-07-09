package com.huy.backendnoithat.dao;

import com.huy.backendnoithat.entity.SavedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageDAO extends JpaRepository<SavedFileEntity, Integer> {
}
