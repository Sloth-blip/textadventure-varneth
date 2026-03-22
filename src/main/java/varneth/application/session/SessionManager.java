package varneth.application.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    public GameSession createSession() {
        String id = UUID.randomUUID().toString();
        GameSession session = new GameSession();
        session.startNewGame();
        sessions.put(id, session);
        return session;
    }

    public GameSession getSession(String id) {
        return sessions.get(id);
    }

    public String createSessionId() {
        return UUID.randomUUID().toString();
    }   

    public GameSession getOrCreate(String id) {

        return sessions.computeIfAbsent(id, k -> {
            GameSession s = new GameSession();
            s.startNewGame();
            return s;
        });
}
}