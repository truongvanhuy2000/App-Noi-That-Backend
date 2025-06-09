package com.huy.backendnoithat.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AwsConfig {
    @Value("${aws.s3.role-arn}")
    private String S3_ROLE_ARN;
    @Value("${aws.sts.external-id}")
    private String EXTERNAL_ID;
    @Value("${aws.s3.credential-duration}")
    private Integer CREDENTIAL_DURATION_HRS;
    private static final Region REGION = Region.AP_SOUTHEAST_2;

    @Bean(value = "stsAssumeS3RoleRequest")
    public AssumeRoleRequest stsAssumeS3RoleRequest() {
        String roleSessionName = UUID.randomUUID().toString();
        return AssumeRoleRequest.builder()
                .externalId(EXTERNAL_ID)
                .roleArn(S3_ROLE_ARN)
                .roleSessionName(roleSessionName)
                .durationSeconds((int) TimeUnit.HOURS.toSeconds(CREDENTIAL_DURATION_HRS))
                .build();
    }

    @Bean
    public StsClient stsClient() {
        return StsClient.builder().region(REGION)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }

    @Bean
    public StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider(
            StsClient stsClient, AssumeRoleRequest stsAssumeS3RoleRequest) {
        return StsAssumeRoleCredentialsProvider.builder()
                .stsClient(stsClient)
                .refreshRequest(stsAssumeS3RoleRequest)
                .asyncCredentialUpdateEnabled(true)
                .build();
    }

    @Bean
    public S3Client s3Client(StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider) {
        return S3Client.builder().region(REGION)
                .credentialsProvider(stsAssumeRoleCredentialsProvider)
                .build();
    }

    @Bean
    public S3AsyncClient s3AsyncClient(StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider) {
        return S3AsyncClient.builder()
                .region(REGION)
                .credentialsProvider(stsAssumeRoleCredentialsProvider)
                .multipartEnabled(true)
                .build();
    }

    @Bean
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        return threadPoolTaskExecutor;
    }

}
