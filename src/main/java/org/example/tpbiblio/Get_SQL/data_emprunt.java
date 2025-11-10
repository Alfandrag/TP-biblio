package org.example.tpbiblio.Get_SQL;

import org.example.tpbiblio.Get_SQL.DatabaseConnexion;
import org.example.tpbiblio.entity.Book_loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class data_emprunt {

    public List<Book_loan> get_book_loan(int userID) {
        List<Book_loan> books = new ArrayList<Book_loan>();
        System.out.print(userID);
        if (userID != 0) {
            String querry = """
                
                    SELECT
                    b.title AS titre,
                    CONCAT(w.first_name, ' ', w.last_name) AS auteur,
                    e.publisher AS editeur,
                    l.loan_date AS date_emprunt,
                    COALESCE(l.return_date, DATE_ADD(l.loan_date, INTERVAL c.loan_time DAY)) AS date_retour,
                    c.loan_time AS duree_max_pret,
                    CASE
                        WHEN l.return_date IS NULL THEN 'Non rendu'
                        ELSE 'Rendu'
                    END AS etat,
                    u.email as email
                FROM Log_loans l
                JOIN Users u ON l.user_id = u.id
                JOIN Books b ON l.book_id = b.id
                LEFT JOIN Editions e ON e.book_id = b.id
                LEFT JOIN Book_writer_link bw ON bw.book_id = b.id
                LEFT JOIN Writers w ON bw.writer_id = w.id
                LEFT JOIN Categories c ON u.cat = c.id
                WHERE u.id = ?;
                """;
            System.out.print(querry);
            try (Connection conn = DatabaseConnexion.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(querry)) {

                stmt.setInt(1, userID);
                stmt.setInt(2, userID);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Book_loan livre = new Book_loan(
                                rs.getString("titre"),
                                rs.getString("auteur"),
                                rs.getString("date_emprunt"),
                                rs.getString("date_retour"),
                                rs.getString("etat"),
                                rs.getString("editeur"),
                                rs.getString("email")
                        );
                        books.add(livre);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String querry2 =
                    """
                SELECT
                    b.title AS titre,
                    CONCAT(w.first_name, ' ', w.last_name) AS auteur,
                    e.publisher AS editeur,
                    l.loan_date AS date_emprunt,
                    COALESCE(l.return_date, DATE_ADD(l.loan_date, INTERVAL c.loan_time DAY)) AS date_retour,
                    c.loan_time AS duree_max_pret,
                    CASE
                        WHEN l.return_date IS NULL THEN 'Non rendu'
                        ELSE 'Rendu'
                    END AS etat,
                    u.email as email
                FROM Log_loans l
                JOIN Users u ON l.user_id = u.id
                JOIN Books b ON l.book_id = b.id
                LEFT JOIN Editions e ON e.book_id = b.id
                LEFT JOIN Book_writer_link bw ON bw.book_id = b.id
                LEFT JOIN Writers w ON bw.writer_id = w.id
                LEFT JOIN Categories c ON u.cat = c.id;
                """;
            System.out.print(querry2);
                try (Connection conn = DatabaseConnexion.getConnection();
                 Statement stmt = conn.createStatement()) {

                    try (ResultSet rs = stmt.executeQuery(querry2)) {
                        while (rs.next()) {
                            Book_loan livre = new Book_loan(
                                    rs.getString("titre"),
                                    rs.getString("auteur"),
                                    rs.getString("date_emprunt"),
                                    rs.getString("date_retour"),
                                    rs.getString("etat"),
                                    rs.getString("editeur"),
                                    rs.getString("email")
                            );
                            books.add(livre);
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return books;
    }
}

















































