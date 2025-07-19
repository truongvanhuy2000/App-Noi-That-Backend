package com.huy.backendnoithat.service.v1.sheet;

import com.huy.backendnoithat.dao.v1.noithat.NoiThatEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.entity.sheet.NoiThatEntity;
import com.huy.backendnoithat.entity.sheet.PhongCachNoiThatEntity;
import com.huy.backendnoithat.mapper.NoiThatEntityDTOMapper;
import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DefaultNoiThatService {
    private final NoiThatEntityDAO noiThatDAO;
    private final NoiThatEntityDTOMapper noiThatEntityDTOMapper;

    public List<NoiThat> findAll(int userID) {
        return noiThatDAO.findAllByAccountId(userID)
            .stream()
            .sorted(Comparator.comparingInt(NoiThatEntity::getOrderIndex))
            .map(noiThatEntityDTOMapper::toDTO)
            .toList();
    }

    public Optional<NoiThat> findById(int userID, int id) {
        return noiThatDAO.findByIdAndAccountId(id, userID)
            .map(noiThatEntityDTOMapper::toDTO);
    }

    @Transactional
    public void save(int userID, NoiThat noiThat, int parentId) {
        NoiThatEntity entity = noiThatEntityDTOMapper.toEntity(noiThat);
        entity.setAccount(AccountEntity.builder().id(userID).build());
        entity.setPhongCachNoiThatEntity(PhongCachNoiThatEntity.builder().id(parentId).build());
        entity = noiThatDAO.save(entity);
        entity.setOrderIndex(entity.getId());
    }

    public void deleteById(int userID, int id) {
        Optional<NoiThatEntity> entity = noiThatDAO.findByIdAndAccountId(id, userID);
        if (entity.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        noiThatDAO.delete(entity.get());
    }

    @Transactional
    public void update(int userID, int id, NoiThat noiThat) {
        Optional<NoiThatEntity> existing = noiThatDAO.findByIdAndAccountId(id, userID);
        if (existing.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        NoiThatEntity entityToUpdate = existing.get();
        entityToUpdate.setName(noiThat.getName());
    }

    public List<NoiThat> searchByPhongCach(int userID, int phongCachId) {
        return noiThatDAO.searchByAccount_IdAndPhongCachNoiThatEntity_Id(userID, phongCachId)
            .stream()
            .sorted(Comparator.comparingInt(NoiThatEntity::getOrderIndex))
            .map(noiThatEntityDTOMapper::toDTO)
            .toList();
    }

    @Transactional
    public void swap(int userID, int id1, int id2) {
        Optional<NoiThatEntity> entity1 = noiThatDAO.findByIdAndAccountId(id1, userID);
        Optional<NoiThatEntity> entity2 = noiThatDAO.findByIdAndAccountId(id2, userID);
        if (entity1.isEmpty() || entity2.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        NoiThatEntity e1 = entity1.get();
        NoiThatEntity e2 = entity2.get();
        int temp = e1.getOrderIndex();
        e1.setOrderIndex(e2.getOrderIndex());
        e2.setOrderIndex(temp);
        noiThatDAO.save(e1);
        noiThatDAO.save(e2);
    }
}
