package com.huy.backendnoithat.Service;

import com.huy.backendnoithat.DTO.ApplicationInfoDTO;

public interface ApplicationInfoService {
    ApplicationInfoDTO findBy(int id);
    void save(ApplicationInfoDTO applicationInfoDTO);
}
