package com.huy.backendnoithat.service.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.Event.NoiThatUpdate;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

@Deprecated
public interface BangNoiThatService {
    void sampleAll(String token);
}
