module com.example.hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.hellofx to javafx.fxml;
    exports com.example.hellofx;
}