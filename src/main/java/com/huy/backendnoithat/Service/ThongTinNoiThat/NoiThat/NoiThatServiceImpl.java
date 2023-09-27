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
    @Autowired
    public NoiThatServiceImpl(NoiThatDAO noiThatDAO) {
        this.noiThatDAO = noiThatDAO;
    }
    @Override
    public List<NoiThat> findAll(String owner) {
        return noiThatDAO.findAll(owner).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }
    @Override
    public NoiThat findUsingId(String owner, int id) {
        NoiThatEntity noiThatEntity = noiThatDAO.findById(owner, id);
        return new NoiThat(noiThatEntity, false);
    }
    @Override
    public NoiThat findUsingName(String owner, String name) {
        NoiThatEntity noiThatEntity = noiThatDAO.findUsingName(owner, name);
        return new NoiThat(noiThatEntity, false);
    }
    @Override
    public void save(String owner, NoiThat noiThat, int parentId) {
        NoiThatEntity noiThatEntity = new NoiThatEntity(noiThat);
        noiThatDAO.save(owner, noiThatEntity, parentId);
    }
    @Override
    public void deleteById(String owner, int id) {
        noiThatDAO.deleteById(owner, id);
    }
    @Override
    public void update(String owner, NoiThat noiThat) {
        noiThatDAO.update(owner, new NoiThatEntity(noiThat));
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
    public List<NoiThat> searchByPhongCach(String owner, int id) {
        return noiThatDAO.searchByPhongCach(owner, id).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }
}
