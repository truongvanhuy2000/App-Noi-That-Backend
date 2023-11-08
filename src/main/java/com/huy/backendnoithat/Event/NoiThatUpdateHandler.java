package com.huy.backendnoithat.Event;

import com.huy.backendnoithat.DTO.Event.NoiThatUpdate;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.function.Consumer;
@Component
public class NoiThatUpdateHandler {
//    private final List<Consumer<NoiThatUpdate>> listeners = new CopyOnWriteArrayList<>();
    private final HashMap<String, Consumer<NoiThatUpdate>> listeners = new HashMap<>();
    public synchronized void register(String username, Consumer<NoiThatUpdate> listener) {
        listeners.put(username, listener);
    }
    @AfterReturning()
    public synchronized void publish(String username, NoiThatUpdate event) {
        listeners.get(username).accept(event);
    }
}
