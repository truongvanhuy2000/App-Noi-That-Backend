package com.huy.backendnoithat.controller.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.Event.NoiThatUpdate;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.BangNoiThatService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import com.huy.backendnoithat.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

    @GetMapping(path = "/event/DBModification", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<NoiThatUpdate>> getDBModificationEvent() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return bangNoiThatService.getDBModificationEvent(token);
    }

}
