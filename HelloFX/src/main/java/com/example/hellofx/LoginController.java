package com.example.hellofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {

    @FXML
    private Label major_label;

    @FXML
    private Label name_label;

    @FXML
    private TextField nrp_field;

    private final
    ConnectionDB db = new ConnectionDB();
    private final
    Connection conn = db.connect_to_db("postgres", "postgres", "postgres");
    @FXML
    void search(ActionEvent event) throws SQLException {

        String connectQuery = "SELECT * FROM public.\"Student\" WHERE id='" + nrp_field.getText() + "';";
        Statement statement = conn.createStatement();
        ResultSet queryOutput = statement.executeQuery(connectQuery);
        if (queryOutput.next()) {
            name_label.setText(queryOutput.getString("name"));
            major_label.setText(queryOutput.getString("major"));
        }
        else {
            name_label.setText("INVALID");
            major_label.setText("INVALID");
        }
    }

}

