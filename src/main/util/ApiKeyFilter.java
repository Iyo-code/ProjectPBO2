package main.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class ApiKeyFilter {
    public static boolean check(HttpExchange exchange, String validKey) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("x-api-key");
        if (authHeader == null || !authHeader.equals(validKey)) {
            String response = "{\"error\":\"Unauthorized: Invalid API Key\"}";
            exchange.sendResponseHeaders(401, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return false;
        }
        return true;
    }
}

