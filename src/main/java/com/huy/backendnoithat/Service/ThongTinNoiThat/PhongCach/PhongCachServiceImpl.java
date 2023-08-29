package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach.PhongCachDAO;
import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import com.huy.backendnoithat.Response.PhongCachResponse;
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
    public List<PhongCachResponse> findAll() {
        List<PhongCachNoiThat> phongCachNoiThats = phongCachDAO.findAll();
        List<PhongCachResponse> phongCachResponses = phongCachNoiThats.stream()
                .map(phongCachNoiThat -> new PhongCachResponse(phongCachNoiThat, false)).toList();
        return phongCachResponses;
    }
    @Override
    public PhongCachResponse findById(int id) {
        PhongCachResponse phongCachResponse = new PhongCachResponse(phongCachDAO.findById(id), false);
        return phongCachResponse;
    }
    @Override
    public PhongCachResponse findUsingName(String name) {
        PhongCachResponse phongCachResponse = new PhongCachResponse(phongCachDAO.findUsingName(name), false);
        return phongCachResponse;
    }

    @Override
    public void save(PhongCachNoiThat phongCachNoiThat) {
        phongCachDAO.save(phongCachNoiThat);
    }

    @Override
    public void deleteById(int id) {
        phongCachDAO.deleteById(id);
    }

    @Override
    public void update(PhongCachNoiThat phongCachNoiThat) {
        phongCachDAO.update(phongCachNoiThat);
    }
}
