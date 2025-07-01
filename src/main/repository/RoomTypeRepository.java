package main.repository;

import main.model.RoomType;
import main.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeRepository {

    public List<RoomType> findByVillaId(int villaId) throws SQLException {
        List<RoomType> list = new ArrayList<>();
        String query = "SELECT * FROM room_types WHERE villa = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public RoomType findById(int id) throws SQLException {
        String query = "SELECT * FROM room_types WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return map(rs);
            }
        }

        return null;
    }

    public void save(RoomType room) throws SQLException {
        String query = "INSERT INTO room_types (villa, name, quantity, capacity, price, bed_size, has_desk, has_ac, has_tv, has_wifi, has_shower, has_hotwater, has_fridge) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, room.getVilla());
            stmt.setString(2, room.getName());
            stmt.setInt(3, room.getQuantity());
            stmt.setInt(4, room.getCapacity());
            stmt.setInt(5, room.getPrice());
            stmt.setString(6, room.getBedSize());
            stmt.setBoolean(7, room.isHasDesk());
            stmt.setBoolean(8, room.isHasAc());
            stmt.setBoolean(9, room.isHasTv());
            stmt.setBoolean(10, room.isHasWifi());
            stmt.setBoolean(11, room.isHasShower());
            stmt.setBoolean(12, room.isHasHotwater());
            stmt.setBoolean(13, room.isHasFridge());

            stmt.executeUpdate();
        }
    }

    public void update(int id, RoomType room) throws SQLException {
        String query = "UPDATE room_types SET villa = ?, name = ?, quantity = ?, capacity = ?, price = ?, bed_size = ?, has_desk = ?, has_ac = ?, has_tv = ?, has_wifi = ?, has_shower = ?, has_hotwater = ?, has_fridge = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, room.getVilla());
            stmt.setString(2, room.getName());
            stmt.setInt(3, room.getQuantity());
            stmt.setInt(4, room.getCapacity());
            stmt.setInt(5, room.getPrice());
            stmt.setString(6, room.getBedSize());
            stmt.setBoolean(7, room.isHasDesk());
            stmt.setBoolean(8, room.isHasAc());
            stmt.setBoolean(9, room.isHasTv());
            stmt.setBoolean(10, room.isHasWifi());
            stmt.setBoolean(11, room.isHasShower());
            stmt.setBoolean(12, room.isHasHotwater());
            stmt.setBoolean(13, room.isHasFridge());
            stmt.setInt(14, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM room_types WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private RoomType map(ResultSet rs) throws SQLException {
        return new RoomType(
                rs.getInt("id"),
                rs.getInt("villa"),
                rs.getString("name"),
                rs.getInt("quantity"),
                rs.getInt("capacity"),
                rs.getInt("price"),
                rs.getString("bed_size"),
                rs.getBoolean("has_desk"),
                rs.getBoolean("has_ac"),
                rs.getBoolean("has_tv"),
                rs.getBoolean("has_wifi"),
                rs.getBoolean("has_shower"),
                rs.getBoolean("has_hotwater"),
                rs.getBoolean("has_fridge")
        );
    }
}
