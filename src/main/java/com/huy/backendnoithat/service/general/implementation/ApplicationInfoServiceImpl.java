package com.huy.backendnoithat.service.general.implementation;

import com.huy.backendnoithat.dao.ApplicationInfoDAO;
import com.huy.backendnoithat.entity.ApplicationInfoEntity;
import com.huy.backendnoithat.model.dto.ApplicationInfoDTO;
import com.huy.backendnoithat.service.general.ApplicationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationInfoServiceImpl implements ApplicationInfoService {
    @Autowired
    private ApplicationInfoDAO applicationInfoDAO;

    @Override
    public ApplicationInfoDTO findBy(int id) {
        Optional<ApplicationInfoEntity> applicationInfo = applicationInfoDAO.findById(id);
        return applicationInfo.map(ApplicationInfoDTO::new).orElseThrow();
    }

    @Override
    public void save(ApplicationInfoDTO applicationInfoDTO) {
        applicationInfoDAO.save(new ApplicationInfoEntity(applicationInfoDTO));
    }
}
