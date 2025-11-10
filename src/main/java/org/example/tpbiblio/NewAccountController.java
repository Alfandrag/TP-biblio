package org.example.tpbiblio;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.tpbiblio.Get_SQL.DatabaseConnexion;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewAccountController {
    @FXML
    private TextField first_name_input;
    @FXML
    private TextField last_name_input;
    @FXML
    private TextField email_input;
    @FXML
    private PasswordField password_input;
    @FXML
    private Label error;

    @FXML
    private void create_press() {
        String firstName = first_name_input.getText();
        String lastName = last_name_input.getText();
        String email = email_input.getText();
        String password = password_input.getText();

        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            error.setText("Veillez remplir tous les champs");
            return;
        }

        if (Update_table(firstName, lastName, email, password)) {
            open_hello_page();
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

    private boolean Update_table(String firstName, String lastName, String email, String hashed_password) {
        String sql = "INSERT INTO Users (first_name, last_name, email, password, cat) VALUES (?, ?, ?, ?, ?)";
        int defaut_cat = 1; // par défaut, est un étudiant

        try (Connection conn = DatabaseConnexion.getConnection();
             PreparedStatement ppstmt = conn.prepareStatement(sql)) {

            ppstmt.setString(1,firstName);
            ppstmt.setString(2,lastName);
            ppstmt.setString(3,email);
            ppstmt.setString(4,hashed_password);
            ppstmt.setInt(5,defaut_cat);
            ppstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void open_hello_page() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) first_name_input.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void return_press() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("connexion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) first_name_input.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







































