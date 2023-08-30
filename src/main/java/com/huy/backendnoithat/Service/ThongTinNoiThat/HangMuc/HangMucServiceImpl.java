package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.Entity.HangMucEntity;
import com.huy.backendnoithat.DataModel.HangMuc;
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
    public List<HangMuc> findAll() {
        return hangMucDAO.findAll().stream().map(hangMuc -> new HangMuc(hangMuc, false)).toList();
    }
    @Override
    public HangMuc findUsingId(int id) {
        return new HangMuc(hangMucDAO.findById(id), false);
    }
    @Override
    public HangMuc findUsingName(String name) {
        return new HangMuc(hangMucDAO.findUsingName(name), false);
    }
    @Override
    public void save(HangMucEntity hangMucEntity) {
        hangMucDAO.save(hangMucEntity);
    }
    @Override
    public void deleteById(int id) {
        hangMucDAO.deleteById(id);
    }
    @Override
    public void update(HangMucEntity hangMucEntity) {
        hangMucDAO.update(hangMucEntity);
    }

    @Override
    public List<HangMuc> joinFetchHangMuc() {
        return null;
    }

    @Override
    public HangMuc joinFetchHangMucUsingId(int id) {
        return null;
    }

    @Override
    public List<HangMuc> searchByNoiThat(int id) {
        return hangMucDAO.searchByNoiThat(id).stream().map(hangMuc -> new HangMuc(hangMuc, false)).toList();
    }
}
