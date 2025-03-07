package com.example.roomallocationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminPage {

    @FXML
    private Button AdminLoginButton;

    @FXML
    private Label errorMassage;

    @FXML
    private TextField managerName;

    @FXML
    private PasswordField managerPassword;

    String adminName="Tiny";
    int password=123;

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room Allocation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void onActionAdminLoginButton(ActionEvent event) {
        String pass= String.valueOf(password);

        if(managerName.getText().trim().equals(adminName) && managerPassword.getText().trim().equals(pass))
        {
            try {
                FXMLLoader root=new FXMLLoader(HelloController.class.getResource("set_pass_page.fxml"));
                Scene scene=new Scene(root.load());
                Stage stage=(Stage)managerName.getScene().getWindow();
                stage.setTitle("CR Page");
                stage.setScene(scene);
                stage.show();

            }catch (Exception ex){
                ex.printStackTrace();
                //showAlert("fill all field");
            }
        }
        else
        {
            showAlert("Invalid username or password");
        }
    }
    @FXML
    void adminBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("hello-view.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)managerName.getScene().getWindow();
            stage.setTitle("CR Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
