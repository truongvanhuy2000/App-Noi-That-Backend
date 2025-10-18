package com.huy.backendnoithat.usecase.sheet.export;

import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.usecase.sheet.SheetService;
import com.huy.backendnoithat.usecase.file.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huytv.exception.ExportException;
import org.huytv.fileExport.ExportFile;
import org.huytv.fileExport.operation.excel.ExportMultipleXLS;
import org.huytv.fileExport.operation.excel.ExportSingleXLS;
import org.huytv.fileExport.operation.pdf.HttpServicePdfExport;
import org.huytv.model.SheetFileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExporterService {
    @Value("${export.pdf.converter}")
    private String PDF_EXPORT_URL;

    private final byte[] exportSheetTemplate;
    private final FileStorageService fileStorageService;
    private final RestTemplate restTemplate;
    private final SheetService sheetService;

    public Resource exportSheetData(SheetDataExportDTO sheetDataExportDTO, int userID) throws IOException, ExportException {
        log.info("Exporting sheet data: {} for user: {}", sheetDataExportDTO, userID);

        byte[] logo = sheetService.getCompanyLogo(userID);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (sheetDataExportDTO.getExportData() == null || sheetDataExportDTO.getExportData().getExportData().isEmpty()) {
            throw new IllegalArgumentException("Data package is null for export");
        }
        if (logo != null) {
            sheetDataExportDTO.getExportData().getExportData().forEach(it -> it.getDataPackage().getThongTinCongTy().setLogo(logo));
        }

        log.info("Exporting sheet data with type: {} for user: {}", sheetDataExportDTO.getExportType(), userID);
        switch (sheetDataExportDTO.getExportType()) {
            case XLSX_SINGLE:
                exportSingleXLS(sheetDataExportDTO, outputStream);
                break;
            case XLSX_MULTI:
                exportMultiXLS(sheetDataExportDTO, outputStream);
                break;
            case PDF:
                exportPDF(sheetDataExportDTO, outputStream);
                break;
        }
        return new ByteArrayResource(outputStream.toByteArray());
    }


    private void exportMultiXLS(SheetDataExportDTO sheetDataExportDTO, OutputStream outputStream) throws IOException, ExportException {
        ExportFile exportFile = new ExportMultipleXLS(
            new ByteArrayInputStream(exportSheetTemplate),
            sheetDataExportDTO.getExportData().getExportData()
        );
        exportFile.export(outputStream);
    }

    private void exportSingleXLS(SheetDataExportDTO sheetDataExportDTO, OutputStream outputStream) throws IOException, ExportException {
        SheetFileData sheetFileData = sheetDataExportDTO.getExportData();
        ExportFile exportFile = new ExportSingleXLS(
            new ByteArrayInputStream(exportSheetTemplate),
            sheetFileData.getExportData().get(0).getDataPackage()
        );
        exportFile.export(outputStream);
    }

    private void exportPDF(SheetDataExportDTO sheetDataExportDTO, OutputStream outputStream) throws IOException, ExportException {
        if (sheetDataExportDTO.getExportData() == null || sheetDataExportDTO.getExportData().getExportData().isEmpty()) {
            throw new IllegalArgumentException("Data package is null for export");
        }
        SheetFileData sheetFileData = sheetDataExportDTO.getExportData();
        ExportFile exportFile = new HttpServicePdfExport((s, bytes) -> {
            ByteArrayResource fileResource = new ByteArrayResource(bytes) {
                @Override
                public String getFilename() {
                    return "example.xlsx";
                }
            };
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<byte[]> response = restTemplate.exchange(
                PDF_EXPORT_URL,
                HttpMethod.POST,
                requestEntity,
                byte[].class
            );
            return response.getBody();
        }, new ByteArrayInputStream(exportSheetTemplate), sheetFileData.getExportData().get(0).getDataPackage());
        exportFile.export(outputStream);
    }
}
