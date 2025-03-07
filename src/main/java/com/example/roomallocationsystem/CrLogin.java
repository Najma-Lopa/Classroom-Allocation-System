package com.example.roomallocationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CrLogin {

    @FXML
    private Button CRLoginButton;

    @FXML
    private TextField CRName;

    @FXML
    private Text changePasswordButton;

    @FXML
    private PasswordField CRPassword;

    @FXML
    private Label errorMassage;

    @FXML
    private Text registerButton;


    @FXML
    void registerButtonOnAction(MouseEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("registrationPage.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)registerButton.getScene().getWindow();
            stage.setTitle("Registration Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room Allocation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void onActionAdminLoginButton(ActionEvent event) {
        if (!CRName.getText().isBlank() && !CRPassword.getText().isBlank()) {
            validateLogin();
        } else {
            showAlert("Username or Password fields cannot be empty!");
        }
    }


    @FXML
    void CrBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("hello-view.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)CRName.getScene().getWindow();
            stage.setTitle("CR Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @FXML
    void changePasswordButtonOnAction(MouseEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("changePassword.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)CRName.getScene().getWindow();
            stage.setTitle("CR Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void validateLogin() {
//        System.out.println("HELLO");
        sql connectionNow = new sql();
        Connection connectionDB = connectionNow.getConnection();

        String verifyLogin = "select count(1) from login where cr_id ='"+CRName.getText()+"' and cr_pass='"+CRPassword.getText()+"'";

        try{
            Statement statement=connectionDB.createStatement();
            ResultSet queryResult=statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1)==1){
                    try {
                        setUserId(CRName.getText());
                        FXMLLoader root=new FXMLLoader(HelloController.class.getResource("allocation.fxml"));
                        Scene scene=new Scene(root.load());
                        Stage stage=(Stage)registerButton.getScene().getWindow();
                        stage.setTitle("Registration Page");
                        stage.setScene(scene);
                        stage.show();

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }else {
                    //System.out.println("Error");
                    showAlert("Enter valid pass and ID");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String currentUserId;

    public static void setUserId(String userId) {
        currentUserId = userId;
    }

    public static String getUserId() {
        return currentUserId;
    }

}
