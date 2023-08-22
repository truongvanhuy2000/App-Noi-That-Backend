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
    public List<PhongCachNoiThat> findAll() {
        List<PhongCachResponse> responses = null;
//        if (fetchAll){
//            List<PhongCachNoiThat> list = phongCachDAO.findAllAndJoinFetch();
//            responses = list.stream().map(phongCachNoiThat -> new PhongCachResponse(
//                    phongCachNoiThat.getId(),
//                    phongCachNoiThat.getName(),
//                    phongCachNoiThat.getNoiThat()
//            )).toList();
//        } else {
//            List<PhongCachNoiThat> list = phongCachDAO.findAll();
//            responses = list.stream().map(
//                    phongCachNoiThat -> new PhongCachResponse(
//                            phongCachNoiThat.getId(),
//                            phongCachNoiThat.getName(),
//                            null))
//                    .toList();
//        }
        return phongCachDAO.findAll();
    }
    @Override
    public PhongCachNoiThat findById(int id) {
        return phongCachDAO.findById(id);
    }
    @Override
    public PhongCachNoiThat findUsingName(String name) {
        return phongCachDAO.findUsingName(name);
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
