package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Response.HangMucResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HangMucServiceImpl implements HangMucService {
    HangMucDAO hangMucDAO;
    @Autowired
    public void setHangMucDAO(HangMucDAO hangMucDAO) {
        this.hangMucDAO = hangMucDAO;
    }
    @Override
    public List<HangMucResponse> findAll() {
        return hangMucDAO.findAll().stream().map(hangMuc -> new HangMucResponse(hangMuc, false)).toList();
    }
    @Override
    public HangMucResponse findUsingId(int id) {
        return new HangMucResponse(hangMucDAO.findById(id), false);
    }
    @Override
    public HangMucResponse findUsingName(String name) {
        return new HangMucResponse(hangMucDAO.findUsingName(name), false);
    }
    @Override
    public void save(HangMuc hangMuc) {
        hangMucDAO.save(hangMuc);
    }
    @Override
    public void deleteById(int id) {
        hangMucDAO.deleteById(id);
    }
    @Override
    public void update(HangMuc hangMuc) {
        hangMucDAO.update(hangMuc);
    }
}
