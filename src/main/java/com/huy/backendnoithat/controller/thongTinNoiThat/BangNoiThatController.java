package com.huy.backendnoithat.controller.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.Event.NoiThatUpdate;
import com.huy.backendnoithat.service.thongTinNoiThat.BangNoiThatService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
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
    public void sampleAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        bangNoiThatService.sampleAll(token);
    }

    @GetMapping(path = "/event/DBModification", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<NoiThatUpdate>> getDBModificationEvent(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = header.split(" ")[1].trim();
        return bangNoiThatService.getDBModificationEvent(token);
    }

}
