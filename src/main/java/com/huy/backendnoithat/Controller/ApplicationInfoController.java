package com.huy.backendnoithat.Controller;

import com.huy.backendnoithat.DTO.ApplicationInfoDTO;
import com.huy.backendnoithat.Service.ApplicationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applicationInfo")
public class ApplicationInfoController {
    @Autowired
    ApplicationInfoService applicationInfoService;
    @GetMapping("/{id}")
    public ApplicationInfoDTO getApplicationInfo(@PathVariable("id") int id) {
        return applicationInfoService.findBy(id);
    }
    @PostMapping("")
    public void saveApplicationInfo(ApplicationInfoDTO applicationInfoDTO) {
        applicationInfoService.save(applicationInfoDTO);
    }
}
