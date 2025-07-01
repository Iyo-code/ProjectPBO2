package main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import main.exception.NotFoundException;
import main.exception.ValidationException;
import main.model.Voucher;
import main.repository.VoucherRepository;
import main.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class VoucherService {
    private static final VoucherRepository repo = new VoucherRepository();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] segments = path.split("/");

            System.out.println("Method: " + method);
            System.out.println("Path: " + path);

            if ("OPTIONS".equalsIgnoreCase(method)) {
                sendJson(exchange, 200, new ResponseMessage("Preflight OK"));
                return;
            }

            if ("GET".equalsIgnoreCase(method)) {
                if (segments.length == 3 && isNumeric(segments[2])) {
                    int id = Integer.parseInt(segments[2]);
                    Voucher voucher = repo.findById(id);
                    if (voucher == null)
                        throw new NotFoundException("Voucher not found");
                    sendJson(exchange, 200, voucher);
                    return;
                } else {
                    List<Voucher> vouchers = repo.findAll();
                    sendJson(exchange, 200, vouchers);
                    return;
                }
            }

            if ("POST".equalsIgnoreCase(method) && segments.length == 2) {
                String body = readRequestBody(exchange);
                Voucher voucher = parseVoucherFromJson(body);
                repo.save(voucher);
                sendJson(exchange, 201, new ResponseMessage("Voucher created successfully"));
                return;
            }

            if ("PUT".equalsIgnoreCase(method) && segments.length == 3 && isNumeric(segments[2])) {
                int id = Integer.parseInt(segments[2]);
                if (repo.findById(id) == null)
                    throw new NotFoundException("Voucher not found");
                String body = readRequestBody(exchange);
                Voucher voucher = parseVoucherFromJson(body);
                repo.update(id, voucher);
                sendJson(exchange, 200, new ResponseMessage("Voucher updated successfully"));
                return;
            }

            if ("DELETE".equalsIgnoreCase(method) && segments.length == 3 && isNumeric(segments[2])) {
                int id = Integer.parseInt(segments[2]);
                if (repo.findById(id) == null)
                    throw new NotFoundException("Voucher not found");
                repo.delete(id);
                sendJson(exchange, 200, new ResponseMessage("Voucher deleted successfully"));
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

    private static Voucher parseVoucherFromJson(String body) {
        try {
            Voucher voucher = objectMapper.readValue(body, Voucher.class);

            if (!ValidationUtil.isNotEmpty(voucher.getCode()) ||
                    !ValidationUtil.isNotEmpty(voucher.getDescription()) ||
                    !ValidationUtil.isNotEmpty(voucher.getStartDate()) ||
                    !ValidationUtil.isNotEmpty(voucher.getEndDate()) ||
                    voucher.getDiscount() < 0) {
                throw new ValidationException("All fields are required and discount must be valid");
            }

            return voucher;
        } catch (Exception e) {
            throw new ValidationException("Invalid JSON format: " + e.getMessage());
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