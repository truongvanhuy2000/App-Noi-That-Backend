package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
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
    NoiThatService noiThatService;
    @Autowired
    public HangMucServiceImpl(HangMucDAO hangMucDAO, NoiThatService noiThatService) {
        this.hangMucDAO = hangMucDAO;
        this.noiThatService = noiThatService;
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
    public void save(HangMuc hangMuc, int parentId) {
        HangMucEntity hangMucEntity = new HangMucEntity(hangMuc);
        NoiThatEntity noiThatEntity = new NoiThatEntity(noiThatService.findUsingId(parentId));
        hangMucEntity.setNoiThatEntity(noiThatEntity);
        hangMucDAO.save(hangMucEntity);
    }
    @Override
    public void deleteById(int id) {
        hangMucDAO.deleteById(id);
    }
    @Override
    public void update(HangMuc hangMuc) {
        hangMucDAO.update(new HangMucEntity(hangMuc));
    }

    @Override
    public List<HangMuc> joinFetchHangMuc() {
        return hangMucDAO.findAllAndJoinFetch().stream().map(hangMuc -> new HangMuc(hangMuc, true)).toList();
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
