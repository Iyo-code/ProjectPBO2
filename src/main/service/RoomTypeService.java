package main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import main.exception.NotFoundException;
import main.exception.ValidationException;
import main.model.RoomType;
import main.repository.RoomTypeRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class RoomTypeService {
    private static final RoomTypeRepository repo = new RoomTypeRepository();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String[] segments = exchange.getRequestURI().getPath().split("/");

            System.out.println("Request: " + method + " " + exchange.getRequestURI().getPath());

            // OPTIONS
            if ("OPTIONS".equalsIgnoreCase(method)) {
                sendJson(exchange, 200, new ResponseMessage("OK"));
                return;
            }

            // /villas/{villaId}/rooms
            if (segments.length == 4 && "rooms".equalsIgnoreCase(segments[3])) {
                int villaId = Integer.parseInt(segments[2]);

                switch (method) {
                    case "GET":
                        List<RoomType> rooms = repo.findByVillaId(villaId);
                        sendJson(exchange, 200, rooms);
                        return;

                    case "POST":
                        String postBody = readRequestBody(exchange);
                        RoomType newRoom = objectMapper.readValue(postBody, RoomType.class);
                        newRoom.setVilla(villaId);
                        validate(newRoom);
                        repo.save(newRoom);
                        sendJson(exchange, 201, new ResponseMessage("Room created"));
                        return;
                }
            }

            // /villas/{villaId}/rooms/{roomId}
            if (segments.length == 5 && "rooms".equalsIgnoreCase(segments[3])) {
                int roomId = Integer.parseInt(segments[4]);

                switch (method) {
                    case "PUT":
                        RoomType existing = repo.findById(roomId);
                        if (existing == null) throw new NotFoundException("Room not found");

                        String putBody = readRequestBody(exchange);
                        RoomType updated = objectMapper.readValue(putBody, RoomType.class);
                        updated.setVilla(existing.getVilla()); // keep villa ID
                        validate(updated);
                        repo.update(roomId, updated);
                        sendJson(exchange, 200, new ResponseMessage("Room updated"));
                        return;

                    case "DELETE":
                        if (repo.findById(roomId) == null)
                            throw new NotFoundException("Room not found");
                        repo.delete(roomId);
                        sendJson(exchange, 200, new ResponseMessage("Room deleted"));
                        return;
                }
            }

            sendJson(exchange, 405, new ErrorMessage("Method not allowed or incorrect URL"));

        } catch (ValidationException | NotFoundException e) {
            sendJson(exchange, e instanceof ValidationException ? 400 : 404, new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            sendJson(exchange, 500, new ErrorMessage("Internal server error"));
        }
    }

    private static void validate(RoomType room) {
        if (room.getName() == null || room.getName().isBlank())
            throw new ValidationException("Room name is required");
        if (room.getCapacity() <= 0)
            throw new ValidationException("Capacity must be greater than 0");
        if (room.getPrice() < 0)
            throw new ValidationException("Price must be >= 0");
        if (room.getBedSize() == null || room.getBedSize().isBlank())
            throw new ValidationException("Bed size is required");
    }

    private static String readRequestBody(HttpExchange exchange) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining());
    }

    private static void sendJson(HttpExchange exchange, int statusCode, Object data) {
        try {
            String json = new ObjectMapper().writeValueAsString(data);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, x-api-key");
            byte[] response = json.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(statusCode, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper response classes
    public static class ResponseMessage {
        public final String message;
        public ResponseMessage(String message) { this.message = message; }
    }

    public static class ErrorMessage {
        public final String error;
        public ErrorMessage(String error) { this.error = error; }
    }
}