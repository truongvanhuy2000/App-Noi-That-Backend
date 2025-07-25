package com.huy.backendnoithat.service.v0.thongTinNoiThat.impl;

import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.entity.sheet.HangMucEntity;
import com.huy.backendnoithat.entity.sheet.NoiThatEntity;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.HangMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HangMucServiceImpl implements HangMucService {
    private final HangMucDAO hangMucDAO;
    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;
    private final NoiThatDAO noiThatDAO;
    
    @Override
    public List<HangMuc> findAll(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return hangMucDAO.findAll(username).stream().map(hangMuc -> new HangMuc(hangMuc, false)).toList();
    }

    @Override
    public HangMuc findUsingId(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return new HangMuc(hangMucDAO.findById(username, id), false);
    }

    @Override
    public HangMuc findUsingName(String token, String name) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return new HangMuc(hangMucDAO.findUsingName(username, name), false);
    }

    @Override
    public void save(String token, HangMuc hangMuc, int parentId) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        HangMucEntity hangMucEntity = new HangMucEntity(hangMuc);
        hangMucDAO.save(username, hangMucEntity, parentId);
    }

    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        hangMucDAO.deleteById(username, id);
    }

    @Override
    public void update(String token, HangMuc hangMuc) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        hangMucDAO.update(username, new HangMucEntity(hangMuc));
    }

    @Override
    public List<HangMuc> searchByNoiThat(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return hangMucDAO.searchByNoiThat(username, id).stream().map(hangMuc -> new HangMuc(hangMuc, false)).toList();
    }

    @Override
    public List<HangMuc> searchBy(String token, String phongCachName, String noiThatName) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return hangMucDAO.searchBy(username, phongCachName, noiThatName).stream().map(
                hangMucEntity -> new HangMuc(hangMucEntity, false)).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        Account account = accountService.findByUsername(username);
        NoiThatEntity noiThatEntity = noiThatDAO.findById(username, parentId);
        String noiThatName = noiThatEntity.getName();
        String phongcachName = noiThatEntity.getPhongCachNoiThatEntity().getName();
        copySampleDataFromAdmin(account.getId(), parentId, noiThatName, phongcachName);
    }

    @Override
    public void copySampleDataFromAdmin(int destinationId, int parentId, String noiThatName, String phongCachName) {
        hangMucDAO.copySampleDataFromAdmin(destinationId, parentId, noiThatName, phongCachName);
    }

    @Override
    public void swap(String token, int id1, int id2) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        Account account = accountService.findByUsername(username);
        hangMucDAO.swap(account.getId(), id1, id2);
    }
}
