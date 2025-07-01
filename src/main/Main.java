package main;

import com.sun.net.httpserver.HttpServer;
import main.service.*;
import main.util.ApiKeyFilter;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    private static final String API_KEY = "villa-booking-api-key-2024";

    public static void main(String[] args) throws Exception {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Villas - RoomTypes - Bookings - Reviews
        server.createContext("/villas", exchange -> {
            try {
                if (!ApiKeyFilter.check(exchange, API_KEY)) return;

                String[] segments = exchange.getRequestURI().getPath().split("/");

                if (segments.length >= 4) {
                    if ("rooms".equalsIgnoreCase(segments[3])) {
                        RoomTypeService.handle(exchange);
                        return;
                    } else if ("bookings".equalsIgnoreCase(segments[3])) {
                        BookingService.handle(exchange);
                        return;
                    } else if ("reviews".equalsIgnoreCase(segments[3])) {
                        ReviewService.handle(exchange);
                        return;
                    }
                }

                VillaService.handle(exchange);
            } catch (Exception e) {
                e.printStackTrace();
                sendServerError(exchange);
            }
        });

        // Customers - Bookings - Reviews
        server.createContext("/customers", exchange -> {
            try {
                if (!ApiKeyFilter.check(exchange, API_KEY)) return;

                String[] segments = exchange.getRequestURI().getPath().split("/");

                if (segments.length >= 4) {
                    if ("bookings".equalsIgnoreCase(segments[3])) {
                        // handle POST /customers/{cid}/bookings/{bid}/reviews
                        if (segments.length >= 6 && "reviews".equalsIgnoreCase(segments[5])) {
                            ReviewService.handle(exchange);
                            return;
                        }
                        BookingService.handle(exchange);
                        return;
                    } else if ("reviews".equalsIgnoreCase(segments[3])) {
                        ReviewService.handle(exchange);
                        return;
                    }
                }

                CustomerService.handle(exchange);
            } catch (Exception e) {
                e.printStackTrace();
                sendServerError(exchange);
            }
        });

        // Vouchers
        server.createContext("/vouchers", exchange -> {
            try {
                if (!ApiKeyFilter.check(exchange, API_KEY)) return;
                VoucherService.handle(exchange);
            } catch (Exception e) {
                e.printStackTrace();
                sendServerError(exchange);
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Villa Booking API is running at http://localhost:" + port);
        System.in.read(); // prevent exit
    }

    private static void sendServerError(com.sun.net.httpserver.HttpExchange exchange) {
        try {
            String error = "{\"error\":\"Internal server error\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, error.length());
            OutputStream os = exchange.getResponseBody();
            os.write(error.getBytes());
            os.close();
        } catch (Exception ignored) {}
    }
}