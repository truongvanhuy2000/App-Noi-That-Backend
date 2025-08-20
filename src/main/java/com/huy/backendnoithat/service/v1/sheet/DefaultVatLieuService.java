package com.huy.backendnoithat.service.v1.sheet;

import com.huy.backendnoithat.dao.v1.noithat.VatLieuEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.entity.sheet.HangMucEntity;
import com.huy.backendnoithat.entity.sheet.ThongSoEntity;
import com.huy.backendnoithat.entity.sheet.VatLieuEntity;
import com.huy.backendnoithat.mapper.VatLieuEntityDTOMapper;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.PaginationResponse;
import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultVatLieuService {
    private final VatLieuEntityDAO vatLieuDAO;
    private final VatLieuEntityDTOMapper vatLieuEntityDTOMapper;

    public List<VatLieu> findAll(int userID) {
        return vatLieuDAO.findAllByAccountId(userID)
            .stream()
            .sorted(Comparator.comparingInt(VatLieuEntity::getOrderIndex))
            .map(vatLieuEntityDTOMapper::toDTO)
            .toList();
    }

    public Optional<VatLieu> findById(int userID, int id) {
        return vatLieuDAO.findByIdAndAccountId(id, userID)
            .map(vatLieuEntityDTOMapper::toDTO);
    }

    @Transactional
    public void save(int userID, VatLieu vatLieu, int parentId) {
        VatLieuEntity entity = vatLieuEntityDTOMapper.toEntity(vatLieu);
        entity.setAccount(AccountEntity.builder().id(userID).build());
        entity.setHangMucEntity(HangMucEntity.builder().id(parentId).build());
        entity.setThongSoEntity(ThongSoEntity.blank());
        entity = vatLieuDAO.save(entity);
        entity.setOrderIndex(entity.getId());
    }

    public void deleteById(int userID, int id) {
        Optional<VatLieuEntity> entity = vatLieuDAO.findByIdAndAccountId(id, userID);
        if (entity.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        vatLieuDAO.delete(entity.get());
    }

    @Transactional
    public void update(int userID, int id, VatLieu vatLieu) {
        Optional<VatLieuEntity> existing = vatLieuDAO.findByIdAndAccountId(id, userID);
        if (existing.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        VatLieuEntity entityToUpdate = existing.get();
        entityToUpdate.setName(vatLieu.getName());
    }

    public List<VatLieu> searchByHangMuc(int userID, int hangMucId) {
        return vatLieuDAO.searchByAccount_IdAndHangMucEntity_Id(userID, hangMucId)
            .stream()
            .sorted(Comparator.comparingInt(VatLieuEntity::getOrderIndex))
            .map(vatLieuEntityDTOMapper::toDTO)
            .toList();
    }

    public PaginationResponse<List<VatLieu>> searchByHangMuc(int userID, int hangMucId, PaginationRequest paginationRequest) {
        Page<VatLieuEntity> page = vatLieuDAO.searchByAccount_IdAndHangMucEntity_Id(userID, hangMucId, PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize()));
        return PaginationResponse.of(page.getTotalElements(),
            page.getContent().stream()
            .sorted(Comparator.comparingInt(VatLieuEntity::getOrderIndex))
            .map(vatLieuEntityDTOMapper::toDTO)
            .toList());
    }

    @Transactional
    public void swap(int userID, int id1, int id2) {
        Optional<VatLieuEntity> entity1 = vatLieuDAO.findByIdAndAccountId(id1, userID);
        Optional<VatLieuEntity> entity2 = vatLieuDAO.findByIdAndAccountId(id2, userID);
        if (entity1.isEmpty() || entity2.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        VatLieuEntity e1 = entity1.get();
        VatLieuEntity e2 = entity2.get();
        int temp = e1.getOrderIndex();
        e1.setOrderIndex(e2.getOrderIndex());
        e2.setOrderIndex(temp);
        vatLieuDAO.save(e1);
        vatLieuDAO.save(e2);
    }
}
