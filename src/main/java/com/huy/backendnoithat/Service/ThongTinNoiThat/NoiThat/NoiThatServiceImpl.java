package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.Entity.NoiThat;
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
        return noiThatDAO.findAll();
    }
    @Override
    public NoiThat findUsingId(int id) {
        return null;
    }
    @Override
    public NoiThat findUsingName(String name) {
        return null;
    }
    @Override
    public void save(NoiThat noiThat) {

    }
    @Override
    public void deleteById(int id) {

    }
    @Override
    public void update(NoiThat noiThat) {

    }
}
