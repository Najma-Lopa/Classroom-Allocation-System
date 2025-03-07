package com.example.roomallocationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

public class SetPassPage {

    @FXML
    private ImageView back_button;

    @FXML
    private TableView<CrInfo> cr_register_list;

    @FXML
    private TableColumn<CrInfo, Integer> col_id;

    @FXML
    private TableColumn<CrInfo, String> col_name;

    @FXML
    private TableColumn<CrInfo, String> col_faculty;

    @FXML
    private TableColumn<CrInfo, String> col_mail;

    @FXML
    private TableColumn<CrInfo, String> col_phn;

    @FXML
    private TableColumn<CrInfo, String> col_dept;

    @FXML
    private TableColumn<CrInfo, String> col_lev;

    @FXML
    private TableColumn<CrInfo, String> col_semester;

    @FXML
    private Button create_account_button;

    @FXML
    private TextField user_pass;

    @FXML
    private TextField user_id;

    private ObservableList<CrInfo> crList = FXCollections.observableArrayList();

    public void initialize() {
        loadData();
        setupRowClickListener();
    }

    @FXML
    void back_button_on_action(MouseEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(HelloController.class.getResource("adminPage.fxml"));
            Scene scene = new Scene(root.load());
            Stage stage = (Stage) back_button.getScene().getWindow();
            stage.setTitle("Admin Page");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void create_accont_button_on_action(ActionEvent event) {
        dataEntry();
        user_id.clear();
        user_pass.clear();
    }

    private void loadData() {
        String query = "SELECT cr_id, cr_name, cr_faculty, cr_dept, cr_level, cr_semester, cr_phn, cr_mail FROM CR_info";

        try (Connection conn = sql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                crList.add(new CrInfo(
                        rs.getInt("cr_id"),
                        rs.getString("cr_name"),
                        rs.getString("cr_faculty"),
                        rs.getString("cr_dept"),
                        rs.getString("cr_level"),
                        rs.getString("cr_semester"),
                        rs.getString("cr_phn"),
                        rs.getString("cr_mail")
                ));
            }

            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
            col_dept.setCellValueFactory(new PropertyValueFactory<>("dept"));
            col_lev.setCellValueFactory(new PropertyValueFactory<>("level"));
            col_semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
            col_phn.setCellValueFactory(new PropertyValueFactory<>("phn"));
            col_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));

            cr_register_list.setItems(crList);
            System.out.println("Data Loaded Successfully!");

        } catch (SQLException e) {
            System.err.println("Error Loading Data!");
            e.printStackTrace();
        }
    }

    private void setupRowClickListener() {
        cr_register_list.setOnMouseClicked(event -> {
            CrInfo selectedStudent = cr_register_list.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                user_id.setText(String.valueOf(selectedStudent.getId()));
                checkPasswordSet(selectedStudent.getId()); // ✅ Check if password is already set
            }
        });
    }

    private void checkPasswordSet(int crId) {
        String query = "SELECT COUNT(*) FROM login WHERE cr_id = ?";

        try (Connection conn = sql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, crId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Password already set! Modification disabled.");
                disablePasswordModification();  // ✅ Disable if password exists
            } else {
                enablePasswordModification();  // ✅ Enable if no password exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void disablePasswordModification() {
        user_pass.setDisable(true);
        create_account_button.setDisable(true);
    }

    private void enablePasswordModification() {
        user_pass.setDisable(false);
        create_account_button.setDisable(false);
    }

    public void dataEntry() {
        sql connectNow = new sql();
        Connection connectionDB = connectNow.getConnection();

        String id = user_id.getText();
        String pass = user_pass.getText();

        if (id.isEmpty() || pass.isEmpty()) {
            System.out.println("User ID or Password cannot be empty!");
            return;
        }

        String checkQuery = "SELECT COUNT(*) FROM login WHERE cr_id = ?";
        String insertQuery = "INSERT INTO login (cr_id, cr_pass) VALUES (?, ?)";

        try (PreparedStatement checkStmt = connectionDB.prepareStatement(checkQuery)) {
            checkStmt.setString(1, id);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Password already set! Modification disabled.");
                disablePasswordModification();  // ✅ Disable after setting
            } else {
                // Insert new record
                try (PreparedStatement insertStmt = connectionDB.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, id);
                    insertStmt.setString(2, pass);
                    insertStmt.executeUpdate();
                    System.out.println("New account created successfully!");
                    disablePasswordModification();  // ✅ Disable after setting
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
