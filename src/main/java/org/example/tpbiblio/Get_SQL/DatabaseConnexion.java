package org.example.tpbiblio.Get_SQL;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnexion {
    private static final String URL = "jdbc:mariadb://localhost:3306/Bibliotheque";
    private static final String USER = "Appli_biblio";
    private static final String PASSWORD = "bestappli";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
