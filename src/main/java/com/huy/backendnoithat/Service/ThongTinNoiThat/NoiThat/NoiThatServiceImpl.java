package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach.PhongCachDAO;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach.PhongCachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoiThatServiceImpl implements NoiThatService {
    NoiThatDAO noiThatDAO;
    PhongCachService phongCachService;
    @Autowired
    public NoiThatServiceImpl(NoiThatDAO noiThatDAO, PhongCachService phongCachService) {
        this.noiThatDAO = noiThatDAO;
        this.phongCachService = phongCachService;
    }
    @Override
    public List<NoiThat> findAll() {
        return noiThatDAO.findAll().stream()
                .map(item -> new NoiThat(item, false)).toList();
    }
    @Override
    public NoiThat findUsingId(int id) {
        NoiThatEntity noiThatEntity = noiThatDAO.findById(id);
        return new NoiThat(noiThatEntity, false);
    }
    @Override
    public NoiThat findUsingName(String name) {
        NoiThatEntity noiThatEntity = noiThatDAO.findUsingName(name);
        return new NoiThat(noiThatEntity, false);
    }
    @Override
    public void save(NoiThat noiThat, int parentId) {
        NoiThatEntity noiThatEntity = new NoiThatEntity(noiThat);
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachService.findById(parentId));
        noiThatEntity.setPhongCachNoiThatEntity(phongCachNoiThatEntity);
        noiThatDAO.save(noiThatEntity);
    }
    @Override
    public void deleteById(int id) {
        noiThatDAO.deleteById(id);
    }
    @Override
    public void update(NoiThat noiThat) {
        noiThatDAO.update(new NoiThatEntity(noiThat));
    }
    @Override
    public List<NoiThat> joinFetchNoiThat() {
        return noiThatDAO.findAllAndJoinFetch().stream()
                .map(item -> new NoiThat(item, true)).toList();
    }

    @Override
    public NoiThat joinFetchNoiThatUsingId(int id) {
        return null;
    }

    @Override
    public List<NoiThat> searchByPhongCach(int id) {
        return noiThatDAO.searchByPhongCach(id).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }
}
