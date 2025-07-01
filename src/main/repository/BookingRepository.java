package main.repository;

import main.model.Booking;
import main.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    public List<Booking> findAll() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                bookings.add(map(rs));
            }
        }
        return bookings;
    }

    public Booking findById(int id) throws SQLException {
        String query = "SELECT * FROM bookings WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return map(rs);
            }
            return null;
        }
    }

    public List<Booking> findByCustomerId(int customerId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE customer = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(map(rs));
            }
        }
        return bookings;
    }

    public List<Booking> findByVillaId(int villaId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query =
                "SELECT b.* FROM bookings b " +
                        "JOIN room_types rt ON b.room_type = rt.id " +
                        "WHERE rt.villa = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(map(rs));
            }
        }
        return bookings;
    }

    public void save(Booking booking) throws SQLException {
        String query =
                "INSERT INTO bookings (" +
                        "customer, room_type, checkin_date, checkout_date, price, " +
                        "voucher, final_price, payment_status, has_checkedin, has_checkedout) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, booking.getCustomer());
            stmt.setInt(2, booking.getRoomType());
            stmt.setString(3, booking.getCheckinDate());
            stmt.setString(4, booking.getCheckoutDate());
            stmt.setInt(5, booking.getPrice());

            if (booking.getVoucher() == null) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, booking.getVoucher());
            }

            stmt.setInt(7, booking.getFinalPrice());
            stmt.setString(8, booking.getPaymentStatus());
            stmt.setInt(9, booking.isHasCheckedIn() ? 1 : 0);
            stmt.setInt(10, booking.isHasCheckedOut() ? 1 : 0);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Booking map(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getInt("customer"),
                rs.getInt("room_type"),
                rs.getString("checkin_date"),
                rs.getString("checkout_date"),
                rs.getInt("price"),
                rs.getObject("voucher") != null ? rs.getInt("voucher") : null,
                rs.getInt("final_price"),
                rs.getString("payment_status"),
                rs.getInt("has_checkedin") == 1,
                rs.getInt("has_checkedout") == 1
        );
    }
}