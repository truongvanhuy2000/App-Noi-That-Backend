package com.huy.backendnoithat.service.v0.thongTinNoiThat.impl;

import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.NoiThatService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.PhongCachService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NoiThatServiceImpl implements NoiThatService {
    private final NoiThatDAO noiThatDAO;
    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;
    private final PhongCachService phongCachService;
    
    @Override
    public List<NoiThat> findAll(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return noiThatDAO.findAll(username).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }

    @Override
    public NoiThat findUsingId(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        NoiThatEntity noiThatEntity = noiThatDAO.findById(username, id);
        return new NoiThat(noiThatEntity, false);
    }

    @Override
    public NoiThat findUsingName(String token, String name) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        NoiThatEntity noiThatEntity = noiThatDAO.findUsingName(username, name);
        return new NoiThat(noiThatEntity, false);
    }

    @Override
    public void save(String token, NoiThat noiThat, int parentId) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        NoiThatEntity noiThatEntity = new NoiThatEntity(noiThat);
        noiThatDAO.save(username, noiThatEntity, parentId);
    }

    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        noiThatDAO.deleteById(username, id);
    }

    @Override
    public void update(String token, NoiThat noiThat) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        noiThatDAO.update(username, new NoiThatEntity(noiThat));
    }

    @Override
    public List<NoiThat> searchByPhongCach(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return noiThatDAO.searchByPhongCach(username, id).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }

    @Override
    public List<NoiThat> searchBy(String token, String phongCachName) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return noiThatDAO.searchBy(username, phongCachName).stream()
                .map(item -> new NoiThat(item, false)).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
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
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        Account account = accountService.findByUsername(username);
        noiThatDAO.swap(account.getId(), id1, id2);
    }
}
