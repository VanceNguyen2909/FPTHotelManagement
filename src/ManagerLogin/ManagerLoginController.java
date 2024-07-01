package ManagerLogin;

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
import Database.ConnectDB;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerLoginController implements Initializable {

    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private TextField MUsername;
    @FXML
    private TextField MPassword;

    private String[] region = {"Ho Chi Minh", "Da Nang", "Ha Noi"};

    private ConnectDB connectDB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myChoiceBox.getItems().addAll(region);
        connectDB = new ConnectDB();
    }

    @FXML
    private void HandleManagerLogin(ActionEvent event) {
        String username = MUsername.getText();
        String password = MPassword.getText();
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
            PreparedStatement pstmt = conn.prepareStatement("SELECT region_name FROM Manager WHERE manager_username = ? AND manager_password = ?");
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
    
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
            try {
            // Sửa đường dẫn ở đây nếu cần thiết
            Parent homeLoginParent = FXMLLoader.load(getClass().getResource("/HomeLogin/HomeLogin.fxml"));
            Scene homeLoginScene = new Scene(homeLoginParent);

            // Lấy thông tin của stage
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(homeLoginScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
