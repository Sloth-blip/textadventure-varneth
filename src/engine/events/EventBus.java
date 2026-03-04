package engine.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventBus {

    private final Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();

    public <E extends GameEvent> void subscribe(Class<E> type, Consumer<E> handler) {
        listeners.computeIfAbsent(type, k -> new ArrayList<>()).add(handler);
    }

    @SuppressWarnings("unchecked")
    public void publish(GameEvent event) {
        for (Consumer<?> raw : listeners.getOrDefault(event.getClass(), List.of())) {
            ((Consumer<GameEvent>) raw).accept(event);
        }
    }
}
