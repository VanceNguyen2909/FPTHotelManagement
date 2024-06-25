/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package EmployeeLogin;

import Database.ConnectDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class EmployeeLoginController implements Initializable {

    @FXML
    private TextField EUsername;
    @FXML
    private TextField EPassword;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    
    private String[] region = {"Ho Chi Minh", "Da Nang", "Ha Noi"};
    
    private ConnectDB connectDB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myChoiceBox.getItems().addAll(region);
        connectDB = new ConnectDB();
    }    

    @FXML
    private void HandleEmployeeLogin(ActionEvent event) {
        String username = EUsername.getText();
        String password = EPassword.getText();
        String selectedRegion = myChoiceBox.getValue();

        if (username.isEmpty()) {
            showAlert("Error", "Username cannot be empty");
            return;
        }

        if (password.isEmpty()) {
            showAlert("Error", "Password cannot be empty");
            return;
        }

        if (selectedRegion == null) {
            showAlert("Error", "Please select a region");
            return;
        }

        try (Connection conn = connectDB.GetConnectDB()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT region_name FROM Employee WHERE employee_username = ? AND employee_password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String regionName = rs.getString("region_name");
                if (selectedRegion.equals(regionName)) {
                    showAlert("Success", "Login successful!");
                } else {
                    showAlert("Error", "Incorrect region selected");
                }
            } else {
                showAlert("Error", "Invalid username or password");
            }

        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
