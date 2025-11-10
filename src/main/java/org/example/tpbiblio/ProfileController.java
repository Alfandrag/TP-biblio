package org.example.tpbiblio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.tpbiblio.Get_SQL.data_emprunt;
import org.example.tpbiblio.entity.Book_loan;

import java.sql.*;
import java.util.List;

public class ProfileController {
    @FXML
    private Button home_button;
    @FXML
    private ChoiceBox selecteur;
    @FXML
    private TableView<Book_loan> Affichage_emprunt;
    @FXML
    private TableColumn<Book_loan,String> titleCol;
    @FXML
    private TableColumn<Book_loan, String> writerCol;
    @FXML
    private TableColumn<Book_loan, String> publisherCol;
    @FXML
    private TableColumn<Book_loan, String> loanDateCol;
    @FXML
    private TableColumn<Book_loan, String> returnDateCol;
    @FXML
    private TableColumn<Book_loan, String> statusCol;
    @FXML
    private TableColumn<Book_loan, String> loaner;

    private Connection connection;
    private int user_ID;
    private int user_cat;

    public void setUserID(int userID, int user_cat) {
        this.user_ID = userID;
        this.user_cat = user_cat;
        if (user_cat == 3) {
            loadProfile(0);
        }
        else {
            loadProfile(user_ID);
        }

    }

    @FXML
    private void home_press() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) home_button.getScene().getWindow();
            HelloController controller = fxmlLoader.getController();
            controller.setUserID(user_ID, user_cat);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProfile(int ID_user) {
        data_emprunt dao = new data_emprunt();
        List<Book_loan> book_loanList = dao.get_book_loan(ID_user);
        ObservableList<Book_loan> observableList = FXCollections.observableArrayList(book_loanList);

        // Lier les colonnes aux attributs de Book_loan
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        writerCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("editeur"));
        loanDateCol.setCellValueFactory(new PropertyValueFactory<>("date_emprunt"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("date_retour"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("etat"));
        loaner.setCellValueFactory(new PropertyValueFactory<>("loaner"));

        Affichage_emprunt.setItems(observableList);
    }

}



























































