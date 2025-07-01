package main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import main.exception.NotFoundException;
import main.model.Booking;
import main.repository.BookingRepository;
import main.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private static final BookingRepository repo = new BookingRepository();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath(); // e.g. /customers/5/bookings
            String[] segments = path.split("/");

            // GET /customers/{id}/bookings
            if ("GET".equals(method) && segments.length == 4 && "bookings".equalsIgnoreCase(segments[3])) {
                int customerId = Integer.parseInt(segments[2]);
                List<Booking> bookings = repo.findByCustomerId(customerId);
                sendJson(exchange, 200, bookings);
                return;
            }

            // GET /villas/{id}/bookings
            if ("GET".equals(method) && segments.length == 4 && "bookings".equalsIgnoreCase(segments[3]) &&
                    "villas".equalsIgnoreCase(segments[1])) {
                int villaId = Integer.parseInt(segments[2]);
                List<Booking> bookings = repo.findByVillaId(villaId);
                sendJson(exchange, 200, bookings);
                return;
            }

            // POST /customers/{id}/bookings
            if ("POST".equals(method) && segments.length == 4 && "bookings".equalsIgnoreCase(segments[3])) {
                int customerId = Integer.parseInt(segments[2]);
                String body = readRequestBody(exchange);
                Booking booking = objectMapper.readValue(body, Booking.class);
                booking.setCustomer(customerId);

                validateBooking(booking);

                repo.save(booking);
                sendJson(exchange, 201, new ResponseMessage("Booking created"));
                return;
            }

            // Jika tidak cocok dengan apapun
            sendJson(exchange, 405, new ErrorMessage("Method not allowed or incorrect URL"));

        } catch (NotFoundException e) {
            sendJson(exchange, 404, new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            sendJson(exchange, 500, new ErrorMessage("Internal server error"));
        }
    }

    private static void validateBooking(Booking b) {
        if (b.getRoomType() <= 0 || !ValidationUtil.isNotEmpty(b.getCheckinDate()) ||
                !ValidationUtil.isNotEmpty(b.getCheckoutDate()) ||
                !ValidationUtil.isNotEmpty(b.getPaymentStatus())) {
            throw new IllegalArgumentException("All required fields must be filled");
        }
    }

    private static String readRequestBody(HttpExchange exchange) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining());
    }

    private static void sendJson(HttpExchange exchange, int statusCode, Object data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, json.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ResponseMessage {
        public final String message;
        public ResponseMessage(String message) { this.message = message; }
    }

    public static class ErrorMessage {
        public final String error;
        public ErrorMessage(String error) { this.error = error; }
    }
}