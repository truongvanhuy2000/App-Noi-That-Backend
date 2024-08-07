package com.huy.backendnoithat.service.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.Event.NoiThatUpdate;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface BangNoiThatService {
    void sampleAll(String token);

    Flux<ServerSentEvent<NoiThatUpdate>> getDBModificationEvent(String token);
}
