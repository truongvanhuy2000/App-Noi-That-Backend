package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.Entity.NoiThat;
import com.huy.backendnoithat.Response.NoiThatResponse;
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
    public List<NoiThatResponse> findAll() {
        List<NoiThatResponse> noiThatResponses = noiThatDAO.findAll().stream()
                .map(item -> new NoiThatResponse(item, false)).toList();
        return noiThatResponses;
    }
    @Override
    public NoiThatResponse findUsingId(int id) {
        NoiThat noiThat = noiThatDAO.findById(id);
        return new NoiThatResponse(noiThat, false);
    }
    @Override
    public NoiThatResponse findUsingName(String name) {
        NoiThat noiThat = noiThatDAO.findUsingName(name);
        return new NoiThatResponse(noiThat, false);
    }
    @Override
    public void save(NoiThat noiThat) {
        noiThatDAO.save(noiThat);
    }
    @Override
    public void deleteById(int id) {
        noiThatDAO.deleteById(id);
    }
    @Override
    public void update(NoiThat noiThat) {
        noiThatDAO.update(noiThat);
    }
}
