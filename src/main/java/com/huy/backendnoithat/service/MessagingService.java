package com.huy.backendnoithat.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MessagingService {
    private final ReactiveStringRedisTemplate reactiveRedisTemplate;

    public void publishMessage(String channel, Message message) {
        var record = StreamRecords.newRecord().ofObject(message)
            .withStreamKey(channel);
        reactiveRedisTemplate.opsForStream()
            .add(record)
            .subscribe();
    }

    public Flux<Message> consumeStream(String streamKey) {
        return reactiveRedisTemplate.opsForStream()
            .read(Message.class, StreamOffset.fromStart(streamKey))
            .flatMap(record -> {
                RecordId recordId = record.getId();
                Mono<Long> delete = reactiveRedisTemplate.opsForStream().delete(streamKey, recordId);
                return delete.thenReturn(record.getValue());
            });
    }

    @Data
    @AllArgsConstructor
    public static class Message {
        private String key;
        private Object value;
    }
}
