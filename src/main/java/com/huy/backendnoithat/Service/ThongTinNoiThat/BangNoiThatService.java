package com.huy.backendnoithat.Service.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.Event.NoiThatUpdate;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface BangNoiThatService {
    void sampleAll(String token);
    Flux<ServerSentEvent<NoiThatUpdate>> getDBModificationEvent(String token);
}
