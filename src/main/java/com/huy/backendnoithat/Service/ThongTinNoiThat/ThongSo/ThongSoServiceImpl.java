package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo.ThongSoDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu.VatLieuDAO;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.BangNoiThat.ThongSo;
import com.huy.backendnoithat.Entity.BangNoiThat.ThongSoEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;
import com.huy.backendnoithat.Service.Account.AccountService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThongSoServiceImpl implements ThongSoService {
    ThongSoDAO thongSoDAO;
    final VatLieuDAO vatLieuDAO;
    AccountService accountService;
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    public ThongSoServiceImpl(ThongSoDAO thongSoDAO, AccountService accountService, JwtTokenUtil jwtTokenUtil, VatLieuDAO vatLieuDAO) {
        this.thongSoDAO = thongSoDAO;
        this.accountService = accountService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.vatLieuDAO = vatLieuDAO;
    }
    @Override
    public List<ThongSo> findAll(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return thongSoDAO.findAll(username).stream().map(ThongSo::new).toList();
    }
    @Override
    public ThongSo findUsingId(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new ThongSo(thongSoDAO.findById(username, id));
    }

    @Override
    public ThongSo findUsingName(String token, String name) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new ThongSo(thongSoDAO.findUsingName(username, name));
    }

    @Override
    public void save(String token, ThongSo thongSo, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        ThongSoEntity thongSoEntity = new ThongSoEntity(thongSo);
        thongSoDAO.save(username, thongSoEntity, parentId);
    }

    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        thongSoDAO.deleteById(username, id);
    }

    @Override
    public void update(String token, ThongSo thongSo) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        thongSoDAO.update(username, new ThongSoEntity(thongSo));
    }

    @Override
    public List<ThongSo> searchByVatLieu(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return thongSoDAO.searchByVatLieu(username, id).stream().map(ThongSo::new).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        VatLieuEntity vatLieuEntity = vatLieuDAO.findById(username, parentId);
        String vatLieuName = vatLieuEntity.getName();
        String hangMucName = vatLieuEntity.getHangMucEntity().getName();
        String noiThatName = vatLieuEntity.getHangMucEntity().getNoiThatEntity().getName();
        String phongcachName = vatLieuEntity.getHangMucEntity().getNoiThatEntity().getPhongCachNoiThatEntity().getName();
        copySampleDataFromAdmin(account.getId(), parentId, vatLieuName, hangMucName, noiThatName, phongcachName);
    }
    @Override
    public void copySampleDataFromAdmin(int destinationId, int parentId, String vatLieuName,
                                        String hangMucName, String noiThatName, String phongCachName) {
        thongSoDAO.copySampleDataFromAdmin(destinationId, parentId, vatLieuName, hangMucName, noiThatName, phongCachName);
    }
}
