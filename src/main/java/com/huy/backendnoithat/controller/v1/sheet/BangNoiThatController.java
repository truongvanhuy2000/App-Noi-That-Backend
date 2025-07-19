package com.huy.backendnoithat.controller.v1.sheet;

import com.huy.backendnoithat.service.v0.thongTinNoiThat.BangNoiThatService;
import com.huy.backendnoithat.service.v1.sheet.GenericSheetDataService;
import com.huy.backendnoithat.utils.SecurityUtils;
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
    private final GenericSheetDataService bangNoiThatService;

    @GetMapping("/create-default-data")
    public void createDefaultData(@RequestParam(value = "overwrite", required = false, defaultValue = "false") boolean overwrite) {
        long userId = SecurityUtils.getUserFromContext();
        bangNoiThatService.sampleAll(userId, overwrite);
    }
}
