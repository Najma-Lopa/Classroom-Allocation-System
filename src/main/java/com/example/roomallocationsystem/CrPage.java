package com.example.roomallocationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CrPage {

    @FXML
    private Button CRLogin;

    @FXML
    private Button adminLogin;

    @FXML
    void CRLoginOnAction(ActionEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("crLogin.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)adminLogin.getScene().getWindow();
            stage.setTitle("CR Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @FXML
    void CRPageOnAction(ActionEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("hello-view.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)adminLogin.getScene().getWindow();
            stage.setTitle("CR Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void adminLoginOnAction(ActionEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("registrationPage.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)CRLogin.getScene().getWindow();
            stage.setTitle("Registration Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
