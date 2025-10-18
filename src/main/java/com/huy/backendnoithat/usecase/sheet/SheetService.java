package com.huy.backendnoithat.usecase.sheet;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.huy.backendnoithat.controller.file.dto.FileSearchRequest;
import com.huy.backendnoithat.controller.sheet.file.dto.SheetSearchRequest;
import com.huy.backendnoithat.dao.LapBaoGiaInfoDAO;
import com.huy.backendnoithat.entity.LapBaoGiaInfoEntity;
import com.huy.backendnoithat.entity.SavedFileEntity;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.exception.AuthorizationException;
import com.huy.backendnoithat.usecase.cache.CacheService;
import com.huy.backendnoithat.usecase.file.manager.SavedFileEntityManager;
import com.huy.backendnoithat.usecase.sheet.management.v1.mapper.SavedFileEntityDTOMapper;
import com.huy.backendnoithat.model.*;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.usecase.file.storage.FileStorageService;
import com.huy.backendnoithat.usecase.token.JwtTokenService;
import com.huy.backendnoithat.usecase.account.management.AccountManagementService;
import com.nimbusds.jose.util.Base64;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huytv.exception.ExportException;
import org.huytv.fileExport.ExportFile;
import org.huytv.fileExport.operation.ntfile.ExportNtFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@CacheConfig(cacheNames = "sheetServiceCache")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SheetService {
    @Value("${token.pre-signed-token.ttlDay}")
    private int preSignedTokenTtlDay;
    @Value("${cache.file-in-use.ttlSec}")
    private int fileInUseTtlSec;

    private static final String PRE_SIGNED_TOKEN_CACHE_KEY_FORMAT = "pst%s%s%s";
    private static final String FILE_INUSE_CACHE_KEY_FORMAT = "fileInUseCache_%s";

    private final JwtTokenService jwtTokenService;
    private final FileStorageService fileStorageService;
    private final LapBaoGiaInfoDAO lapBaoGiaInfoDAO;
    private final SavedFileEntityManager savedFileEntityManager;
    private final SavedFileEntityDTOMapper savedFileEntityDTOMapper;
    private final AccountManagementService accountManagementService;
    private final CacheService cacheService;

    public PreSignedToken shareSheetFile(Integer fileId, int userID) {
        log.info("Sharing sheet file with file ID: {}, user ID: {}", fileId, userID);

        // Simulate the generation of a pre-signed token for sharing a sheet file
        if (fileId == null || fileId <= 0) {
            throw new IllegalArgumentException("Invalid file ID provided.");
        }
        Map<String, Object> claims = Map.of(
            Constant.USER_SHARE, userID,
            Constant.CONTENT_ID, fileId,
            Constant.ACTION, Constant.ACTION_SHARE
        );
        Date expirationDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(preSignedTokenTtlDay));
        log.info("Generating pre-signed token for file ID: {}, user ID: {}", fileId, userID);
        String token = jwtTokenService.generateToken(claims, expirationDate);
        String id = NanoIdUtils.randomNanoId();
        String cacheKey = Base64.encode(String.format(PRE_SIGNED_TOKEN_CACHE_KEY_FORMAT, fileId, userID, id)).toString();
        cacheService.put(cacheKey, token, TimeUnit.DAYS.toSeconds(preSignedTokenTtlDay));
        return PreSignedToken.builder()
            .token(cacheKey)
            .resourceId(fileId.toString())
            .build();
    }

    public String validatePreSignedToken(String cacheKey) {
        log.info("Validating pre-signed token with cacheKey {}", cacheKey);

        String token = (String) cacheService.get(cacheKey);

        if (token == null || token.isBlank()) {
            log.error("Token must not be null or empty");
            throw new AuthorizationException("Token must not be null or empty");
        }

        if (!jwtTokenService.verifyToken(token)) {
            log.error("Invalid token provided: {}", token);
            throw new AuthorizationException("Invalid token provided");
        }
        Map<String, Object> claims = jwtTokenService.getClaimsFromToken(token);
        if (claims == null || claims.isEmpty()) {
            log.error("Invalid token claims: {}", claims);
            throw new AuthorizationException("Invalid token claims");
        }
        if (claims.get(Constant.ACTION) == null || claims.get(Constant.CONTENT_ID) == null || claims.get(Constant.USER_SHARE) == null) {
            log.error("Token claims missing required fields: {}", claims);
            throw new AuthorizationException("Token claims missing required fields");
        }
        if (!claims.get(Constant.ACTION).equals(Constant.ACTION_SHARE)) {
            log.error("Invalid action in token claims: {}", claims.get(Constant.ACTION));
            throw new AuthorizationException("Invalid action in token claims");
        }
        return token;
    }

    public SavedFileDTO viewPreSignedSheetContent(String cacheKey) {
        log.info("Viewing pre-signed sheet content");
        String token = this.validatePreSignedToken(cacheKey);
        Map<String, Object> claims = jwtTokenService.getClaimsFromToken(token);

        Long fileID = (Long) claims.get(Constant.CONTENT_ID);
        return fileStorageService.getFile(fileID.intValue(), FileType.NT_FILE);
    }

    @Transactional
    @Cacheable(value = "companyLogoCache", key = "#userID", unless = "#result == null || #result.length == 0")
    public byte[] getCompanyLogo(int userID) {
        log.info("Getting company logo for user ID: {}", userID);

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
        try {
            return savedFileDTO.getInputStream().readAllBytes();
        } catch (IOException e) {
            log.error("Error reading company logo for user ID {}: {}", userID, e.getMessage());
            return null; // Return null if there's an error reading the file
        }
    }

    @Transactional
    @CacheEvict(value = "companyLogoCache", key = "#userID")
    public SavedFileDTO uploadCompanyLogo(int userID, UploadFile uploadFile) {
        log.info("Uploading company logo for user ID: {} uploadFile: {}", userID, uploadFile);

        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByAccountId(userID);
        if (lapBaoGiaInfoEntity == null) {
            log.info("Creating new LapBaoGiaInfoEntity for user ID: {}", userID);
            lapBaoGiaInfoEntity = new LapBaoGiaInfoEntity();
            lapBaoGiaInfoEntity.setAccount(AccountEntity.builder().id(userID).build());
        }
        SavedFileEntity companyLogo = lapBaoGiaInfoEntity.getCompanyLogo();
        if (companyLogo != null) {
            log.info("Deleting existing company logo for user ID: {}", userID);
            fileStorageService.deleteFile(companyLogo.getId(), FileType.IMAGE_FILE);
            lapBaoGiaInfoEntity.setCompanyLogo(null); // Clear the existing logo
        }
        SavedFileDTO savedFileDTO = fileStorageService.saveFile(FileType.IMAGE_FILE, uploadFile);
        if (savedFileDTO == null) {
            log.error("Failed to save company logo for user ID: {}", userID);
            throw new RuntimeException("Failed to save company logo");
        }
        lapBaoGiaInfoEntity.setCompanyLogo(SavedFileEntity.builder().id(savedFileDTO.getId()).build());
        lapBaoGiaInfoDAO.save(lapBaoGiaInfoEntity);
        log.info("Company logo uploaded successfully for user ID: {}", userID);
        return savedFileDTO;
    }

    @Cacheable(value = "sheetFileCache", key = "#fileID", unless = "#result == null")
    public SheetFileDTO getSheetFile(int fileID) throws IOException {
        log.info("Getting sheet file with ID: {}", fileID);

        SavedFileDTO savedFileDTO = fileStorageService.getFile(fileID, FileType.NT_FILE);
        if (savedFileDTO == null) {
            log.error("File not found with ID: {}", fileID);
            throw new IllegalArgumentException("File not found with ID: " + fileID);
        }
        return new SheetFileDTO(savedFileDTO.getId(), savedFileDTO.getFileName(),
            savedFileDTO.getInputStream().readAllBytes(), savedFileDTO.getSize());
    }

    @CachePut(value = "sheetFileCache", key = "#fileId", condition = "#fileId != null")
    public SheetFileDTO saveSheetFile(Integer fileId, SheetDataExportDTO sheetDataExportDTO) throws IOException, ExportException {
        log.info("Saving sheet file with ID: {}, export data: {}", fileId, sheetDataExportDTO);

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
            log.info("Saving new sheet file with name: {}", sheetDataExportDTO.getFileName());

            SavedFileDTO savedFileDTO = fileStorageService.saveFile(FileType.NT_FILE, uploadFile);
            return new SheetFileDTO(savedFileDTO.getId(), sheetDataExportDTO.getFileName(), fileContent, savedFileDTO.getSize());
        } else {
            log.info("Updating existing sheet file with ID: {}", fileId);
            fileStorageService.updateFile(uploadFile, FileType.NT_FILE, fileId);
            return new SheetFileDTO(fileId, sheetDataExportDTO.getFileName(), fileContent, uploadFile.getSize());
        }
    }

    public PaginationResponse<List<SavedFileDTO>> searchSheetFiles(
        int userID, PaginationRequest paginationRequest, SheetSearchRequest sheetSearchRequest
    ) {
        log.info("Searching sheet files with paginationRequest: {}, sheetSearchRequest: {}", paginationRequest, sheetSearchRequest);

        var entities = savedFileEntityManager.search(
            paginationRequest.getPage(),
            paginationRequest.getSize(),
            FileSearchRequest.builder()
                .userId(userID)
                .fileType(FileType.NT_FILE)
                .fileName(sheetSearchRequest.getFileName())
                .userId(userID)
                .build()
        );
        List<SavedFileDTO> savedFileDTOList = entities.stream()
            .map(savedFileEntityDTOMapper::toDTO)
            .peek(savedFileDTO -> {
                String cacheKey = String.format(FILE_INUSE_CACHE_KEY_FORMAT, savedFileDTO.getId());
                savedFileDTO.setInUse(cacheService.get(cacheKey) != null);
            }).toList();
        return PaginationResponse.<List<SavedFileDTO>>builder()
            .total(entities.getTotalSize())
            .data(savedFileDTOList)
            .build();
    }

    public SheetStats getSheetStats(int userID) {
        log.info("Getting sheet stats for user ID: {}", userID);

        Account account = accountManagementService.findById(userID);
        var result = fileStorageService.find(
            PaginationRequest.builder().size(1).page(0).build(),
            FileSearchRequest.builder().userId(userID).fileType(FileType.NT_FILE).build()
        );

        long totalFiles = result.getTotal();
        return SheetStats.builder()
            .totalSheets((int) totalFiles)
            .limitSheets(account.getFileLimit())
            .build();
    }

    public void takeEditingSheet(String identification, int fileID) {
        log.info("Taking editing sheet with file ID: {}, identification: {}", fileID, identification);
        String cacheKey = String.format(FILE_INUSE_CACHE_KEY_FORMAT, fileID);
        cacheService.put(cacheKey, "INUSED", TimeUnit.SECONDS.toSeconds(fileInUseTtlSec));
    }

    public void releaseEditingSheet(int fileID) {
        log.info("Releasing editing sheet with file ID: {}", fileID);
        String cacheKey = String.format(FILE_INUSE_CACHE_KEY_FORMAT, fileID);
        cacheService.remove(cacheKey);
    }

    public boolean isSheetInUse(int fileId) {
        String cacheKey = String.format(FILE_INUSE_CACHE_KEY_FORMAT, fileId);
        return cacheService.get(cacheKey) != null;
    }

    public interface Constant {
        String ACTION = "action";
        String CONTENT_ID = "contentId";
        String ACTION_SHARE = "share";
        String USER_SHARE = "userShare";
    }
}
