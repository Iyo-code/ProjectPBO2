package main.repository;

import main.model.Villa;
import main.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VillaRepository {

    public List<Villa> findAll() throws SQLException {
        List<Villa> villas = new ArrayList<>();
        String query = "SELECT * FROM villas";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                villas.add(map(rs));
            }
        }
        return villas;
    }

    public Villa findById(int id) throws SQLException {
        String query = "SELECT * FROM villas WHERE id = ?";
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

    public void save(Villa villa) throws SQLException {
        String query = "INSERT INTO villas (name, description, address) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, villa.getName());
            stmt.setString(2, villa.getDescription());
            stmt.setString(3, villa.getAddress());
            stmt.executeUpdate();
        }
    }

    public void update(int id, Villa villa) throws SQLException {
        String query = "UPDATE villas SET name = ?, description = ?, address = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, villa.getName());
            stmt.setString(2, villa.getDescription());
            stmt.setString(3, villa.getAddress());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM villas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Villa> findAvailableVillas(String checkin, String checkout) throws SQLException {
        List<Villa> villas = new ArrayList<>();
        String query =
                "SELECT DISTINCT v.* " +
                        "FROM villas v " +
                        "JOIN room_types rt ON rt.villa = v.id " +
                        "LEFT JOIN bookings b ON b.room_type = rt.id " +
                        "    AND NOT (b.checkout_date <= ? OR b.checkin_date >= ?) " +
                        "GROUP BY rt.id " +
                        "HAVING rt.quantity > COUNT(b.id)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, checkin);
            stmt.setString(2, checkout);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                villas.add(map(rs));
            }
        }

        return villas;
    }

    private Villa map(ResultSet rs) throws SQLException {
        return new Villa(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("address")
        );
    }
}