/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlle;

import Database.ConnectDB;
import Model.Service;
import Model.ServiceInventory;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class EditInventoryController implements Initializable {

    @FXML
    private AnchorPane HandleCancel;
    @FXML
    private TextField SIQuantity;
    @FXML
    private TextField SIPrice;
    @FXML
    private TextField SINOTE;
    @FXML
    private Button HandleUpdate;
    @FXML
    private TextField SITotal;
    
    private ServiceInventory service; // The service being edited
    private Connection connection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ConnectDB connectDB = new ConnectDB();
        connection = connectDB.GetConnectDB();
    }    
    
//    public void setService(Service service) {
//        this.service = service;
//        tfName.setText(service.getName());
//        tfTYPE.setText(service.getServiceType());
//        tfPrice.setText(String.valueOf(service.getPrice()));
//        tfNote.setText(service.getNote()); // Sử dụng ghi chú từ dịch vụ
//    }

    public void setServiceInventory(ServiceInventory service) {
        this.service = service;
        
        SIQuantity.setText(String.valueOf(service.getQuantity()));
        SIPrice.setText(String.valueOf(service.getUnit_price()));
        SINOTE.setText(service.getNote()); // Sử dụng ghi chú từ dịch vụ
    }
    

    @FXML
    private void handleCancelEdit(ActionEvent event) {
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        int quantity = Integer.parseInt(SIQuantity.getText());
        int price = Integer.parseInt(SIPrice.getText());
        String note = SINOTE.getText();

        try {
            // Set auto-commit to false to start a transaction
            connection.setAutoCommit(false);

            // Update ServiceInventory
            String updateInventorySQL = "UPDATE ServiceInventory SET quantity = ?, unit_price = ?, note = ? WHERE id = ?";
            PreparedStatement updateInventoryStmt = connection.prepareStatement(updateInventorySQL);
            updateInventoryStmt.setInt(1, quantity);
            updateInventoryStmt.setInt(2, price);
            updateInventoryStmt.setString(3, note);
            updateInventoryStmt.setInt(4, service.getId());

            int inventoryRowsAffected = updateInventoryStmt.executeUpdate();

            // Update Service
            String updateServiceSQL = "UPDATE Service SET importedDuringPeriod = (SELECT SUM(quantity) FROM ServiceInventory WHERE service_name = ?) WHERE name = ?";
            PreparedStatement updateServiceStmt = connection.prepareStatement(updateServiceSQL);
            updateServiceStmt.setString(1, service.getService_name());
            updateServiceStmt.setString(2, service.getService_name());

            int serviceRowsAffected = updateServiceStmt.executeUpdate();

            // Commit the transaction
            connection.commit();

            if (inventoryRowsAffected > 0 && serviceRowsAffected > 0) {
                showAlert("Success", "Service updated successfully!");
                // Close the window after a successful update
                Stage stage = (Stage) SINOTE.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Error", "Failed to update service.");
            }
        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction on error
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            showAlert("Error", "Failed to update service: " + ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Reset to default state
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
