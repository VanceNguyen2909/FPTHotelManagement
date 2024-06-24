/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package AdminLogin;


import Database.ConnectDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author admin
 */
public class AdminLoginController implements Initializable {

    @FXML
    private TextField AdUsername;
    @FXML
    private TextField AdPassword;
    
    private ConnectDB connectDB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectDB = new ConnectDB(); // Initialize connectDB
    }    

    @FXML

    private void handleLoginAdmin(ActionEvent event) {
        String username = AdUsername.getText();
        String password = AdPassword.getText();

        if (login(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private boolean login(String username, String password) {
        String query = "SELECT * FROM Admin WHERE admin_name = ? AND admin_password = ?";
        try (Connection connection = connectDB.GetConnectDB(); // Use GetConnectDB method
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
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
