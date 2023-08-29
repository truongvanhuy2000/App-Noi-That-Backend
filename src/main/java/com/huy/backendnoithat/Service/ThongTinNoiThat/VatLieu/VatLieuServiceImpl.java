package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu.VatLieuDAO;
import com.huy.backendnoithat.Entity.VatLieu;
import com.huy.backendnoithat.Response.VatLieuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VatLieuServiceImpl implements VatLieuService {
    VatLieuDAO vatLieuDAO;
    @Autowired
    public void setVatLieuDAO(VatLieuDAO vatLieuDAO) {
        this.vatLieuDAO = vatLieuDAO;
    }
    @Override
    public List<VatLieuResponse> findAll() {
        return vatLieuDAO.findAll().stream().map(item -> new VatLieuResponse(item, false)).toList();
    }
    @Override
    public VatLieuResponse findUsingId(int id) {
        return new VatLieuResponse(vatLieuDAO.findById(id), false);
    }
    @Override
    public VatLieuResponse findUsingName(String name) {
        return new VatLieuResponse(vatLieuDAO.findUsingName(name), false);
    }
    @Override
    public void save(VatLieu vatLieu) {
        vatLieuDAO.save(vatLieu);
    }
    @Override
    public void deleteById(int id) {
        vatLieuDAO.deleteById(id);
    }
    @Override
    public void update(VatLieu vatLieu) {
        vatLieuDAO.update(vatLieu);
    }
}
