package org.example.tpbiblio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;

    private int userID;

    public void setUserID(int userID) {
        this.userID = userID;
    }


    @FXML
    protected void profile_press(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            ProfileController controller = fxmlLoader.getController();
            controller.setUserID(userID);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
