package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
import com.huy.backendnoithat.Entity.BangNoiThat.HangMucEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.Service.Account.AccountService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HangMucServiceImpl implements HangMucService {
    HangMucDAO hangMucDAO;
    AccountService accountService;
    JwtTokenUtil jwtTokenUtil;
    NoiThatDAO noiThatDAO;
    @Autowired
    public HangMucServiceImpl(HangMucDAO hangMucDAO, AccountService accountService, JwtTokenUtil jwtTokenUtil, NoiThatDAO noiThatDAO) {
        this.hangMucDAO = hangMucDAO;
        this.accountService = accountService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.noiThatDAO = noiThatDAO;
    }
    @Override
    public List<HangMuc> findAll(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return hangMucDAO.findAll(username).stream().map(hangMuc -> new HangMuc(hangMuc, false)).toList();
    }
    @Override
    public HangMuc findUsingId(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new HangMuc(hangMucDAO.findById(username, id), false);
    }
    @Override
    public HangMuc findUsingName(String token, String name) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new HangMuc(hangMucDAO.findUsingName(username, name), false);
    }
    @Override
    public void save(String token, HangMuc hangMuc, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        HangMucEntity hangMucEntity = new HangMucEntity(hangMuc);
        hangMucDAO.save(username, hangMucEntity, parentId);
    }
    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        hangMucDAO.deleteById(username, id);
    }
    @Override
    public void update(String token, HangMuc hangMuc) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        hangMucDAO.update(username, new HangMucEntity(hangMuc));
    }

    @Override
    public List<HangMuc> joinFetchHangMuc() {
        return null;
    }

    @Override
    public HangMuc joinFetchHangMucUsingId(int id) {
        return null;
    }

    @Override
    public List<HangMuc> searchByNoiThat(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return hangMucDAO.searchByNoiThat(username, id).stream().map(hangMuc -> new HangMuc(hangMuc, false)).toList();
    }

    @Override
    public List<HangMuc> searchBy(String token, String phongCachName, String noiThatName) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return hangMucDAO.searchBy(username, phongCachName, noiThatName).stream().map(
                hangMucEntity -> new HangMuc(hangMucEntity, false)).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
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
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        hangMucDAO.swap(account.getId(), id1, id2);
    }
}
