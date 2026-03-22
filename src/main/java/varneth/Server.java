package varneth;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import varneth.application.intent.PlayerIntent;
import varneth.application.session.GameSession;
import varneth.application.session.SessionManager;

public class Server {

    private static final SessionManager SESSION_MANAGER = new SessionManager();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void start() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/state", Server::handleState);
        server.createContext("/api/intent", Server::handleIntent);

        server.start();

        System.out.println("HTTP Server gestartet auf Port 8080");
    }

    private static void handleState(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!requireMethod(exchange, "GET")) return;


        String sessionId = exchange.getRequestHeaders().getFirst("X-Session-Id");

        if (sessionId == null || sessionId.isBlank()) {
            sessionId = SESSION_MANAGER.createSessionId();
        }

        GameSession session = SESSION_MANAGER.getOrCreate(sessionId);

        var state = session.getCurrentState();

        byte[] json = MAPPER.writeValueAsBytes(state);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("X-Session-Id", sessionId);

        exchange.sendResponseHeaders(200, json.length);
        exchange.getResponseBody().write(json);
        exchange.getResponseBody().close();
    }

    private static void handleIntent(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!requireMethod(exchange, "POST")) return;

        String sessionId = exchange.getRequestHeaders().getFirst("X-Session-Id");

        if (sessionId == null || sessionId.isBlank()) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        GameSession session = SESSION_MANAGER.getSession(sessionId);

        if (session == null) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        PlayerIntent intent = MAPPER.readValue(exchange.getRequestBody(), PlayerIntent.class);

        var state = session.handleIntent(intent);

        byte[] json = MAPPER.writeValueAsBytes(state);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("X-Session-Id", sessionId);

        exchange.sendResponseHeaders(200, json.length);
        exchange.getResponseBody().write(json);
        exchange.getResponseBody().close();
    }

    private static boolean requireMethod(HttpExchange exchange, String method) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase(method)) {
            exchange.sendResponseHeaders(405, -1);
            return false;
        }
        return true;
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:5173");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, X-Session-Id");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Expose-Headers", "X-Session-Id");
    }

}