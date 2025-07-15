package com.huy.backendnoithat.controller.v1.noithat;

import com.huy.backendnoithat.service.v0.thongTinNoiThat.BangNoiThatService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("V1BangNoiThatController")
@RequestMapping("/api/v1/bangnoithat")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BangNoiThatController {
    private final BangNoiThatService bangNoiThatService;

    @GetMapping("/create-default-data")
    public void createDefaultData(@RequestParam(value = "overwrite", required = false, defaultValue = "false") boolean overwrite) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        bangNoiThatService.sampleAll(token);
    }
}
