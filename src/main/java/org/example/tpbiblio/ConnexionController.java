package org.example.tpbiblio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.tpbiblio.Get_SQL.DatabaseConnexion;
import org.example.tpbiblio.Get_SQL.DatabaseHelper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ConnexionController {
    @FXML
    private TextField username_input;

    @FXML
    private PasswordField password_input;

    @FXML
    private Label error;

    @FXML
    protected void try_login(ActionEvent event) {
        String email = username_input.getText();
        String password = password_input.getText();
        int userID = DatabaseHelper.getUserId(email, hashing(password));

        if (email.isEmpty() || password.isEmpty()) {
            error.setText("Veillez remplir tous les champs");
            return;
        }

        // on hash le mot de passe en sha-256

        String hashed_password = hashing(password);

        if (checkLogin(email, hashed_password)) {
            open_hello_page(userID);
        } else {
            error.setText("Identifiants incorrects");
        }
    }

    private String hashing(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed_bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed_bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkLogin(String email, String hashed_password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND pssword = ?";
        try (Connection conn = DatabaseConnexion.getConnection();
             PreparedStatement ppstmt = conn.prepareStatement(sql)) {

            ppstmt.setString(1,email);
            ppstmt.setString(2,hashed_password);
            ResultSet rs = ppstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void open_hello_page(int userID) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            HelloController controller = fxmlLoader.getController();
            controller.setUserID(userID);
            Stage stage = (Stage) username_input.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void create_press() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new_account.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) username_input.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Create Account");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
