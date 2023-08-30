package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach.PhongCachDAO;
import com.huy.backendnoithat.Entity.PhongCachNoiThatEntity;
import com.huy.backendnoithat.DataModel.PhongCach;
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
    public List<PhongCach> findAll() {
        List<PhongCachNoiThatEntity> phongCachNoiThatEntities = phongCachDAO.findAll();
        List<PhongCach> phongCachRespons = phongCachNoiThatEntities.stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, false)).toList();
        return phongCachRespons;
    }
    @Override
    public PhongCach findById(int id) {
        PhongCach phongCach = new PhongCach(phongCachDAO.findById(id), false);
        return phongCach;
    }
    @Override
    public PhongCach findUsingName(String name) {
        PhongCach phongCach = new PhongCach(phongCachDAO.findUsingName(name), false);
        return phongCach;
    }

    @Override
    public void save(PhongCach phongCachNoiThatEntity) {

        phongCachDAO.save(phongCachNoiThatEntity);
    }

    @Override
    public void deleteById(int id) {
        phongCachDAO.deleteById(id);
    }

    @Override
    public void update(PhongCach phongCachNoiThatEntity) {
        phongCachDAO.update(phongCachNoiThatEntity);
    }

    @Override
    public List<PhongCach> joinFetchPhongCach() {
        return phongCachDAO.findAllAndJoinFetch().stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, true)).toList();
    }

    @Override
    public PhongCach joinFetchPhongCachUsingId(int id) {
        return new PhongCach(phongCachDAO.findByIdAndJoinFetch(id), true);
    }
}
