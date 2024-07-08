package com.huy.backendnoithat.event;

import com.huy.backendnoithat.model.dto.Event.NoiThatUpdate;
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

    public synchronized void publish(String username, NoiThatUpdate event) {
        if (listeners.containsKey(username)) {
            listeners.get(username).accept(event);
        }
    }
}
