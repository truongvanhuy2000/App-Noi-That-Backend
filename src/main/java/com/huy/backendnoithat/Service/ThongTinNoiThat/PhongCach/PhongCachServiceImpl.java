package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach.PhongCachDAO;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PhongCachServiceImpl implements PhongCachService {
    PhongCachDAO phongCachDAO;
    @Autowired
    public void setPhongCachDAO(PhongCachDAO phongCachDAO) {
        this.phongCachDAO = phongCachDAO;
    }
    @Override
    public List<PhongCach> findAll(String owner) {
        List<PhongCachNoiThatEntity> phongCachNoiThatEntities = phongCachDAO.findAll();
        return phongCachNoiThatEntities.stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, false)).toList();
    }
    @Override
    public PhongCach findById(String owner, int id) {
        return new PhongCach(phongCachDAO.findById(id), false);
    }
    @Override
    public PhongCach findUsingName(String owner, String name) {
        return new PhongCach(phongCachDAO.findUsingName(name), false);
    }

    @Override
    public void save(String owner, PhongCach phongCachNoiThat) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoiThat);
        phongCachDAO.save(phongCachNoiThatEntity);
    }

    @Override
    public void deleteById(String owner, int id) {
        phongCachDAO.deleteById(id);
    }

    @Override
    public void update(String owner, PhongCach phongCachNoi) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoi);
        phongCachDAO.update(phongCachNoiThatEntity);
    }

    @Override
    public List<PhongCach> joinFetchPhongCach(String owner) {
        return phongCachDAO.findAllAndJoinFetch().stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, true)).toList();
    }

    @Override
    public PhongCach joinFetchPhongCachUsingId(String owner, int id) {
        return new PhongCach(phongCachDAO.findByIdAndJoinFetch(id), true);
    }
}
