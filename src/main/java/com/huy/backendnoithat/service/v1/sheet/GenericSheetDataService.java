package com.huy.backendnoithat.service.v1.sheet;

import com.huy.backendnoithat.dao.v1.noithat.*;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.entity.sheet.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenericSheetDataService {
    private final PhongCachEntityDAO phongCachEntityDAO;
    private final NoiThatEntityDAO noiThatEntityDAO;
    private final HangMucEntityDAO hangMucEntityDAO;
    private final VatLieuEntityDAO vatLieuEntityDAO;
    private final ThongSoEntityDAO thongSoEntityDAO;
    private static final int ADMIN_USER_ID = 2;

    @Transactional
    public void sampleAll(long userID, boolean overwrite) {
        List<PhongCachNoiThatEntity> existingPhongCach = phongCachEntityDAO.findAllByAccountId((int) userID);
        if (existingPhongCach != null && !existingPhongCach.isEmpty() && !overwrite) {
            log.info("User {} already has phong cach data, skipping sample data creation.", userID);
            return;
        }
        // 1. Fetch admin's data
        List<PhongCachNoiThatEntity> phongCachEntities = phongCachEntityDAO.findAllByAccountId(ADMIN_USER_ID);
        List<NoiThatEntity> noiThatEntities = noiThatEntityDAO.searchByAccount_IdJoinPhongCach(ADMIN_USER_ID);
        List<HangMucEntity> hangMucEntities = hangMucEntityDAO.searchByAccount_IdJoinNoiThat(ADMIN_USER_ID);
        List<VatLieuEntity> vatLieuEntities = vatLieuEntityDAO.searchByAccount_IdJoinHangMuc(ADMIN_USER_ID);
        List<ThongSoEntity> thongSoEntities = thongSoEntityDAO.searchByAccount_IdJoinVatLieu(ADMIN_USER_ID);

        // 2. Prepare ID maps
        Map<Integer, Integer> phongCachIdMap = new HashMap<>();
        Map<Integer, Integer> noiThatIdMap = new HashMap<>();
        Map<Integer, Integer> hangMucIdMap = new HashMap<>();
        Map<Integer, Integer> vatLieuIdMap = new HashMap<>();

        AccountEntity userAccount = AccountEntity.builder().id((int) userID).build();

        // 3. Copy PhongCach
        for (PhongCachNoiThatEntity pc : phongCachEntities) {
            PhongCachNoiThatEntity newPC = pc.clone();
            newPC.setAccount(userAccount);
            phongCachEntityDAO.save(newPC);
            phongCachIdMap.put(pc.getId(), newPC.getId());
        }

        // 4. Copy NoiThat
        for (NoiThatEntity nt : noiThatEntities) {
            NoiThatEntity newNT = nt.clone();
            newNT.setAccount(userAccount);
            newNT.setPhongCachNoiThatEntity(
                PhongCachNoiThatEntity.builder().id(phongCachIdMap.get(nt.getPhongCachNoiThatEntity().getId())).build()
            );
            noiThatEntityDAO.save(newNT);
            noiThatIdMap.put(nt.getId(), newNT.getId());
        }

        // 5. Copy HangMuc
        for (HangMucEntity hm : hangMucEntities) {
            HangMucEntity newHM = hm.clone();
            newHM.setAccount(userAccount);
            newHM.setNoiThatEntity(
                NoiThatEntity.builder().id(noiThatIdMap.get(hm.getNoiThatEntity().getId())).build()
            );
            hangMucEntityDAO.save(newHM);
            hangMucIdMap.put(hm.getId(), newHM.getId());
        }

        // 6. Copy VatLieu
        for (VatLieuEntity vl : vatLieuEntities) {
            VatLieuEntity newVL = vl.clone();
            newVL.setAccount(userAccount);
            newVL.setHangMucEntity(
                HangMucEntity.builder().id(hangMucIdMap.get(vl.getHangMucEntity().getId())).build()
            );
            vatLieuEntityDAO.save(newVL);
            vatLieuIdMap.put(vl.getId(), newVL.getId());
        }

        // 7. Copy ThongSo
        for (ThongSoEntity ts : thongSoEntities) {
            ThongSoEntity newTS = ts.clone();
            newTS.setAccount(userAccount);
            newTS.setVatLieuEntity(
                VatLieuEntity.builder().id(vatLieuIdMap.get(ts.getVatLieuEntity().getId())).build()
            );
            thongSoEntityDAO.save(newTS);
        }
    }
}
