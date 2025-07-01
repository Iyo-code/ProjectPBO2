package main.repository;

import main.model.Review;
import main.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    public List<Review> findAll() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM reviews";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                reviews.add(map(rs));
            }
        }

        return reviews;
    }

    public Review findByBookingId(int bookingId) throws SQLException {
        String query = "SELECT * FROM reviews WHERE booking = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return map(rs);
            }
            return null;
        }
    }

    public List<Review> findByCustomerId(int customerId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query =
                "SELECT r.* FROM reviews r " +
                        "JOIN bookings b ON r.booking = b.id " +
                        "WHERE b.customer = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(map(rs));
            }
        }

        return reviews;
    }

    public List<Review> findByVillaId(int villaId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query =
                "SELECT r.* FROM reviews r " +
                        "JOIN bookings b ON r.booking = b.id " +
                        "JOIN room_types rt ON b.room_type = rt.id " +
                        "WHERE rt.villa = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(map(rs));
            }
        }

        return reviews;
    }

    public void save(Review review) throws SQLException {
        String query = "INSERT INTO reviews (booking, star, title, content) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, review.getBooking());
            stmt.setInt(2, review.getStar());
            stmt.setString(3, review.getTitle());
            stmt.setString(4, review.getContent());

            stmt.executeUpdate();
        }
    }

    private Review map(ResultSet rs) throws SQLException {
        return new Review(
                rs.getInt("booking"),
                rs.getInt("star"),
                rs.getString("title"),
                rs.getString("content")
        );
    }
}
