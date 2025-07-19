package com.huy.backendnoithat.service.v0.thongTinNoiThat.impl;

import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.PhongCach.PhongCachDAO;
import com.huy.backendnoithat.entity.sheet.PhongCachNoiThatEntity;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.BangNoiThat.PhongCach;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.PhongCachService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PhongCachServiceImpl implements PhongCachService {
    private final PhongCachDAO phongCachDAO;
    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;

    @Override
    public List<PhongCach> findAll(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        List<PhongCachNoiThatEntity> phongCachNoiThatEntities = phongCachDAO.findAll(username);
        return phongCachNoiThatEntities.stream()
                .map(phongCachNoiThat -> new PhongCach(phongCachNoiThat, false)).toList();
    }

    @Override
    public PhongCach findById(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return new PhongCach(phongCachDAO.findById(username, id), false);
    }

    @Override
    public PhongCach findUsingName(String token, String name) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return new PhongCach(phongCachDAO.findUsingName(username, name), false);
    }

    @Override
    public void save(String token, PhongCach phongCachNoiThat) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoiThat);
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        phongCachDAO.save(username, phongCachNoiThatEntity);
    }

    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        phongCachDAO.deleteById(username, id);
    }

    @Override
    public void update(String token, PhongCach phongCachNoi) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = new PhongCachNoiThatEntity(phongCachNoi);
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        phongCachDAO.update(username, phongCachNoiThatEntity);
    }

    @Override
    public void copySampleDataFromAdmin(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        Account account = accountService.findByUsername(username);
        copySampleDataFromAdmin(account.getId());
    }

    @Override
    public void copySampleDataFromAdmin(int accountId) {
        phongCachDAO.copySampleDataFromAdmin(accountId);
    }

    @Override
    public void swap(String token, int id1, int id2) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        Account account = accountService.findByUsername(username);
        phongCachDAO.swap(account.getId(), id1, id2);
    }

}
