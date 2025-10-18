package com.huy.backendnoithat.usecase.application.info;

import com.huy.backendnoithat.model.dto.ApplicationInfoDTO;

public interface ApplicationInfoService {
    ApplicationInfoDTO findBy(int id);

    void save(ApplicationInfoDTO applicationInfoDTO);
}
