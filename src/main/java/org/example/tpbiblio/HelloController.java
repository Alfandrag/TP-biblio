package org.example.tpbiblio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.tpbiblio.Get_SQL.data_emprunt;
import org.example.tpbiblio.Get_SQL.data_visual;
import org.example.tpbiblio.entity.Book_loan;
import org.example.tpbiblio.entity.Book_main;

import java.util.List;

public class HelloController {
    @FXML
    private TableView<Book_main> bookTable;

    @FXML
    private TableColumn<Book_main, String> titreColumn;

    @FXML
    private TableColumn<Book_main, String> auteurColumn;

    @FXML
    private TableColumn<Book_main, String> editeurColumn;

    @FXML
    private TableColumn<Book_main, String> anneeColumn;

    @FXML
    private TableColumn<Book_main, String> genresColumn;

    @FXML
    private TableColumn<Book_main, Integer> copiesColumn;

    private int userID;
    private int user_cat;

    public void setUserID(int userID, int user_cat) {
        this.userID = userID;
        this.user_cat = user_cat;
        load_main();
    }


    @FXML
    protected void profile_press(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            ProfileController controller = fxmlLoader.getController();
            controller.setUserID(userID, user_cat);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setTitle("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load_main() {
        data_visual dao = new data_visual();
        List<Book_main> book_List = dao.get_book_visual();
        ObservableList<Book_main> observableList = FXCollections.observableArrayList(book_List);


        titreColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        auteurColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        editeurColumn.setCellValueFactory(new PropertyValueFactory<>("editeur"));
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("Annee_Parution"));
        genresColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_copies")); //.asObject()


        bookTable.setItems(observableList);
    }
}
