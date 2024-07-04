package com.huy.backendnoithat.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AwsConfig {
    private final Region region = Region.AP_SOUTHEAST_2;
    @Bean
    public StsClient stsClient() {
        return StsClient.builder().region(region)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }


}
