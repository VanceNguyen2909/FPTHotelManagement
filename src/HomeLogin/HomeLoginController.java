/**
 * Sample Skeleton for 'HomeLogin.fxml' Controller Class
 */

package HomeLogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeLoginController {


    
    
    @FXML
    private void handleButtonAdmin(ActionEvent event) throws IOException {
            try {
            // Sửa đường dẫn ở đây nếu cần thiết
            Parent homeLoginParent = FXMLLoader.load(getClass().getResource("/AdminLogin/AdminLogin.fxml"));
            Scene homeLoginScene = new Scene(homeLoginParent);

            // Lấy thông tin của stage
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(homeLoginScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    @FXML
    void handleButtonEmployee(ActionEvent event) {

    }

    @FXML
    void handleButtonManeger(ActionEvent event) {

    }
    
    @FXML
    private void handleButtonCustomer(ActionEvent event) throws IOException {
            try {
            // Sửa đường dẫn ở đây nếu cần thiết
            Parent homeLoginParent = FXMLLoader.load(getClass().getResource("/CustomerLogin/CustomerLogin.fxml"));
            Scene homeLoginScene = new Scene(homeLoginParent);

            // Lấy thông tin của stage
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(homeLoginScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initialize() {

    }
}
