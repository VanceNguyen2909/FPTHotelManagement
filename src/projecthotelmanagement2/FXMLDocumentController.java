/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package projecthotelmanagement2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.stage.Stage;

/**
 *
 * @author ADMIN
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonSignIn(ActionEvent event) throws IOException {
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

    @FXML
    private void handleButtonSIGNUP(ActionEvent event) throws IOException {
            try {
            // Sửa đường dẫn ở đây nếu cần thiết
            Parent homeLoginParent = FXMLLoader.load(getClass().getResource("/SignUp/SIGNUP.fxml"));
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
