package com.huy.backendnoithat.Service;

import com.huy.backendnoithat.DAO.ApplicationInfoDAO;
import com.huy.backendnoithat.DTO.ApplicationInfoDTO;
import com.huy.backendnoithat.Entity.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationInfoServiceImpl implements ApplicationInfoService {
    @Autowired
    private ApplicationInfoDAO applicationInfoDAO;

    @Override
    public ApplicationInfoDTO findBy(int id) {
        Optional<ApplicationInfo> applicationInfo = applicationInfoDAO.findById(id);
        return applicationInfo.map(ApplicationInfoDTO::new).orElseThrow();
    }
    @Override
    public void save(ApplicationInfoDTO applicationInfoDTO) {
        applicationInfoDAO.save(new ApplicationInfo(applicationInfoDTO));
    }
}
