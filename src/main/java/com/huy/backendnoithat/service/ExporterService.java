package com.huy.backendnoithat.service;

import com.huy.backendnoithat.model.UploadFile;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.model.enums.UploadStatus;
import com.huy.backendnoithat.service.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huytv.exception.ExportException;
import org.huytv.fileExport.ExportFile;
import org.huytv.fileExport.operation.excel.ExportMultipleXLS;
import org.huytv.fileExport.operation.excel.ExportSingleXLS;
import org.huytv.fileExport.operation.ntfile.ExportNtFile;
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

import java.io.*;

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
        byte[] logo = sheetService.getCompanyLogo(userID);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (sheetDataExportDTO.getExportData() == null || sheetDataExportDTO.getExportData().getExportData().isEmpty()) {
            throw new IllegalArgumentException("Data package is null for export");
        }
        if (logo != null) {
            sheetDataExportDTO.getExportData().getExportData().forEach(it -> it.getDataPackage().getThongTinCongTy().setLogo(logo));
        }
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

    public SavedFileDTO saveNoiThatFile(Integer fileId, SheetDataExportDTO sheetDataExportDTO) throws IOException, ExportException {
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
            return fileStorageService.saveFile(FileType.NT_FILE, uploadFile);
        } else {
            fileStorageService.updateFile(uploadFile, FileType.NT_FILE, fileId);
            return SavedFileDTO.builder().id(fileId)
                .fileName(sheetDataExportDTO.getFileName())
                .size(uploadFile.getSize())
                .uploadStatus(UploadStatus.UPLOADED)
                .build();
        }
    }
}
