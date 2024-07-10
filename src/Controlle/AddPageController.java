/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Database.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.scene.control.Alert;
import java.sql.SQLException;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AddPageController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField SName;
    @FXML
    private Label label1;
    @FXML
    private TextField SType;
    @FXML
    private Label label11;
    @FXML
    private TextField SPrice;
    
    private ConnectDB connectDB;
    
    private ServiceController serviceController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connectDB = new ConnectDB();
    }    

    @FXML
    private void handleAdd(ActionEvent event) {
        String Name = SName.getText();
        String Type = SType.getText();
        int Init_stock = 0;
        int Imported = 0;
        int Sold = 0;
        int Revenue = 0;
        int Final = 0;
        String Note = "0";
        int Price;
        try {
            Price = Integer.parseInt(SPrice.getText()); // Chuyển đổi giá trị từ TextField thành số nguyên
        } catch (NumberFormatException e) {
            showAlert("Error", "Price must be a valid integer.");
            return; // Ngừng thực hiện nếu giá trị không hợp lệ
        }

        try (Connection conn = connectDB.GetConnectDB()) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Service (name, servicetype, price, initial_stock, importedDuringPeriod, soldDuringPeriod, revenue_during_period, final_stock, Note) VALUES (?,?,?,?,?,?,?,?,?) ");
            pstmt.setString(1, Name);
            pstmt.setString(2, Type);
            pstmt.setInt(3, Price);
            pstmt.setInt(4, Init_stock);
            pstmt.setInt(5, Imported);
            pstmt.setInt(6, Sold);
            pstmt.setInt(7, Revenue);
            pstmt.setInt(8, Final);
            pstmt.setString(9, Note);
            

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Service added successfully!");
                if (serviceController != null) {
                    serviceController.refreshServiceData(); // Cập nhật danh sách sau khi thêm
                }
                Stage stage = (Stage) SName.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Error", "Failed to add service.");
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
    
    public void setServiceController(ServiceController serviceController) {
        this.serviceController = serviceController;
    }
}
