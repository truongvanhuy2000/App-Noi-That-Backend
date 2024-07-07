package com.huy.backendnoithat.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sts.StsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public S3Client s3Client(StsClient stsClient,
                             @Qualifier("stsAssumeS3RoleRequest") AssumeRoleRequest assumeRoleRequest) {
        return S3Client.builder().region(REGION)
                .credentialsProvider(StsAssumeRoleCredentialsProvider.builder()
                        .stsClient(stsClient)
                        .refreshRequest(assumeRoleRequest)
                        .asyncCredentialUpdateEnabled(true)
                        .build())
                .build();
    }


}
