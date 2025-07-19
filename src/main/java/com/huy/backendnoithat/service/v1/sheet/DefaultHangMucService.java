package com.huy.backendnoithat.service.v1.sheet;

import com.huy.backendnoithat.dao.v1.noithat.HangMucEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.entity.sheet.HangMucEntity;
import com.huy.backendnoithat.entity.sheet.NoiThatEntity;
import com.huy.backendnoithat.mapper.HangMucEntityDTOMapper;
import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultHangMucService {
    private final HangMucEntityDAO hangMucDAO;
    private final HangMucEntityDTOMapper hangMucEntityDTOMapper;

    public List<HangMuc> findAll(int userID) {
        return hangMucDAO.findAllByAccountId(userID)
            .stream()
            .sorted(Comparator.comparingInt(HangMucEntity::getOrderIndex))
            .map(hangMucEntityDTOMapper::toDTO)
            .toList();
    }

    public Optional<HangMuc> findById(int userID, int id) {
        return hangMucDAO.findByIdAndAccountId(id, userID)
            .map(hangMucEntityDTOMapper::toDTO);
    }

    @Transactional
    public void save(int userID, HangMuc hangMuc, int parentId) {
        HangMucEntity entity = hangMucEntityDTOMapper.toEntity(hangMuc);
        entity.setAccount(AccountEntity.builder().id(userID).build());
        entity.setNoiThatEntity(NoiThatEntity.builder().id(parentId).build());
        entity = hangMucDAO.save(entity);
        entity.setOrderIndex(entity.getId());
    }

    public void deleteById(int userID, int id) {
        Optional<HangMucEntity> entity = hangMucDAO.findByIdAndAccountId(id, userID);
        if (entity.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        hangMucDAO.delete(entity.get());
    }

    @Transactional
    public void update(int userID, int id, HangMuc hangMuc) {
        Optional<HangMucEntity> existing = hangMucDAO.findByIdAndAccountId(id, userID);
        if (existing.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        HangMucEntity entityToUpdate = existing.get();
        entityToUpdate.setName(hangMuc.getName());
        // add other mutable fields as needed
    }

    public List<HangMuc> searchByNoiThat(int userID, int noiThatId) {
        return hangMucDAO.searchByAccount_IdAndNoiThatEntity_Id(userID, noiThatId)
            .stream()
            .sorted(Comparator.comparingInt(HangMucEntity::getOrderIndex))
            .map(hangMucEntityDTOMapper::toDTO)
            .toList();
    }

    @Transactional
    public void swap(int userID, int id1, int id2) {
        Optional<HangMucEntity> entity1 = hangMucDAO.findByIdAndAccountId(id1, userID);
        Optional<HangMucEntity> entity2 = hangMucDAO.findByIdAndAccountId(id2, userID);
        if (entity1.isEmpty() || entity2.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        HangMucEntity e1 = entity1.get();
        HangMucEntity e2 = entity2.get();
        int temp = e1.getOrderIndex();
        e1.setOrderIndex(e2.getOrderIndex());
        e2.setOrderIndex(temp);
    }
}