package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach.PhongCachDAO;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.DTO.Event.NoiThatUpdate;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import com.huy.backendnoithat.Event.NoiThatUpdateHandler;
import com.huy.backendnoithat.Service.Account.AccountService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PhongCachServiceImpl implements PhongCachService {
    PhongCachDAO phongCachDAO;
    AccountService accountService;
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    NoiThatUpdateHandler noiThatUpdateHandler;
    @Autowired
    public PhongCachServiceImpl(PhongCachDAO phongCachDAO, JwtTokenUtil jwtTokenUtil, AccountService accountService) {
        this.phongCachDAO = phongCachDAO;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountService = accountService;
    }

    @Override
    public List<PhongCach> findAll(String owner) {
        List<PhongCachNoiThatEntity> phongCachNoiThatEntities = phongCachDAO.findAll(owner);
        return phongCachNoiThatEntities.stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, false)).toList();
    }
    @Override
    public PhongCach findById(String owner, int id) {
        return new PhongCach(phongCachDAO.findById(owner, id), false);
    }
    @Override
    public PhongCach findUsingName(String owner, String name) {
        return new PhongCach(phongCachDAO.findUsingName(owner, name), false);
    }

    @Override
    public void save(String owner, PhongCach phongCachNoiThat) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoiThat);
        phongCachDAO.save(owner, phongCachNoiThatEntity);
        createUpdateEvent(owner);
    }

    @Override
    public void deleteById(String owner, int id) {
        phongCachDAO.deleteById(owner, id);
        createUpdateEvent(owner);
    }

    @Override
    public void update(String owner, PhongCach phongCachNoi) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoi);
        phongCachDAO.update(owner, phongCachNoiThatEntity);
        createUpdateEvent(owner);
    }

    @Override
    public List<PhongCach> joinFetchPhongCach(String owner) {
        return phongCachDAO.findAllAndJoinFetch(owner).stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, true)).toList();
    }

    @Override
    public PhongCach joinFetchPhongCachUsingId(String owner, int id) {
        return new PhongCach(phongCachDAO.findByIdAndJoinFetch(owner, id), true);
    }
    @Override
    public void copySampleDataFromAdmin(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        copySampleDataFromAdmin(account.getId());
        createUpdateEvent(username);
    }
    @Override
    public void copySampleDataFromAdmin(int accountId) {
        phongCachDAO.copySampleDataFromAdmin(accountId);
    }
    @Override
    public void swap(String token, int id1, int id2) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        phongCachDAO.swap(account.getId(), id1, id2);
        createUpdateEvent(username);
    }
    private void createUpdateEvent(String username) {
        noiThatUpdateHandler.publish(username, new NoiThatUpdate("PhongCach", username));
    }
}
