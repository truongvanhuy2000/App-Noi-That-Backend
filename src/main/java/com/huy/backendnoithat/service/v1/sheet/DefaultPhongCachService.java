package com.huy.backendnoithat.service.v1.sheet;

import com.huy.backendnoithat.dao.v1.noithat.PhongCachEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.entity.sheet.HangMucEntity;
import com.huy.backendnoithat.entity.sheet.PhongCachNoiThatEntity;
import com.huy.backendnoithat.mapper.PhongCachNoiThatEntityDTOMapper;
import com.huy.backendnoithat.model.dto.BangNoiThat.PhongCach;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPhongCachService {
    private final PhongCachEntityDAO phongCachEntityDAO;
    private final PhongCachNoiThatEntityDTOMapper phongCachNoiThatEntityDTOMapper;

    public List<PhongCach> findAll(int userID) {
        List<PhongCachNoiThatEntity> entityList = phongCachEntityDAO.findAllByAccountId(userID);
        return entityList.stream()
            .sorted(Comparator.comparingInt(PhongCachNoiThatEntity::getOrderIndex))
            .map(phongCachNoiThatEntityDTOMapper::toDTO)
            .toList();
    }

    public Optional<PhongCach> findById(int userID, int id) {
        Optional<PhongCachNoiThatEntity> entity = phongCachEntityDAO.findByIdAndAccountId(id, userID);
        return entity.map(phongCachNoiThatEntityDTOMapper::toDTO);
    }

    @Transactional
    public void save(int userID, PhongCach phongCach) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = phongCachNoiThatEntityDTOMapper.toEntity(phongCach);
        phongCachNoiThatEntity.setAccount(AccountEntity.builder().id(userID).build());
        phongCachNoiThatEntity = phongCachEntityDAO.save(phongCachNoiThatEntity);
        phongCachNoiThatEntity.setOrderIndex(phongCachNoiThatEntity.getId());
    }

    public void deleteById(int userID, int id) {
        Optional<PhongCachNoiThatEntity> phongCachNoiThatEntity = phongCachEntityDAO.findByIdAndAccountId(id, userID);
        if (phongCachNoiThatEntity.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        phongCachEntityDAO.delete(phongCachNoiThatEntity.get());
    }

    @Transactional
    public void update(int userID, int id, PhongCach phongCach) {
        Optional<PhongCachNoiThatEntity> phongCachNoiThatEntity = phongCachEntityDAO.findByIdAndAccountId(id, userID);
        if (phongCachNoiThatEntity.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        PhongCachNoiThatEntity phongCachNoiThatEntityToUpdate = phongCachNoiThatEntity.get();
        phongCachNoiThatEntityToUpdate.setName(phongCach.getName());
    }

    @Transactional
    public void swap(int userID, int id1, int id2) {
        Optional<PhongCachNoiThatEntity> phongCachNoiThatEntity1 = phongCachEntityDAO.findByIdAndAccountId(id1, userID);
        Optional<PhongCachNoiThatEntity> phongCachNoiThatEntity2 = phongCachEntityDAO.findByIdAndAccountId(id2, userID);
        if (phongCachNoiThatEntity1.isEmpty() || phongCachNoiThatEntity2.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        PhongCachNoiThatEntity entity1 = phongCachNoiThatEntity1.get();
        PhongCachNoiThatEntity entity2 = phongCachNoiThatEntity2.get();
        int temp = entity1.getOrderIndex();
        entity1.setOrderIndex(entity2.getOrderIndex());
        entity2.setOrderIndex(temp);
        phongCachEntityDAO.save(entity1);
        phongCachEntityDAO.save(entity2);
    }
}
