package com.huy.backendnoithat.service.v1.sheet;

import com.huy.backendnoithat.dao.v1.noithat.ThongSoEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.entity.sheet.ThongSoEntity;
import com.huy.backendnoithat.entity.sheet.VatLieuEntity;
import com.huy.backendnoithat.mapper.ThongSoEntityDTOMapper;
import com.huy.backendnoithat.model.dto.BangNoiThat.ThongSo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultThongSoService {
    private final ThongSoEntityDAO thongSoDAO;
    private final ThongSoEntityDTOMapper thongSoEntityDTOMapper;

    public List<ThongSo> findAll(int userID) {
        return thongSoDAO.findAllByAccountId(userID)
            .stream()
            .map(thongSoEntityDTOMapper::toDTO)
            .toList();
    }

    public Optional<ThongSo> findById(int userID, int id) {
        return thongSoDAO.findByIdAndAccountId(id, userID)
            .map(thongSoEntityDTOMapper::toDTO);
    }

    public void save(int userID, ThongSo thongSo, int parentId) {
        ThongSoEntity entity = thongSoEntityDTOMapper.toEntity(thongSo);
        entity.setAccount(AccountEntity.builder().id(userID).build());
        entity.setVatLieuEntity(VatLieuEntity.builder().id(parentId).build());
        thongSoDAO.save(entity);
    }

    public void deleteById(int userID, int id) {
        Optional<ThongSoEntity> entity = thongSoDAO.findByIdAndAccountId(id, userID);
        if (entity.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        thongSoDAO.delete(entity.get());
    }

    @Transactional
    public void update(int userID, int id, ThongSo thongSo) {
        Optional<ThongSoEntity> existing = thongSoDAO.findByIdAndAccountId(id, userID);
        if (existing.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        ThongSoEntity entityToUpdate = existing.get();
        if (thongSo.getCao() != null) {
            entityToUpdate.setCao(thongSo.getCao());
        }
        if (thongSo.getRong() != null) {
            entityToUpdate.setRong(thongSo.getRong());
        }
        if (thongSo.getDai() != null) {
            entityToUpdate.setDai(thongSo.getDai());
        }
        if (thongSo.getDonVi() != null) {
            entityToUpdate.setDonVi(thongSo.getDonVi());
        }
        if (thongSo.getDonGia() != null) {
            entityToUpdate.setDonGia(thongSo.getDonGia());
        }
    }

    public List<ThongSo> searchByVatLieu(int userID, int vatLieuId) {
        return thongSoDAO.searchByAccount_IdAndVatLieuEntity_Id(userID, vatLieuId)
            .stream()
            .map(thongSoEntityDTOMapper::toDTO)
            .toList();
    }
}

