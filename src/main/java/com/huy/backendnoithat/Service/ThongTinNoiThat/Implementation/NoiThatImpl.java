package com.huy.backendnoithat.Service.ThongTinNoiThat.Implementation;

import com.huy.backendnoithat.Entity.NoiThat;
import com.huy.backendnoithat.Service.ThongTinNoiThat.Interface.NoiThatService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoiThatImpl implements NoiThatService {
    @Override
    public List<NoiThat> findAll() {
        return null;
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
