package com.example.roomallocationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;

public class Allocation {

    @FXML
    private GridPane centerGrid;

    @FXML
    private Button backFromAllocate;

    @FXML
    private ChoiceBox<String> chooseDept;

    @FXML
    private Button roomAllocate;

    @FXML
    private Button whoAllocate;

    @FXML
    private Button searchRoom;

    @FXML
    private Spinner<Integer> minutes;

    @FXML
    private Spinner<Integer> hour;

    @FXML
    private Spinner<Integer> endMinutes;

    @FXML
    private Spinner<Integer> endhour;

    private Button selectedRoomButton = null; // Store the currently selected room button

    // Initialize method to load departments into the ChoiceBox and set up spinners
    @FXML
    private void initialize() {
        loadDepartments();  // Load all faculty names into the ChoiceBox
        setupStartTimeSpinners();  // Set up the spinner for start time
        setupEndTimeSpinners();    // Set up the spinner for end time
    }

    // Load faculty names into the ChoiceBox from the database
    private void loadDepartments() {
        try (Connection conn = sql.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT f_name FROM faculty")) {

            while (rs.next()) {
                chooseDept.getItems().add(rs.getString("f_name"));  // Add faculty names to the ChoiceBox
            }
        } catch (Exception e) {
            System.err.println("Failed to load departments!");
            e.printStackTrace();
        }
    }

    // Set up the start time spinners (hour and minutes)
    private void setupStartTimeSpinners() {
        hour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(9, 16, 10));  // Set the hour spinner to range from 9 to 16 with default 10
        hour.setEditable(true);  // Allow editing the spinner value if needed

        minutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));  // Set the minutes spinner to range from 0 to 59 with default 0
        minutes.setEditable(true);  // Allow editing the spinner value if needed
    }

    // Set up the end time spinners (end hour and minutes)
    private void setupEndTimeSpinners() {
        endhour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 17, 11));  // Set the end hour spinner to range from 10 to 17 with default 11
        endhour.setEditable(true);  // Allow editing the spinner value if needed

        endMinutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));  // Set the end minutes spinner to range from 0 to 59 with default 0
        endMinutes.setEditable(true);  // Allow editing the spinner value if needed
    }

    // Handle search button click: Load rooms for the selected department/faculty
    @FXML
    void searchRoomOnAction(ActionEvent actionEvent) {
        if (chooseDept.getValue() == null || chooseDept.getValue().isEmpty()) {
            showAlert("Please select a Faculty first!");
            return;
        }
        loadRooms(chooseDept.getValue()); // Load the rooms for the selected faculty
    }

    // Load rooms for the selected department/faculty
