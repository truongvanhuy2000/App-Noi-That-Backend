package com.huy.backendnoithat.controller.v0.thongTinNoiThat;

import com.huy.backendnoithat.service.v0.thongTinNoiThat.BangNoiThatService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@Deprecated
@RestController
@RequestMapping("/api/bangnoithat")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BangNoiThatController {
    private final BangNoiThatService bangNoiThatService;

    @GetMapping("/sampleAll")
    public void sampleAll() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        bangNoiThatService.sampleAll(token);
    }
}
