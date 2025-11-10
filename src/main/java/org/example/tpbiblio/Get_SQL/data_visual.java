package org.example.tpbiblio.Get_SQL;

import org.example.tpbiblio.entity.Book_main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class data_visual {
    public List<Book_main> get_book_visual() {
        List<Book_main> books = new ArrayList<Book_main>();

        String querry = """
                SELECT
                    B.title AS Titre_Livre,
                    CONCAT(W.first_name, ' ', W.last_name) AS Auteur,
                    E.publisher AS Editeur,
                    E.edition_year AS Annee_Parution,
                    GROUP_CONCAT(BC.category SEPARATOR ', ') AS Genre,
                    E.available_copies AS Nombre_Copies
                FROM
                    Books AS B
                JOIN
                    Book_writer_link AS BWL ON B.id = BWL.book_id
                JOIN
                    Writers AS W ON BWL.writer_id = W.id
                JOIN
                    Editions AS E ON B.id = E.book_id
                LEFT JOIN
                    Books_cat_link AS BCL ON B.id = BCL.book_id
                LEFT JOIN
                    Book_Categories AS BC ON BCL.book_cat = BC.id
                GROUP BY
                    B.title, Auteur, E.publisher, E.edition_year, E.available_copies;
                """;
        try (Connection conn = DatabaseConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(querry)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book_main livre = new Book_main(
                            rs.getString("Titre_Livre"),
                            rs.getString("Auteur"),
                            rs.getString("Annee_Parution"),
                            rs.getString("genre"),
                            rs.getString("editeur"),
                            rs.getInt("nombre_copies")
                    );
                    books.add(livre);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

}
