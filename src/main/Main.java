package main;

import com.sun.net.httpserver.HttpServer;
import main.util.ApiKeyFilter;
import main.service.VillaService;
import main.service.CustomerService;
import main.service.VoucherService;

import java.net.InetSocketAddress;

public class Main {
    private static final String API_KEY = "villa-booking-api-key-2024";

    public static void main(String[] args) throws Exception {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Villa
        server.createContext("/villas", exchange -> {
            if (!ApiKeyFilter.check(exchange, API_KEY))
                return;

            VillaService.handle(exchange);
        });

        // Customer
        server.createContext("/customers", exchange -> {
            if (!ApiKeyFilter.check(exchange, API_KEY))
                return;

            CustomerService.handle(exchange);
        });

        // Voucher
        server.createContext("/vouchers", exchange -> {
            if (!ApiKeyFilter.check(exchange, API_KEY))
                return;

            VoucherService.handle(exchange);
        });

        server.setExecutor(null); // default
        server.start();

        System.out.println("Villa Booking API is running at http://localhost:" + port);
    }
}
