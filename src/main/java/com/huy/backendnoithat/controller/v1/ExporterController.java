package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.service.ExporterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huytv.exception.ExportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/exporter")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExporterController {
    private final ExporterService exporterService;

    @Operation(summary = "Export sheet data")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Excel file",
            content = @Content(mediaType = "application/octet-stream",
                schema = @Schema(type = "string", format = "binary")))
    })
    @PostMapping(produces = "application/octet-stream")
    public ResponseEntity<Resource> exportSheetData(@RequestBody SheetDataExportDTO sheetDataExportDTO) throws IOException, ExportException {
        Resource resource = exporterService.exportSheetData(sheetDataExportDTO);
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

    @PostMapping("/save/nt-file")
    public SavedFileDTO saveNoiThatFile(
        @RequestParam(value = "fileId", required = false) Integer fileId,
        @RequestBody SheetDataExportDTO sheetDataExportDTO
    ) {
        try {
            return exporterService.saveNoiThatFile(fileId, sheetDataExportDTO);
        } catch (IOException | ExportException e) {
            log.error("Error saving Noi That file: {}", e.getMessage());
            throw new RuntimeException("Failed to save Noi That file", e);
        }
    }
}
