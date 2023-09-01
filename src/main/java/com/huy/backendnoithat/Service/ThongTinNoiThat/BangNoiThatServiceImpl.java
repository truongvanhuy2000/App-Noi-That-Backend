package com.huy.backendnoithat.Service.ThongTinNoiThat;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.BangNoiThatDAO;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BangNoiThatServiceImpl implements BangNoiThatService{
    private BangNoiThatDAO bangNoiThatDAO;
    @Autowired
    public BangNoiThatServiceImpl(BangNoiThatDAO bangNoiThatDAO) {
        this.bangNoiThatDAO = bangNoiThatDAO;
    }
    @Override
    public List<PhongCachNoiThatEntity> fetchAll() {
        return bangNoiThatDAO.fetchAll();
    }
}
