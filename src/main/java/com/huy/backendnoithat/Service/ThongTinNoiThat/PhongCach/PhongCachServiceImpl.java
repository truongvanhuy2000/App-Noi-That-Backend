package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach.PhongCachDAO;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
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
    public PhongCachServiceImpl(PhongCachDAO phongCachDAO, JwtTokenUtil jwtTokenUtil, AccountService accountService) {
        this.phongCachDAO = phongCachDAO;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountService = accountService;
    }

    @Override
    public List<PhongCach> findAll(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        List<PhongCachNoiThatEntity> phongCachNoiThatEntities = phongCachDAO.findAll(username);
        return phongCachNoiThatEntities.stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, false)).toList();
    }
    @Override
    public PhongCach findById(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new PhongCach(phongCachDAO.findById(username, id), false);
    }
    @Override
    public PhongCach findUsingName(String token, String name) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new PhongCach(phongCachDAO.findUsingName(username, name), false);
    }

    @Override
    public void save(String token, PhongCach phongCachNoiThat) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoiThat);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        phongCachDAO.save(username, phongCachNoiThatEntity);
    }

    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        phongCachDAO.deleteById(username, id);
    }

    @Override
    public void update(String token, PhongCach phongCachNoi) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoi);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        phongCachDAO.update(username, phongCachNoiThatEntity);
    }

    @Override
    public List<PhongCach> joinFetchPhongCach(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return phongCachDAO.findAllAndJoinFetch(username).stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, true)).toList();
    }

    @Override
    public PhongCach joinFetchPhongCachUsingId(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new PhongCach(phongCachDAO.findByIdAndJoinFetch(username, id), true);
    }
    @Override
    public void copySampleDataFromAdmin(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        copySampleDataFromAdmin(account.getId());
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
    }

}
