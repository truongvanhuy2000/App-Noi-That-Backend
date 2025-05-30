//package com.huy.backendnoithat.service.general.implementation;
//
//import com.huy.backendnoithat.dao.FileStorageDAO;
//import com.huy.backendnoithat.entity.SavedFileEntity;
//import com.huy.backendnoithat.exception.FileStorageException;
//import com.huy.backendnoithat.model.dto.AccountManagement.Account;
//import com.huy.backendnoithat.model.dto.SavedFileDTO;
//import com.huy.backendnoithat.service.aws.S3Service;
//import com.huy.backendnoithat.service.file.S3FileStorageService;
//import jakarta.servlet.ServletOutputStream;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.unit.DataSize;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.ResponseInputStream;
//import software.amazon.awssdk.services.s3.model.GetObjectResponse;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.util.concurrent.CompletableFuture;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.*;
//import static org.mockito.Mockito.times;
//
//@ExtendWith(MockitoExtension.class)
//class S3FileStorageServiceTest {
//    @Mock
//    private S3Service s3Service;
//    @InjectMocks
//    private S3FileStorageService fileStorageService;
//    @Mock
//    private FileStorageDAO fileStorageDAO;
//    @Mock
//    private ModelMapper modelMapper;
//
//    @BeforeEach
//    void init() throws IOException {
//
//    }
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void saveNtFile_happyPath() throws IOException {
//        Account testAccount = Account.builder().id(999).username("test").build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(testAccount, "");
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        long contentLength = DataSize.ofMegabytes(10).toBytes();
//        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
//
//        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
//        given(multipartFile.getInputStream()).willReturn(new ByteArrayInputStream(byteBuffer.array()));
//        given(multipartFile.getOriginalFilename()).willReturn("testFile");
//        given(multipartFile.getSize()).willReturn(contentLength);
//
//        SavedFileEntity savedFile = SavedFileEntity.builder().build();
//        CompletableFuture future = Mockito.mock(CompletableFuture.class);
//        given(fileStorageDAO.save(any())).willReturn(savedFile);
//        given(s3Service.putObjectAsync(any(), eq(contentLength), any())).willReturn(future);
//        given(future.thenAccept(any())).willReturn(future);
//        given(future.exceptionally(any())).willReturn(future);
//        fileStorageService.saveNtFile(multipartFile);
//
//        verify(s3Service, times(1)).putObjectAsync(any(), eq(contentLength), any());
//        verify(modelMapper, times(1)).map(any(), eq(SavedFileDTO.class));
//    }
//
//    @Test
//    void saveNtFile_invalidAccount() {
//        Authentication authentication = new UsernamePasswordAuthenticationToken(null, "");
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
//        assertThrows(FileStorageException.class, () -> fileStorageService.saveNtFile(multipartFile));
//    }
//
//    @Test
//    void saveNtFile_throwIOExceptionReadingFromMultipart() throws IOException {
//        Account testAccount = Account.builder().id(999).username("test").build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(testAccount, "");
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
//        given(multipartFile.getInputStream()).willThrow(new IOException("Test broken IO"));
//
//        SavedFileEntity savedFile = SavedFileEntity.builder().build();
//        given(fileStorageDAO.save(any())).willReturn(savedFile);
//
//        assertThrows(FileStorageException.class, () -> fileStorageService.saveNtFile(multipartFile));
//    }
//
//    @Test
//    void getNtFile_fileIsStoreOnS3() throws IOException {
//        int fileId = 999;
//
//        Account testAccount = Account.builder().id(999).username("test").build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(testAccount, "");
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        ServletOutputStream servletOutputStreamMock = Mockito.mock(ServletOutputStream.class);
//        long contentLength = DataSize.ofMegabytes(10).toBytes();
//        SavedFileEntity savedFile = SavedFileEntity.builder()
//                .isUploaded(true)
//                .physicalName("test")
//                .build();
//
//        given(fileStorageDAO.findByIdAndAccountId(eq(fileId), anyInt())).willReturn(savedFile);
//        String key = String.format("%s/nt/%s", testAccount.getUsername(), savedFile.getPhysicalName());
//        ResponseInputStream mockResponseInputStream = Mockito.mock(ResponseInputStream.class);
//        GetObjectResponse getObjectResponse = Mockito.mock(GetObjectResponse.class);
//        given(s3Service.getObject(eq(key))).willReturn(mockResponseInputStream);
//        given(mockResponseInputStream.response()).willReturn(getObjectResponse);
//        given(getObjectResponse.contentLength()).willReturn(contentLength);
//        given(mockResponseInputStream.transferTo(eq(servletOutputStreamMock))).willReturn(contentLength);
//
//        fileStorageService.getNtFile(fileId, servletOutputStreamMock);
//
//        verify(s3Service, times(1)).getObject(any());
//        verify(modelMapper, times(1)).map(eq(savedFile), eq(SavedFileDTO.class));
//    }
//
//    @Test
//        // TODO This feature is not implement yet
//    void getNtFile_fileIsStoreLocally() {
//        int fileId = 999;
//
//        Account testAccount = Account.builder().id(999).username("test").build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(testAccount, "");
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        ServletOutputStream servletOutputStreamMock = Mockito.mock(ServletOutputStream.class);
//        SavedFileEntity savedFile = SavedFileEntity.builder()
//                .isUploaded(false)
//                .isBackup(true)
//                .physicalName("test")
//                .build();
//
//        given(fileStorageDAO.findByIdAndAccountId(eq(fileId), anyInt())).willReturn(savedFile);
//
//        fileStorageService.getNtFile(fileId, servletOutputStreamMock);
//
//        verify(s3Service, times(0)).getObject(any());
//        verify(modelMapper, times(1)).map(eq(savedFile), eq(SavedFileDTO.class));
//    }
//
//    @Test // TODO This feature is not implement yet
//    void getNtFile_fileIsNotStored() {
//        int fileId = 999;
//
//        Account testAccount = Account.builder().id(999).username("test").build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(testAccount, "");
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        ServletOutputStream servletOutputStreamMock = Mockito.mock(ServletOutputStream.class);
//        SavedFileEntity savedFile = SavedFileEntity.builder()
//                .isUploaded(false)
//                .isBackup(false)
//                .physicalName("test")
//                .build();
//
//        given(fileStorageDAO.findByIdAndAccountId(eq(fileId), anyInt())).willReturn(savedFile);
//        assertThrows(FileStorageException.class, () ->fileStorageService.getNtFile(fileId, servletOutputStreamMock));
//    }
//
//    @Test
//    void getNtFile_fileIsNotFullyTransferredToOutputStream() throws IOException {
//        int fileId = 999;
//
//        Account testAccount = Account.builder().id(999).username("test").build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(testAccount, "");
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        ServletOutputStream servletOutputStreamMock = Mockito.mock(ServletOutputStream.class);
//        long contentLength = DataSize.ofMegabytes(10).toBytes();
//        long diffContentLength = DataSize.ofMegabytes(8).toBytes();
//        SavedFileEntity savedFile = SavedFileEntity.builder()
//                .isUploaded(true)
//                .physicalName("test")
//                .build();
//
//        given(fileStorageDAO.findByIdAndAccountId(eq(fileId), anyInt())).willReturn(savedFile);
//        String key = String.format("%s/nt/%s", testAccount.getUsername(), savedFile.getPhysicalName());
//        ResponseInputStream mockResponseInputStream = Mockito.mock(ResponseInputStream.class);
//        GetObjectResponse getObjectResponse = Mockito.mock(GetObjectResponse.class);
//        given(s3Service.getObject(eq(key))).willReturn(mockResponseInputStream);
//        given(mockResponseInputStream.response()).willReturn(getObjectResponse);
//        given(getObjectResponse.contentLength()).willReturn(contentLength);
//        given(mockResponseInputStream.transferTo(eq(servletOutputStreamMock))).willReturn(diffContentLength);
//
//        assertThrows(FileStorageException.class, () ->fileStorageService.getNtFile(fileId, servletOutputStreamMock));
//    }
//
//    @Test
//    void getNtFile_IOExceptionWhenTransferToServletOutputStream() throws IOException {
//        int fileId = 999;
//
//        Account testAccount = Account.builder().id(999).username("test").build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(testAccount, "");
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        ServletOutputStream servletOutputStreamMock = Mockito.mock(ServletOutputStream.class);
//        SavedFileEntity savedFile = SavedFileEntity.builder()
//                .isUploaded(true)
//                .physicalName("test")
//                .build();
//
//        given(fileStorageDAO.findByIdAndAccountId(eq(fileId), anyInt())).willReturn(savedFile);
//        String key = String.format("%s/nt/%s", testAccount.getUsername(), savedFile.getPhysicalName());
//        ResponseInputStream mockResponseInputStream = Mockito.mock(ResponseInputStream.class);
//        given(s3Service.getObject(eq(key))).willReturn(mockResponseInputStream);
//        given(mockResponseInputStream.transferTo(eq(servletOutputStreamMock))).willThrow(new IOException());
//
//        verify(s3Service, times(0)).getObject(any());
//        assertThrows(FileStorageException.class, () ->fileStorageService.getNtFile(fileId, servletOutputStreamMock));
//    }
//
//    @Test
//    void getNtFile_invalidAccount() {
//        int fileId = 999;
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(null, "");
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        ServletOutputStream servletOutputStreamMock = Mockito.mock(ServletOutputStream.class);
//
//        assertThrows(FileStorageException.class, () ->fileStorageService.getNtFile(fileId, servletOutputStreamMock));
//    }
//
//    @Test
//    void getNtFile_SavedFileEntityNotFound() {
//        int fileId = 999;
//
//        Account testAccount = Account.builder().id(999).username("test").build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(testAccount, "");
//
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        ServletOutputStream servletOutputStreamMock = Mockito.mock(ServletOutputStream.class);
//
//        given(fileStorageDAO.findByIdAndAccountId(eq(fileId), anyInt())).willReturn(null);
//
//        assertThrows(FileStorageException.class, () ->fileStorageService.getNtFile(fileId, servletOutputStreamMock));
//    }
//
//    @Test
//    void deleteNtFile() {
//    }
//
//    @Test
//    void updateNtFile() {
//    }
//}