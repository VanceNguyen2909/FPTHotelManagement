package SignUp;

import Database.ConnectDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SIGNUPController implements Initializable {

    @FXML
    private TextField Fname;
    @FXML
    private TextField CusEmail;
    @FXML
    private TextField CusUsername;
    @FXML
    private TextField CusPass;
    @FXML
    private TextField CusCPass;
    @FXML
    private TextField CusPhone;
    

    private ConnectDB connectDB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectDB = new ConnectDB();
    }

    @FXML
    private void HandleSignUp(ActionEvent event) {
        String name = Fname.getText();
        String email = CusEmail.getText();
        String username = CusUsername.getText();
        String password = CusPass.getText();
        String confirmPassword = CusCPass.getText();
        String phone = CusPhone.getText();
        

        if (name.isEmpty()) {
            showAlert("Error", "Full name cannot be empty");
            return;
        }
        
        if (!isValidEmail(email)) {
            showAlert("Error", "Invalid email format");
            return;
        }
        
        if (username.isEmpty()) {
            showAlert("Error", "Username cannot be empty");
            return;
        }

        if (username.isEmpty()) {
            showAlert("Error", "Username cannot be empty");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Password and Confirm Password do not match");
            return;
        }

        

        if (!isNumeric(phone)) {
            showAlert("Error", "Phone number must contain only digits");
            return;
        }

        try (Connection conn = connectDB.GetConnectDB()) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Customer (customer_name, customer_username, customer_password, customer_email, customer_phone) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);

            pstmt.executeUpdate();
            showAlert("Success", "Sign up successful!");

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

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("[0-9]+");
    }
}
