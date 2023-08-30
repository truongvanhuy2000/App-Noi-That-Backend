package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.Entity.NoiThatEntity;
import com.huy.backendnoithat.DataModel.NoiThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoiThatServiceImpl implements NoiThatService {
    NoiThatDAO noiThatDAO;
    @Autowired
    public void setNoiThatDAO(NoiThatDAO noiThatDAO) {
        this.noiThatDAO = noiThatDAO;
    }
    @Override
    public List<NoiThat> findAll() {
        List<NoiThat> noiThatRespons = noiThatDAO.findAll().stream()
                .map(item -> new NoiThat(item, false)).toList();
        return noiThatRespons;
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
    public void save(NoiThatEntity noiThatEntity) {
        noiThatDAO.save(noiThatEntity);
    }
    @Override
    public void deleteById(int id) {
        noiThatDAO.deleteById(id);
    }
    @Override
    public void update(NoiThatEntity noiThatEntity) {
        noiThatDAO.update(noiThatEntity);
    }

    @Override
    public List<NoiThat> joinFetchNoiThat() {
        return null;
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