// Load rooms for the selected department/faculty
    private void loadRooms(String department) {
        centerGrid.getChildren().clear(); // Clear previous room buttons

        int row = 0, col = 0; // Grid positions for buttons

        try (Connection conn = sql.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT room_number FROM Room_info WHERE f_name = ?")) {

            stmt.setString(1, department);  // Set the department/faculty
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and create buttons for each room
            while (rs.next()) {
                int roomNumber = rs.getInt("room_number");
                Button roomButton = createRoomButton(roomNumber);

                // Check if room is already booked for the selected time range
                if (isRoomBookedInSelectedTime(roomNumber)) {
                    roomButton.setStyle("-fx-background-color: red;"); // Set button color to red for booked rooms
                }

                // Add the room button to the GridPane at the appropriate row/column
                centerGrid.add(roomButton, col, row);

                col++;
                if (col == 3) { // Every 3 buttons will go to the next row
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load rooms!");
            e.printStackTrace();
        }
    }

    // Check if the room is already booked in the selected time range
    private boolean isRoomBookedInSelectedTime(int roomNumber) {
        int startHour = hour.getValue();
        int startMinute = minutes.getValue();
        int endHour = endhour.getValue();
        int endMinute = endMinutes.getValue();

        LocalDateTime startDateTime = LocalDateTime.now().withHour(startHour).withMinute(startMinute).withSecond(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(endHour).withMinute(endMinute).withSecond(0);

        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

        // Check for any overlap with existing bookings
        String query = "SELECT * FROM Room_Bookings WHERE room_number = ? " +
                "AND allocated = TRUE " +
                "AND ((start_time < ? AND end_time > ?) OR " +
                "     (start_time < ? AND end_time > ?))";  // This checks if the time range overlaps

        try (Connection conn = sql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomNumber);         // Room number
            stmt.setTimestamp(2, endTimestamp); // End time of the selected booking
            stmt.setTimestamp(3, startTimestamp); // Start time of the selected booking
            stmt.setTimestamp(4, startTimestamp); // Start time of the selected booking
            stmt.setTimestamp(5, endTimestamp);  // End time of the selected booking

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If any record exists, return true meaning the room is already booked
        } catch (SQLException e) {
            System.err.println("Failed to check room booking status!");
            e.printStackTrace();
        }
        return false;  // Return false if no overlap is found
    }

    // Create a room button
    private Button createRoomButton(int roomNumber) {
        Button button = new Button("Room " + roomNumber);
        button.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-font-size: 14px;");

        // Set button's action when clicked
        button.setOnAction(event -> {
            if (selectedRoomButton != null) {
                selectedRoomButton.setStyle("-fx-background-color: lightgray;");  // Reset previous selection
            }
            button.setStyle("-fx-background-color: lightblue;");  // Highlight the selected room
            selectedRoomButton = button;  // Store the currently selected button
        });

        return button;
    }
    // Show alert messages to the user
    // Show alert messages to the user
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room Allocation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void roomAllocateButtonOnAction(ActionEvent actionEvent) {
        if (selectedRoomButton == null) {
            showAlert("No room selected!");
            return;
        }

        int selectedRoomNumber = Integer.parseInt(selectedRoomButton.getText().replace("Room ", ""));
        int startHour = hour.getValue();
        int startMinute = minutes.getValue();
        int endHour = endhour.getValue();
        int endMinute = endMinutes.getValue();

        LocalDateTime currentTime = LocalDateTime.now(); // বর্তমান সময়
        LocalDateTime startDateTime = LocalDateTime.now().withHour(startHour).withMinute(startMinute).withSecond(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(endHour).withMinute(endMinute).withSecond(0);

        if (startDateTime.isBefore(currentTime)) {
            showAlert("You cannot select a past time for booking!");
            return;
        }

        if (startDateTime.isAfter(endDateTime)) {
            showAlert("Start Time cannot be later than End Time!");
            return;
        }

        LocalDateTime nineAM = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0);
        LocalDateTime fivePM = LocalDateTime.now().withHour(17).withMinute(0).withSecond(0);

        if (startDateTime.isBefore(nineAM) || endDateTime.isAfter(fivePM)) {
            showAlert("Booking can only be made between 9:00 AM and 5:00 PM!");
            return;
        }

        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

        // **Check if the user has already booked another room at the same time**
        if (isRoomBooked(selectedRoomNumber, startTimestamp.toString(), endTimestamp.toString())) {
            showAlert("You have already booked a room for this time slot!");
            return;
        }

        // **Proceed with booking**
        bookRoom(selectedRoomNumber, startTimestamp.toString(), endTimestamp.toString());
        loadRooms(chooseDept.getValue());
    }

    // Method to book the room and save to the database
    private void bookRoom(int roomNumber, String startTime, String endTime) {
        String userIdStr = getCurrentUserId();

        if (userIdStr == null || userIdStr.isEmpty()) {
            showAlert("User is not logged in!");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr); // Parse user ID safely
            String query = "INSERT INTO Room_Bookings (room_number, department, start_time, end_time, allocated, user_id) " +
                    "VALUES (?, ?, ?, ?, TRUE, ?)";

            try (Connection conn = sql.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, roomNumber);
                stmt.setString(2, chooseDept.getValue()); // Department selected
                stmt.setString(3, startTime);
                stmt.setString(4, endTime);
                stmt.setInt(5, userId); // Use the valid parsed user ID

                stmt.executeUpdate();
                System.out.println("Room " + roomNumber + " booked from " + startTime + " to " + endTime);
            } catch (Exception e) {
                System.err.println("Failed to book room!");
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid user ID format!");
            e.printStackTrace();
        }
    }

    // Check if the room is already booked in the selected time range
    private boolean isRoomBooked(int selectedRoomNumber, String startTime, String endTime) {
        String query = "SELECT * FROM Room_Bookings WHERE (room_number = ? OR user_id = ?) " +
                "AND allocated = TRUE " +
                "AND ((start_time < ? AND end_time > ?) OR " +
                "     (start_time >= ? AND start_time < ?) OR " +
                "     (end_time > ? AND end_time <= ?))";

        try (Connection conn = sql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, selectedRoomNumber);  // রুম নাম্বার চেক
            stmt.setString(2, getCurrentUserId());  // বর্তমান ইউজারের আইডি চেক
            stmt.setString(3, endTime);  // শেষ সময়
            stmt.setString(4, startTime);  // শুরুর সময়
            stmt.setString(5, startTime);
            stmt.setString(6, endTime);
            stmt.setString(7, startTime);
            stmt.setString(8, endTime);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // যদি রেকর্ড পাওয়া যায়, তাহলে true অর্থাৎ ইতোমধ্যে বুক করা হয়েছে
        } catch (SQLException e) {
            System.err.println("Failed to check room booking status!");
            e.printStackTrace();
        }
        return false;  // যদি কোনো ম্যাচ না পাওয়া যায়, তাহলে বুক করা হয়নি
    }

    // Retrieve the current logged-in user's ID
    private String getCurrentUserId() {
        // Assuming you are storing the user ID in a static variable or session
        // If you're using a session management system, replace this method accordingly.
        String st=CrLogin.getUserId();
        return st;  // Get user ID from a hypothetical `LoggedInUser` class
    }

    @FXML
    void whoAllocateOnAction(ActionEvent actionEvent) {
        if (selectedRoomButton == null) {
            showAlert("No room selected!");
            return;
        }

        int selectedRoomNumber = Integer.parseInt(selectedRoomButton.getText().replace("Room ", ""));
        showRoomBookingInfo(selectedRoomNumber); // Call method to show who allocated the room
    }

    private void showRoomBookingInfo(int roomNumber) {
        // Get the selected start time to check if it overlaps with the booking
        int startHour = hour.getValue();
        int startMinute = minutes.getValue();
        int endHour = endhour.getValue();
        int endMinute = endMinutes.getValue();

        LocalDateTime startDateTime = LocalDateTime.now().withHour(startHour).withMinute(startMinute).withSecond(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(endHour).withMinute(endMinute).withSecond(0);

        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

        String query = "SELECT cr_name, cr_faculty, cr_dept, cr_level, cr_semester, cr_Phn, cr_mail, start_time, end_time " +
                "FROM Room_Bookings rb " +
                "JOIN CR_info cr ON rb.user_id = cr.cr_id " +
                "WHERE room_number = ? " +
                "AND allocated = TRUE " +
                "AND ((start_time < ? AND end_time > ?) OR " +  // Check if the booking overlaps with the selected time
                "     (start_time < ? AND end_time > ?))";

        try (Connection conn = sql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomNumber);  // Room number
            stmt.setTimestamp(2, endTimestamp);  // End time of the selected booking
            stmt.setTimestamp(3, startTimestamp);  // Start time of the selected booking
            stmt.setTimestamp(4, startTimestamp);  // Start time for overlap check
            stmt.setTimestamp(5, endTimestamp);  // End time for overlap check

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("cr_name");
                String faculty = rs.getString("cr_faculty");
                String department = rs.getString("cr_dept");
                int level = rs.getInt("cr_level");
                String semester = rs.getString("cr_semester");
                String phone = rs.getString("cr_Phn");
                String email = rs.getString("cr_mail");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");

                String bookingDetails = "Room " + roomNumber + " is already allocated!\n\n" +
                        "Allocated by: " + userName + "\n" +
                        "Faculty: " + faculty + "\n" +
                        "Department: " + department + "\n" +
                        "Level: " + level + "\n" +
                        "Semester: " + semester + "\n" +
                        "Phone: " + phone + "\n" +
                        "Email: " + email + "\n" +
                        "Start Time: " + startTime + "\n" +
                        "End Time: " + endTime;

                showAlert(bookingDetails); // Display the booking details in an alert
            } else {
                showAlert("Room is not currently allocated.");
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch room booking info!");
            e.printStackTrace();
        }
    }

    @FXML
    void backFromAllocateOnAction(ActionEvent event) {
        try {
            FXMLLoader root=new FXMLLoader(HelloController.class.getResource("crLogin.fxml"));
            Scene scene=new Scene(root.load());
            Stage stage=(Stage)backFromAllocate.getScene().getWindow();
            stage.setTitle("CR Page");
            stage.setScene(scene);
            stage.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



}
