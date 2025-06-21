package com.huy.backendnoithat.service;

import com.huy.backendnoithat.exception.AuthorizationException;
import com.huy.backendnoithat.model.PreSignedToken;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.service.file.FileStorageService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SheetService {
    private final JwtTokenService jwtTokenService;
    private final FileStorageService fileStorageService;

    public PreSignedToken shareSheetFile(Integer fileId) {
        // Simulate the generation of a pre-signed token for sharing a sheet file
        if (fileId == null || fileId <= 0) {
            throw new IllegalArgumentException("Invalid file ID provided.");
        }
        Map<String, Object> claims = Map.of(
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

    private interface Constant {
        String ACTION = "action";
        String CONTENT_ID = "contentId";
        String ACTION_SHARE = "share";
    }
}
