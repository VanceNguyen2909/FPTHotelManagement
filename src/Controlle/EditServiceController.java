package Controlle;

import Database.ConnectDB;
import Database.ServiceDAO;
import Model.Service;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditServiceController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfTYPE;
    @FXML
    private TextField tfNote;

    private Service service; // The service being edited
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConnectDB connectDB = new ConnectDB();
        connection = connectDB.GetConnectDB();
    }

    public void setService(Service service) {
        this.service = service;
        tfName.setText(service.getName());
        tfTYPE.setText(service.getServiceType());
        tfPrice.setText(String.valueOf(service.getPrice()));
        tfNote.setText(service.getNote()); // Sử dụng ghi chú từ dịch vụ
    }

    @FXML
    private void handleCancelEdit(ActionEvent event) {
        Stage stage = (Stage) tfName.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        String name = tfName.getText();
        String type = tfTYPE.getText();
        int price = Integer.parseInt(tfPrice.getText());
        String note = tfNote.getText();

        try {
            String query = "UPDATE Service SET name = ?, servicetype = ?, price = ?, note = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, price);
            preparedStatement.setString(4, note);
            preparedStatement.setInt(5, service.getId()); // Đặt giá trị cho tham số thứ 5

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Service updated successfully!");
                // Đóng cửa sổ sau khi cập nhật thành công
                Stage stage = (Stage) tfName.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Error", "Failed to update service.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to update service: " + ex.getMessage());
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
