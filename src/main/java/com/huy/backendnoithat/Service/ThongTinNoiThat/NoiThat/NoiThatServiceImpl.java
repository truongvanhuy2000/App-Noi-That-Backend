package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.Service.Account.AccountService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach.PhongCachService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoiThatServiceImpl implements NoiThatService {
    NoiThatDAO noiThatDAO;
    AccountService accountService;
    JwtTokenUtil jwtTokenUtil;
    final
    PhongCachService phongCachService;
    @Autowired
    public NoiThatServiceImpl(NoiThatDAO noiThatDAO, AccountService accountService, JwtTokenUtil jwtTokenUtil, PhongCachService phongCachService) {
        this.noiThatDAO = noiThatDAO;
        this.accountService = accountService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.phongCachService = phongCachService;
    }
    @Override
    public List<NoiThat> findAll(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return noiThatDAO.findAll(username).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }
    @Override
    public NoiThat findUsingId(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        NoiThatEntity noiThatEntity = noiThatDAO.findById(username, id);
        return new NoiThat(noiThatEntity, false);
    }
    @Override
    public NoiThat findUsingName(String token, String name) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        NoiThatEntity noiThatEntity = noiThatDAO.findUsingName(username, name);
        return new NoiThat(noiThatEntity, false);
    }
    @Override
    public void save(String token, NoiThat noiThat, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        NoiThatEntity noiThatEntity = new NoiThatEntity(noiThat);
        noiThatDAO.save(username, noiThatEntity, parentId);
    }
    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        noiThatDAO.deleteById(username, id);
    }
    @Override
    public void update(String token, NoiThat noiThat) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        noiThatDAO.update(username, new NoiThatEntity(noiThat));
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
    public List<NoiThat> searchByPhongCach(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return noiThatDAO.searchByPhongCach(username, id).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }

    @Override
    public List<NoiThat> searchBy(String token, String phongCachName) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return noiThatDAO.searchBy(username, phongCachName).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        String parentName = phongCachService.findById(username, parentId).getName();
        copySampleDataFromAdmin(account.getId(), parentId, parentName);
    }
    @Override
    public void copySampleDataFromAdmin(int accountId, int parentId, String parentName) {
        noiThatDAO.copySampleDataFromAdmin(accountId, parentId, parentName);
    }

    @Override
    public void swap(String token, int id1, int id2) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        noiThatDAO.swap(account.getId(), id1, id2);
    }
}
