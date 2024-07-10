package Controlle;

import Database.ServiceInventoryDAO;
import Model.ServiceInventory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddInventoryController implements Initializable {
    private ServiceInventoryDAO serviceInventoryDAO = new ServiceInventoryDAO();
    private ImportDPController importDPController;  // Thay vì ServiceController

    @FXML
    private TextField SIQuantity;
    @FXML
    private TextField SIPrice;
    @FXML
    private TextField SITotal;
    @FXML
    private TextField SINOTE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SIQuantity.textProperty().addListener((observable, oldValue, newValue) -> calculateTotal());
        SIPrice.textProperty().addListener((observable, oldValue, newValue) -> calculateTotal());
    }

    @FXML
    private void SIHandleCancel(ActionEvent event) {
        // Đóng cửa sổ hiện tại
        Stage stage = (Stage) SIQuantity.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void SIHandleAdd(ActionEvent event) {
        try {
            if (importDPController == null) {
                showAlert("Error", "ImportDPController is not initialized.");
                return;
            }

            String serviceName = importDPController.getCurrentServiceName();
            if (serviceName == null) {
                showAlert("Error", "No service selected.");
                return;
            }

            int quantity = Integer.parseInt(SIQuantity.getText());
            int price = Integer.parseInt(SIPrice.getText());
            String note = SINOTE.getText();

            ServiceInventory serviceInventory = new ServiceInventory();
            serviceInventory.setService_name(serviceName);
            serviceInventory.setQuantity(quantity);
            serviceInventory.setUnit_price(price);
            serviceInventory.setInput_person("vophamthanhlong1");
            serviceInventory.setNote(note);

            serviceInventoryDAO.insertAndUpdate(serviceInventory);

            showAlert("Success", "Inventory added successfully!");

            // Đóng cửa sổ hiện tại
            Stage stage = (Stage) SIQuantity.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers for Quantity and Price.");
        }
    }

    private void calculateTotal() {
        try {
            int quantity = Integer.parseInt(SIQuantity.getText());
            int price = Integer.parseInt(SIPrice.getText());
            int total = quantity * price;
            SITotal.setText(String.valueOf(total));
        } catch (NumberFormatException e) {
            SITotal.setText("0");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setImportDPController(ImportDPController importDPController) {
        this.importDPController = importDPController;
    }
}
