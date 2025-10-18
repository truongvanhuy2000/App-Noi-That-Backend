package com.huy.backendnoithat.controller.application.info;

import com.huy.backendnoithat.model.dto.ApplicationInfoDTO;
import com.huy.backendnoithat.usecase.application.info.ApplicationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicationInfo")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ApplicationInfoController {
    private final ApplicationInfoService applicationInfoService;

    @GetMapping("/{id}")
    public ApplicationInfoDTO getApplicationInfo(@PathVariable("id") int id) {
        return applicationInfoService.findBy(id);
    }

    @PostMapping("")
    public void saveApplicationInfo(ApplicationInfoDTO applicationInfoDTO) {
        applicationInfoService.save(applicationInfoDTO);
    }
}
