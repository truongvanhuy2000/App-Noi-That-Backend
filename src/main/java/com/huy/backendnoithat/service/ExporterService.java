package com.huy.backendnoithat.service;

import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExporterService {
    private final Map<String, Sinks.One<byte[]>> responseMap = new ConcurrentHashMap<>();
    private final MessagingService messagingService;
    private static final String EXPORTER_STREAM = "exporter";
    private static final String EXPORTER_RESULT_STREAM = "exporter_result";

    @PostConstruct
    public void init() {
        messagingService.consumeStream(EXPORTER_RESULT_STREAM)
            .subscribe(message -> {
                String correlationId = message.getKey();
                byte[] response = (byte[]) message.getValue();
                emitResponse(correlationId, response);
            });
    }

    public Mono<Resource> export(SheetDataExportDTO sheetDataExportDTO) {
        String correlationId = UUID.randomUUID().toString();
        Sinks.One<byte[]> sink = Sinks.one();
        responseMap.put(correlationId, sink);
        messagingService.publishMessage(EXPORTER_STREAM, new MessagingService.Message(correlationId, sheetDataExportDTO));
        return sink.asMono().map(ByteArrayResource::new);
    }

    public void emitResponse(String correlationId, byte[] response) {
        Sinks.One<byte[]> sink = responseMap.remove(correlationId);
        if (sink != null) {
            sink.tryEmitValue(response);
        }
    }
}
