package com.example.roomallocationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;

public class ChangePassword {

    @FXML
    private Button backButton;

    @FXML
    private TextField changePassID;

    @FXML
    private TextField currentPass;

    @FXML
    private TextField newPass;

    @FXML
    private Button okButton;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("crLogin.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)newPass.getScene().getWindow();
            stage.setTitle("CR Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @FXML
    void okButtonOnAction(ActionEvent event) {
        String crId = changePassID.getText();  // Get CR ID
        String oldPass = currentPass.getText(); // Get old password
        String newPassword = newPass.getText(); // Get new password

        if (crId.isEmpty() || oldPass.isEmpty() || newPassword.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        String DATABASE_NAME = "project";
        String url = "jdbc:mysql://localhost:3306/" + DATABASE_NAME; // Update with your DB name
        String user = "root"; // Update with your DB username
        String password = "@Najma321"; // Update with your DB password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Check if the current password matches the database
            String checkQuery = "SELECT * FROM login WHERE cr_id = ? AND cr_pass = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, Integer.parseInt(crId));
            checkStmt.setString(2, oldPass);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                // Update password
                String updateQuery = "UPDATE login SET cr_pass = ? WHERE cr_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, newPassword);
                updateStmt.setInt(2, Integer.parseInt(crId));

                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert("Success", "Password updated successfully!");
                } else {
                    showAlert("Error", "Password update failed!");
                }
            } else {
                showAlert("Error", "Invalid current password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to update password!");
        }
    }

    // Helper function to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}