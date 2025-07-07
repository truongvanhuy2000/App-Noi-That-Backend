package com.huy.backendnoithat.controller.v1.publics;

import com.huy.backendnoithat.exception.AuthorizationException;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.service.ExporterService;
import com.huy.backendnoithat.service.SheetService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.huytv.exception.ExportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/public/sheet/pre-signed")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SheetPublicViewController {
    private final SheetService sheetService;
    private final ExporterService exporterService;
    private final JwtTokenService jwtTokenService;

    @GetMapping("/view-content/{token}")
    public ResponseEntity<Resource> getPreSignedSheetContent(@PathVariable String token) {
        SavedFileDTO savedFileDTO = sheetService.viewPreSignedSheetContent(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(savedFileDTO.getSize());
        Resource inputStreamResource = new InputStreamResource(savedFileDTO.getInputStream());
        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }

    @GetMapping("/view/company-logo/{token}")
    public ResponseEntity<Resource> getPreSignedCompanyLogo(@PathVariable String token) throws IOException {
        if (!sheetService.validatePreSignedToken(token)) {
            throw new AuthorizationException("Invalid or expired token");
        }
        Map<String, Object> claims = jwtTokenService.getClaimsFromToken(token);
        Long userId = (Long) claims.get(SheetService.Constant.USER_SHARE);
        byte[] logo = sheetService.getCompanyLogo(userId.intValue());
        if (logo == null || logo.length == 0) {
            return null;
        }
        Resource resource = new ByteArrayResource(logo);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @PostMapping(value = "/export-content/{token}", produces = "application/octet-stream")
    public ResponseEntity<Resource> exportPreSignedSheetData(
        @PathVariable String token,
        @RequestBody SheetDataExportDTO sheetDataExportDTO
    ) throws IOException, ExportException {
        if (!sheetService.validatePreSignedToken(token)) {
            throw new AuthorizationException("Invalid or expired token");
        }
        Map<String, Object> claims = jwtTokenService.getClaimsFromToken(token);
        Long userId = (Long) claims.get(SheetService.Constant.USER_SHARE);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("exported_data.xlsx").build());
        Resource resource = exporterService.exportSheetData(sheetDataExportDTO, userId.intValue());
        long contentLength = resource.contentLength();
        headers.setContentLength(contentLength);
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
