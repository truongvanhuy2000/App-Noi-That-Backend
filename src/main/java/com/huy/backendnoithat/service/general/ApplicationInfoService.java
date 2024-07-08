package com.huy.backendnoithat.service.general;

import com.huy.backendnoithat.model.dto.ApplicationInfoDTO;

public interface ApplicationInfoService {
    ApplicationInfoDTO findBy(int id);

    void save(ApplicationInfoDTO applicationInfoDTO);
}
