package com.huy.backendnoithat.service;

import com.huy.backendnoithat.model.UploadFile;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.model.enums.ExportType;
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
import org.huytv.model.SheetFileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExporterService {
    private final byte[] exportSheetTemplate;
    private final FileStorageService fileStorageService;

    public Resource exportSheetData(SheetDataExportDTO sheetDataExportDTO) throws IOException, ExportException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        switch (sheetDataExportDTO.getExportType()) {
            case XLSX_SINGLE:
                exportSingleXLS(sheetDataExportDTO, outputStream);
                break;
            case XLSX_MULTI:
                exportMultiXLS(sheetDataExportDTO, outputStream);
            break;
            default:
                log.error("Unsupported export type: {}", sheetDataExportDTO.getExportType());
        }
        return new ByteArrayResource(outputStream.toByteArray());
    }


    private void exportMultiXLS(SheetDataExportDTO sheetDataExportDTO, OutputStream outputStream) throws IOException, ExportException {
        if (sheetDataExportDTO.getExportData() == null || sheetDataExportDTO.getExportData().getExportData().isEmpty()) {
            throw new IllegalArgumentException("Data package is null for export");
        }
        ExportFile exportFile = new ExportMultipleXLS(
            new ByteArrayInputStream(exportSheetTemplate),
            sheetDataExportDTO.getExportData().getExportData()
        );
        exportFile.export(outputStream);
    }

    private void exportSingleXLS(SheetDataExportDTO sheetDataExportDTO, OutputStream outputStream) throws IOException, ExportException {
        if (sheetDataExportDTO.getExportData() == null || sheetDataExportDTO.getExportData().getExportData().isEmpty()) {
            log.error("Data package is null for export");
            return;
        }
        SheetFileData sheetFileData = sheetDataExportDTO.getExportData();
        ExportFile exportFile = new ExportSingleXLS(
            new ByteArrayInputStream(exportSheetTemplate),
            sheetFileData.getExportData().get(0).getDataPackage()
        );
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
