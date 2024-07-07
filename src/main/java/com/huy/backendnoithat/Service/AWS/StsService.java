package com.huy.backendnoithat.Service.AWS;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StsService {
    private final StsClient stsClient;
    private final AssumeRoleRequest stsAssumeS3RoleRequest;
    public Credentials assumeS3AccessRole() {
        AssumeRoleResponse assumeRoleResponse = stsClient.assumeRole(stsAssumeS3RoleRequest);
        return assumeRoleResponse.credentials();
    }
}
