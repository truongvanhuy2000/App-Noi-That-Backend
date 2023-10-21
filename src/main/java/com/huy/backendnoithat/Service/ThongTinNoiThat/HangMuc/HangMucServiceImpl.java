package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.Entity.BangNoiThat.HangMucEntity;
import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat.NoiThatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HangMucServiceImpl implements HangMucService {
    HangMucDAO hangMucDAO;
    @Autowired
    public HangMucServiceImpl(HangMucDAO hangMucDAO) {
        this.hangMucDAO = hangMucDAO;
    }
    @Override
    public List<HangMuc> findAll(String owner) {
        return hangMucDAO.findAll(owner).stream().map(hangMuc -> new HangMuc(hangMuc, false)).toList();
    }
    @Override
    public HangMuc findUsingId(String owner, int id) {
        return new HangMuc(hangMucDAO.findById(owner, id), false);
    }
    @Override
    public HangMuc findUsingName(String owner, String name) {
        return new HangMuc(hangMucDAO.findUsingName(owner, name), false);
    }
    @Override
    public void save(String owner, HangMuc hangMuc, int parentId) {
        HangMucEntity hangMucEntity = new HangMucEntity(hangMuc);
        hangMucDAO.save(owner, hangMucEntity, parentId);
    }
    @Override
    public void deleteById(String owner, int id) {
        hangMucDAO.deleteById(owner, id);
    }
    @Override
    public void update(String owner, HangMuc hangMuc) {
        hangMucDAO.update(owner, new HangMucEntity(hangMuc));
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
    public List<HangMuc> searchByNoiThat(String owner, int id) {
        return hangMucDAO.searchByNoiThat(owner, id).stream().map(hangMuc -> new HangMuc(hangMuc, false)).toList();
    }

    @Override
    public List<HangMuc> searchBy(String owner, String phongCachName, String noiThatName) {
        return hangMucDAO.searchBy(owner, phongCachName, noiThatName).stream().map(
                hangMucEntity -> new HangMuc(hangMucEntity, false)).toList();
    }
}
