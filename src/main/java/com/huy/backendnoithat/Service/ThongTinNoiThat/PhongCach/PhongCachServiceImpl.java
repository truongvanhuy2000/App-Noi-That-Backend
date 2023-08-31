package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach.PhongCachDAO;
import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;
import com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc.HangMucService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat.NoiThatService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PhongCachServiceImpl implements PhongCachService {
    PhongCachDAO phongCachDAO;
    NoiThatService noiThatService;
    @Autowired
    public PhongCachServiceImpl(PhongCachDAO phongCachDAO, NoiThatService noiThatService) {
        this.phongCachDAO = phongCachDAO;
        this.noiThatService = noiThatService;
    }

    @Override
    public List<PhongCach> findAll() {
        List<PhongCachNoiThatEntity> phongCachNoiThatEntities = phongCachDAO.findAll();
        return phongCachNoiThatEntities.stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, false)).toList();
    }
    @Override
    public PhongCach findById(int id) {
        return new PhongCach(phongCachDAO.findById(id), false);
    }
    @Override
    public PhongCach findUsingName(String name) {
        return new PhongCach(phongCachDAO.findUsingName(name), false);
    }

    @Override
    public void save(PhongCach phongCachNoiThat) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoiThat);
        phongCachDAO.save(phongCachNoiThatEntity);
    }

    @Override
    public void deleteById(int id) {
        phongCachDAO.deleteById(id);
    }

    @Override
    public void update(PhongCach phongCachNoi) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoi);
        phongCachDAO.update(phongCachNoiThatEntity);
    }

    @Override
    public List<PhongCach> joinFetchPhongCach() {
        List<PhongCach> phongCachList = phongCachDAO.findAllAndJoinFetch().stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, true)).toList();;
        List<NoiThat> noiThats = noiThatService.joinFetchNoiThat();
        return phongCachNoiThatEntities
    }

    @Override
    public PhongCach joinFetchPhongCachUsingId(int id) {
        return new PhongCach(phongCachDAO.findByIdAndJoinFetch(id), true);
    }
}
