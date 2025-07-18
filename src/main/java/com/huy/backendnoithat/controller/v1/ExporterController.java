package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.service.ExporterService;
import com.huy.backendnoithat.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huytv.exception.ExportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/exporter")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExporterController {
    private final ExporterService exporterService;

    @PostMapping(produces = "application/octet-stream")
    public ResponseEntity<Resource> exportSheetData(@RequestBody SheetDataExportDTO sheetDataExportDTO) throws IOException, ExportException {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        Resource resource = exporterService.exportSheetData(sheetDataExportDTO, userID);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
            ContentDisposition.builder("attachment")
                .filename("exported_data.xlsx")
                .build()
        );
        long contentLength = resource.contentLength();
        headers.setContentLength(contentLength);
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
