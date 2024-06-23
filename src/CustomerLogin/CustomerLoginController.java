package CustomerLogin;

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
import javafx.scene.control.TextField;

public class CustomerLoginController implements Initializable {

    @FXML
    private TextField CusUsername;
    @FXML
    private TextField CusPassword;

    private ConnectDB connectDB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectDB = new ConnectDB(); // Initialize connectDB
    }

    @FXML
    private void handleLoginCustomer(ActionEvent event) {
        String username = CusUsername.getText();
        String password = CusPassword.getText();

        if (login(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private boolean login(String username, String password) {
        String query = "SELECT * FROM Login_Customer WHERE customer_username = ? AND customer_password = ?";
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
}
