package main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import main.exception.NotFoundException;
import main.exception.ValidationException;
import main.model.Villa;
import main.repository.VillaRepository;
import main.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VillaService {
    private static final VillaRepository repo = new VillaRepository();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();
            String[] segments = path.split("/");

            if ("OPTIONS".equalsIgnoreCase(method)) {
                sendJson(exchange, 200, new ResponseMessage("Preflight OK"));
                return;
            }

            // GET /villas?ci_date=...&co_date=...
            if ("GET".equalsIgnoreCase(method) && query != null &&
                    query.contains("ci_date") && query.contains("co_date")) {
                String ci_date = null, co_date = null;
                for (String param : query.split("&")) {
                    String[] kv = param.split("=");
                    if (kv.length == 2) {
                        String key = kv[0];
                        String value = URLDecoder.decode(kv[1], "UTF-8");
                        if (key.equals("ci_date")) ci_date = value;
                        if (key.equals("co_date")) co_date = value;
                    }
                }

                if (ci_date == null || co_date == null) {
                    sendJson(exchange, 400, new ErrorMessage("ci_date and co_date are required"));
                    return;
                }

                List<Villa> available = repo.findAvailableVillas(ci_date, co_date);
                sendJson(exchange, 200, available);
                return;
            }

            if ("GET".equalsIgnoreCase(method)) {
                if (segments.length == 3 && isNumeric(segments[2])) {
                    int id = Integer.parseInt(segments[2]);
                    Villa villa = repo.findById(id);
                    if (villa == null) throw new NotFoundException("Villa not found");
                    sendJson(exchange, 200, villa);
                    return;
                } else if (segments.length == 2 || (segments.length == 3 && segments[2].isEmpty())) {
                    List<Villa> villas = repo.findAll();
                    sendJson(exchange, 200, villas);
                    return;
                }
            }

            if ("POST".equalsIgnoreCase(method) && segments.length == 2) {
                String body = readRequestBody(exchange);
                Villa villa = parseVillaFromBody(body);
                repo.save(villa);
                sendJson(exchange, 201, new ResponseMessage("Villa created successfully"));
                return;
            }

            if ("PUT".equalsIgnoreCase(method) && segments.length == 3 && isNumeric(segments[2])) {
                int id = Integer.parseInt(segments[2]);
                if (repo.findById(id) == null)
                    throw new NotFoundException("Villa not found");

                String body = readRequestBody(exchange);
                Villa villa = parseVillaFromBody(body);
                repo.update(id, villa);
                sendJson(exchange, 200, new ResponseMessage("Villa updated successfully"));
                return;
            }

            if ("DELETE".equalsIgnoreCase(method) && segments.length == 3 && isNumeric(segments[2])) {
                int id = Integer.parseInt(segments[2]);
                if (repo.findById(id) == null)
                    throw new NotFoundException("Villa not found");

                repo.delete(id);
                sendJson(exchange, 200, new ResponseMessage("Villa deleted successfully"));
                return;
            }

            sendJson(exchange, 405, new ErrorMessage("Method not allowed or incorrect URL format"));

        } catch (ValidationException | NotFoundException e) {
            int status = (e instanceof ValidationException) ? 400 : 404;
            sendJson(exchange, status, new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            sendJson(exchange, 500, new ErrorMessage("Internal server error"));
            e.printStackTrace();
        }
    }

    private static Villa parseVillaFromBody(String body) {
        try {
            Villa villa = objectMapper.readValue(body, Villa.class);

            if (!ValidationUtil.isNotEmpty(villa.getName()) ||
                    !ValidationUtil.isNotEmpty(villa.getDescription()) ||
                    !ValidationUtil.isNotEmpty(villa.getAddress())) {
                throw new ValidationException("All fields (name, description, address) are required.");
            }

            return villa;
        } catch (Exception e) {
            throw new ValidationException("Invalid request body: " + e.getMessage());
        }
    }

    private static String readRequestBody(HttpExchange exchange) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining());
    }

    private static void sendJson(HttpExchange exchange, int statusCode, Object bodyObject) {
        try {
            String json = objectMapper.writeValueAsString(bodyObject);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, x-api-key");

            byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(statusCode, responseBytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static class ResponseMessage {
        public final String message;
        public ResponseMessage(String message) {
            this.message = message;
        }
    }

    public static class ErrorMessage {
        public final String error;
        public ErrorMessage(String error) {
            this.error = error;
        }
    }
}