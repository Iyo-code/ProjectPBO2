package main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import main.exception.NotFoundException;
import main.exception.ValidationException;
import main.model.Review;
import main.repository.ReviewRepository;
import main.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewService {
    private static final ReviewRepository repo = new ReviewRepository();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] segments = path.split("/");

            // OPTIONS handler (for CORS preflight)
            if ("OPTIONS".equalsIgnoreCase(method)) {
                sendJson(exchange, 200, new ResponseMessage("OK"));
                return;
            }

            // GET /villas/{id}/reviews
            if ("GET".equalsIgnoreCase(method) && segments.length == 4 &&
                    "villas".equals(segments[1]) && "reviews".equals(segments[3])) {
                int villaId = Integer.parseInt(segments[2]);
                List<Review> reviews = repo.findByVillaId(villaId);
                sendJson(exchange, 200, reviews);
                return;
            }

            // GET /customers/{id}/reviews
            if ("GET".equalsIgnoreCase(method) && segments.length == 4 &&
                    "customers".equals(segments[1]) && "reviews".equals(segments[3])) {
                int customerId = Integer.parseInt(segments[2]);
                List<Review> reviews = repo.findByCustomerId(customerId);
                sendJson(exchange, 200, reviews);
                return;
            }

            // POST /customers/{cid}/bookings/{bid}/reviews
            if ("POST".equalsIgnoreCase(method) && segments.length == 6 &&
                    "customers".equals(segments[1]) &&
                    "bookings".equals(segments[3]) &&
                    "reviews".equals(segments[5])) {

                int bookingId = Integer.parseInt(segments[4]);
                String body = readRequestBody(exchange);
                Review review = parseReviewFromBody(body);
                review.setBooking(bookingId);

                if (repo.findByBookingId(bookingId) != null) {
                    throw new ValidationException("Review already exists for this booking");
                }

                repo.save(review);
                sendJson(exchange, 201, new ResponseMessage("Review created"));
                return;
            }

            sendJson(exchange, 405, new ErrorMessage("Method not allowed or invalid URL"));

        } catch (ValidationException | NotFoundException e) {
            int status = (e instanceof ValidationException) ? 400 : 404;
            sendJson(exchange, status, new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            sendJson(exchange, 500, new ErrorMessage("Internal server error"));
            e.printStackTrace();
        }
    }

    private static Review parseReviewFromBody(String body) {
        try {
            Review r = objectMapper.readValue(body, Review.class);
            if (r.getStar() < 1 || r.getStar() > 5 || !ValidationUtil.isNotEmpty(r.getTitle()) || !ValidationUtil.isNotEmpty(r.getContent())) {
                throw new ValidationException("All fields (star, title, content) are required and star must be 1-5");
            }
            return r;
        } catch (Exception e) {
            throw new ValidationException("Invalid review JSON: " + e.getMessage());
        }
    }

    private static String readRequestBody(HttpExchange exchange) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining());
    }

    private static void sendJson(HttpExchange exchange, int statusCode, Object response) {
        try {
            String json = objectMapper.writeValueAsString(response);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
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

    // Response wrapper classes
    public static class ResponseMessage {
        public final String message;
        public ResponseMessage(String message) { this.message = message; }
    }

    public static class ErrorMessage {
        public final String error;
        public ErrorMessage(String error) { this.error = error; }
    }
}