package com.huy.backendnoithat.configuration;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {
    @Value("${drive.key.path}")
    private String DRIVE_KEY_PATH;


    @Bean
    public Drive googleDriveService() {
        File file = new File(DRIVE_KEY_PATH);
        try (InputStream inputStream = new FileInputStream(file)) {
            GoogleCredentials credentials = GoogleCredentials
                .fromStream(inputStream)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

            return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)).setApplicationName("MyDriveApp").build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Google Drive credentials", e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
