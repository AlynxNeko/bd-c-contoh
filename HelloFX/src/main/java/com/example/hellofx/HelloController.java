package com.example.hellofx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Objects;

public class HelloController {

    @FXML
    private Label errorLabel;
    @FXML
    private Button addButton;

    @FXML
    private Tab admintab;

    @FXML
    private Button deleteButton;

    @FXML
    private ChoiceBox<String> majorField;

    @FXML
    private TableColumn<Student, String> mhs_id;

    @FXML
    private TableColumn<Student, String> mhs_major;

    @FXML
    private TableColumn<Student, String> mhs_name;
    ObservableList<Student> listStudent = FXCollections.observableArrayList();

    @FXML
    private Tab mhstab;

    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;

    @FXML
    private TableView<Student> table;

    @FXML
    private Tab tabtab;

    int indexSelected = 0;

    @FXML
    void gotolink(ActionEvent event) throws URISyntaxException, IOException {
        Desktop desk = Desktop.getDesktop();
        desk.browse(new URI("https://www.youtube.com/watch?v=Qu84tcGExSQ"));
    }

    @FXML
    void getIndex(MouseEvent event) {
        indexSelected = table.getSelectionModel().getSelectedIndex();
    }

    @FXML
    void deletee(ActionEvent event) throws SQLException {
        String idd = table.getSelectionModel().getSelectedItem().getId();
        String connectQuery = "DELETE FROM public.\"Student\" WHERE id = '" + idd + "';";
        Statement statement = conn.createStatement();
        statement.executeUpdate(connectQuery);
        updateTable();
    }

    private final
    ConnectionDB db = new ConnectionDB();
    private final
    Connection conn = db.connect_to_db("postgres", "postgres", "postgres");

    @FXML
    public void initialize() throws SQLException {

        mhs_id.setCellValueFactory(new PropertyValueFactory<>("id")); //nama di db
        mhs_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        mhs_major.setCellValueFactory(new PropertyValueFactory<>("major"));
        table.setItems(listStudent);

        majorField.getItems().clear();
        majorField.getItems().addAll("Informatika", "SIB", "DSA");

        updateTable();

    }

    private void updateTable() throws SQLException {
        listStudent.clear();
        String connectQuery = "SELECT * FROM public.\"Student\";";
        Statement statement = conn.createStatement();
        ResultSet queryOutput = statement.executeQuery(connectQuery);
        while (queryOutput.next()){
            listStudent.add(new Student(
                    queryOutput.getString("id"),
                    queryOutput.getString("name"),
                    queryOutput.getString("major")
            ));
        }
        table.setItems(listStudent);
    }

    private boolean checkIsi(){
        return Objects.equals(nameField.getText(), "")
                || Objects.equals(majorField.getValue(), null)
                || Objects.equals(idField.getText(), "");
    }
    @FXML
    void addd(ActionEvent event) {
        if (checkIsi()){
            errorLabel.setText("Please fill all the data!");
        } else {
            try {
                String id = idField.getText();
                String name = nameField.getText();
                String major = majorField.getValue();

                //insert to db
                String connectQuery = String.format("""
                        INSERT INTO public."Student"(
                        	id, name, major)
                        	VALUES ('%s', '%s', '%s');
                        	""", id, name, major);
                Statement statement = conn.createStatement();
                statement.executeUpdate(connectQuery);

                updateTable();
            } catch (SQLException e) {
                errorLabel.setText("Something wrong with the table");
            } catch (RuntimeException e){
                errorLabel.setText("Please insert the correct time format");
            }
        }
    }


}


