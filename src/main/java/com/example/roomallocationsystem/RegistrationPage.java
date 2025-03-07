package com.example.roomallocationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;

public class RegistrationPage {

    @FXML
    private ImageView back_button;

    @FXML
    private Button submit_button;

    @FXML
    private TextField user_dept;

    @FXML
    private TextField user_id;

    @FXML
    private TextField user_faculty;

    @FXML
    private TextField user_level;

    @FXML
    private TextField user_name;

    @FXML
    private TextField user_semester;

    @FXML
    private TextField user_mail;

    @FXML
    private TextField user_phone;

    @FXML
    void back_button_on_action(MouseEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(HelloController.class.getResource("crLogin.fxml"));
            Scene scene = new Scene(root.load());
            Stage stage = (Stage) user_dept.getScene().getWindow();
            stage.setTitle("CR Page");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void submit_button_on_action(ActionEvent event) {
        if (validateInputs()) {
            dataEntry();
            try {
                FXMLLoader root = new FXMLLoader(HelloController.class.getResource("crLogin.fxml"));
                Scene scene = new Scene(root.load());
                Stage stage = (Stage) user_dept.getScene().getWindow();
                stage.setTitle("CR Page");
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean validateInputs() {
        String name = user_name.getText().trim();
        String id = user_id.getText().trim();
        String faculty = user_faculty.getText().trim();
        String department = user_dept.getText().trim();
        String level = user_level.getText().trim();
        String semester = user_semester.getText().trim();
        String phone = user_phone.getText().trim();
        String mail = user_mail.getText().trim();

        if (name.isEmpty() || id.isEmpty() || faculty.isEmpty() || department.isEmpty() ||
                level.isEmpty() || semester.isEmpty() || phone.isEmpty() || mail.isEmpty()) {

            showAlert("Error", "Must fill all data", Alert.AlertType.ERROR);
            return false;
        }

        if (!phone.matches("\\d{11}")) {
            showAlert("Error", "The phone number must be exactly 11 digits!", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void dataEntry() {
        sql connectNow = new sql();
        Connection connectionDB = connectNow.getConnection();

        String name = user_name.getText().trim();
        String id = user_id.getText().trim();
        String faculty = user_faculty.getText().trim();
        String department = user_dept.getText().trim();
        String level = user_level.getText().trim();
        String semester = user_semester.getText().trim();
        String phone = user_phone.getText().trim();
        String mail = user_mail.getText().trim();

        String insertFields = "INSERT INTO CR_info VALUES('";
        String insertValues = id + "','" + name + "','" + faculty + "','" + department + "','" + level + "','" + semester + "','" + phone + "','" + mail + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(insertToRegister);
            System.out.println("Data Inserted Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
