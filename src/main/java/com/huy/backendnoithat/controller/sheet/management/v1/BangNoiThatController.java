package com.huy.backendnoithat.controller.sheet.management.v1;

import com.huy.backendnoithat.usecase.sheet.management.v1.GenericSheetDataService;
import com.huy.backendnoithat.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
