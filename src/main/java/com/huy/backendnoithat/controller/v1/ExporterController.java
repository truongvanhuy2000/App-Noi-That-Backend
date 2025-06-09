package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.service.ExporterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/exporter")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExporterController {
    private final ExporterService exporterService;

    @PostMapping("")
    public ResponseEntity<String> exportSheetData(@RequestBody SheetDataExportDTO sheetDataExportDTO) {
        exporterService.export(sheetDataExportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
