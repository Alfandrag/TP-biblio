module org.example.tpbiblio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;

    opens org.example.tpbiblio to javafx.fxml;
    exports org.example.tpbiblio;
    exports org.example.tpbiblio.Get_SQL;
    opens org.example.tpbiblio.Get_SQL to javafx.fxml;
}