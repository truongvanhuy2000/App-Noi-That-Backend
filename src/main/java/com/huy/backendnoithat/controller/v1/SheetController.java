package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.model.PreSignedToken;
import com.huy.backendnoithat.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/sheet")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SheetController {
    private final SheetService sheetService;

    @GetMapping(value = "/share/{fileId}")
    public PreSignedToken shareSheetFile(@PathVariable(value = "fileId", required = false) Integer fileId) {
        return sheetService.shareSheetFile(fileId);
    }

}
