package com.huy.backendnoithat.service;

import com.huy.backendnoithat.dao.LapBaoGiaInfoDAO;
import com.huy.backendnoithat.entity.LapBaoGiaInfoEntity;
import com.huy.backendnoithat.entity.SavedFileEntity;
import com.huy.backendnoithat.exception.AuthorizationException;
import com.huy.backendnoithat.model.PreSignedToken;
import com.huy.backendnoithat.model.SheetFileDTO;
import com.huy.backendnoithat.model.UploadFile;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.service.file.FileStorageService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.huytv.exception.ExportException;
import org.huytv.fileExport.ExportFile;
import org.huytv.fileExport.operation.ntfile.ExportNtFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@CacheConfig(cacheNames = "sheetServiceCache")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SheetService {
    private final JwtTokenService jwtTokenService;
    private final FileStorageService fileStorageService;
    private final LapBaoGiaInfoDAO lapBaoGiaInfoDAO;
    private final CacheManager cacheManager;

    public PreSignedToken shareSheetFile(Integer fileId, int userID) {
        // Simulate the generation of a pre-signed token for sharing a sheet file
        if (fileId == null || fileId <= 0) {
            throw new IllegalArgumentException("Invalid file ID provided.");
        }
        Map<String, Object> claims = Map.of(
            Constant.USER_SHARE, userID,
            Constant.CONTENT_ID, fileId,
            Constant.ACTION, Constant.ACTION_SHARE
        );
        Date expirationDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
        String token = jwtTokenService.generateToken(claims, expirationDate);
        return PreSignedToken.builder()
            .token(token)
            .resourceId(fileId.toString())
            .build();
    }

    public boolean validatePreSignedToken(String token) {
        if (!jwtTokenService.verifyToken(token)) {
            return false;
        }
        Map<String, Object> claims = jwtTokenService.getClaimsFromToken(token);
        if (claims == null || claims.isEmpty()) {
            return false;
        }
        if (claims.get(Constant.ACTION) == null || claims.get(Constant.CONTENT_ID) == null || claims.get(Constant.USER_SHARE) == null) {
            return false;
        }
        if (!claims.get(Constant.ACTION).equals(Constant.ACTION_SHARE)) {
            return false;
        }
        return true;
    }

    public SavedFileDTO viewPreSignedSheetContent(String token) {
        if (token == null || token.isBlank()) {
            throw new AuthorizationException("Token must not be null or empty");
        }
        if (!jwtTokenService.verifyToken(token)) {
            throw new AuthorizationException("Invalid token provided");
        }
        Map<String, Object> claims = jwtTokenService.getClaimsFromToken(token);
        if (claims.get(Constant.ACTION) == null || !claims.get(Constant.ACTION).equals(Constant.ACTION_SHARE)) {
            throw new AuthorizationException("Invalid action in token claims");
        }
        Long fileID = (Long) claims.get(Constant.CONTENT_ID);
        return fileStorageService.getFile(fileID.intValue(), FileType.NT_FILE);
    }

    @Transactional
    @Cacheable(value = "companyLogoCache", key = "#userID")
    public byte[] getCompanyLogo(int userID) throws IOException {
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByAccountId(userID);
        if (lapBaoGiaInfoEntity == null) {
            return null;
        }
        SavedFileEntity companyLogo = lapBaoGiaInfoEntity.getCompanyLogo();
        if (companyLogo == null) {
            return null; // No logo available
        }
        SavedFileDTO savedFileDTO = fileStorageService.getFile(companyLogo.getId(), FileType.IMAGE_FILE);
        if (savedFileDTO == null) {
            return null; // No file found
        }
        return savedFileDTO.getInputStream().readAllBytes();
    }

    @Transactional
    @CacheEvict(value = "companyLogoCache", key = "#userID")
    public SavedFileDTO uploadCompanyLogo(int userID, UploadFile uploadFile) {
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByAccountId(userID);
        if (lapBaoGiaInfoEntity == null) {
            lapBaoGiaInfoEntity = new LapBaoGiaInfoEntity();
        }
        SavedFileDTO savedFileDTO = fileStorageService.saveFile(FileType.IMAGE_FILE, uploadFile);
        if (savedFileDTO == null) {
            throw new RuntimeException("Failed to save company logo");
        }
        lapBaoGiaInfoEntity.setCompanyLogo(SavedFileEntity.builder().id(savedFileDTO.getId()).build());
        lapBaoGiaInfoDAO.save(lapBaoGiaInfoEntity);
        return savedFileDTO;
    }

    @Cacheable(value = "sheetFileCache", key = "#fileID")
    public SheetFileDTO getSheetFile(int fileID) throws IOException {
        SavedFileDTO savedFileDTO = fileStorageService.getFile(fileID, FileType.NT_FILE);
        if (savedFileDTO == null) {
            throw new IllegalArgumentException("File not found with ID: " + fileID);
        }
        return new SheetFileDTO(savedFileDTO.getId(), savedFileDTO.getFileName(),
            savedFileDTO.getInputStream().readAllBytes(), savedFileDTO.getSize());
    }

    @CachePut(value = "sheetFileCache", key = "#fileId", condition = "#fileId != null")
    public SheetFileDTO saveSheetFile(Integer fileId, SheetDataExportDTO sheetDataExportDTO) throws IOException, ExportException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExportFile exportFile = new ExportNtFile(sheetDataExportDTO.getExportData());
        exportFile.export(byteArrayOutputStream);
        byte[] fileContent = byteArrayOutputStream.toByteArray();
        var uploadFile = UploadFile.builder()
            .contentType("application/nt-file")
            .fileName(sheetDataExportDTO.getFileName())
            .size(fileContent.length)
            .inputStream(new ByteArrayInputStream(fileContent))
            .build();

        if (fileId == null) {
            SavedFileDTO savedFileDTO = fileStorageService.saveFile(FileType.NT_FILE, uploadFile);
            return new SheetFileDTO(savedFileDTO.getId(), sheetDataExportDTO.getFileName(), fileContent, savedFileDTO.getSize());
        } else {
            fileStorageService.updateFile(uploadFile, FileType.NT_FILE, fileId);
            return new SheetFileDTO(fileId, sheetDataExportDTO.getFileName(), fileContent, uploadFile.getSize());
        }
    }

    public interface Constant {
        String ACTION = "action";
        String CONTENT_ID = "contentId";
        String ACTION_SHARE = "share";
        String USER_SHARE = "userShare";
    }
}
