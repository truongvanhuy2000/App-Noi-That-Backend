package com.huy.backendnoithat.Service.AWS;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StsService {
    private final StsClient stsClient;
    @Value("${aws.s3.role-arn}")
    private String s3Role;
    @Value("${aws.sts.external-id}")
    private String externalId;
    public Credentials assumeRole() {
        String roleSessionName = UUID.randomUUID().toString();
        AssumeRoleRequest roleRequest = AssumeRoleRequest.builder()
                .externalId(externalId)
                .roleArn(s3Role)
                .roleSessionName(roleSessionName)
                .durationSeconds((int) TimeUnit.HOURS.toSeconds(6))
                .build();
        AssumeRoleResponse assumeRoleResponse = stsClient.assumeRole(roleRequest);
        return assumeRoleResponse.credentials();
    }
}
