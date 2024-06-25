/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ManagerLogin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ManagerLoginController implements Initializable {

    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private TextField MUsername;
    @FXML
    private TextField MPassword;
    
    private String[]region = {"Hồ Chí Minh", "Đà Nẵng", "Hà Nội"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myChoiceBox.getItems().addAll(region);
    }    

    @FXML
    private void HandleManagerLogin(ActionEvent event) {
    }
    
}
