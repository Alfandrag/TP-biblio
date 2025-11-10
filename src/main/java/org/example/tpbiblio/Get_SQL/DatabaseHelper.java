package org.example.tpbiblio.Get_SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.tpbiblio.Get_SQL.DatabaseConnexion;

public class DatabaseHelper {
    public static int getUserId(String email, String password) {
        String sql = "SELECT id FROM Users WHERE email = ? AND pssword = ?";
        try (Connection conn = DatabaseConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
