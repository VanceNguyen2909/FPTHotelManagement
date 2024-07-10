package Controlle;

import Database.ServiceInventoryDAO;
import Model.Service;
import Model.ServiceInventory;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ImportDPController implements Initializable {

    @FXML
    private Label labelserviceinventony;
    @FXML
    private TableColumn<ServiceInventory, Integer> SinventorytcId;
    @FXML
    private TableColumn<ServiceInventory, Integer> SinventorytcQuan;
    @FXML
    private TableColumn<ServiceInventory, Integer> SinventorytcPrice;
    @FXML
    private TableColumn<ServiceInventory, Integer> SinventorytcTotal;
    @FXML
    private TableColumn<ServiceInventory, Timestamp> SinventorytcTime;
    @FXML
    private TableColumn<ServiceInventory, String> SinventorytcUser;
    @FXML
    private TableColumn<ServiceInventory, String> SinventorytcAction;
    @FXML
    private TableView<ServiceInventory> TableSinventory;
    @FXML
    private TableColumn<ServiceInventory, String> SinventorytcNote;

    private ServiceInventoryDAO dao = new ServiceInventoryDAO();
    private ObservableList<ServiceInventory> list = FXCollections.observableArrayList();
    private FilteredList<ServiceInventory> filteredList = new FilteredList<>(list, p -> true);
    private Service currentService;
    @FXML
    private TableColumn<ServiceInventory, String> Editbutton;
    @FXML
    private TableColumn<ServiceInventory, String> DeleteButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    // Phương thức để nhận đối tượng Service và cập nhật Label và danh sách hiển thị
    public void setService(Service service) {
        currentService = service;
        labelserviceinventony.setText(service.getName());
        loadServiceInventory(service.getName());
    }

    // Phương thức để lấy tên dịch vụ hiện tại
    public String getCurrentServiceName() {
        return currentService != null ? currentService.getName() : null;
    }

    // Hiển thị các mục nhập kho liên quan đến dịch vụ hiện tại
    private void loadServiceInventory(String serviceName) {
        list.clear();
        List<ServiceInventory> allInventory = dao.ListServiceInventory();
        for (ServiceInventory inventory : allInventory) {
            if (inventory.getService_name().equals(serviceName)) {
                list.add(inventory);
            }
        }

        SinventorytcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        SinventorytcQuan.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        SinventorytcPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        SinventorytcTotal.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        SinventorytcTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        SinventorytcUser.setCellValueFactory(new PropertyValueFactory<>("input_person"));
        SinventorytcNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        Editbutton.setCellValueFactory(new PropertyValueFactory<>("EditService"));
        DeleteButton.setCellValueFactory(new PropertyValueFactory<>("DeletedService"));
        
        TableSinventory.setItems(filteredList);
    }

    @FXML
    private void handleInputInventory(ActionEvent event) {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AddInventory.fxml"));
            Parent serviceParent = loader.load();

            AddInventoryController controller = loader.getController();
            controller.setImportDPController(this); // Thiết lập importDPController

            Stage newWindow = new Stage();
            newWindow.setTitle("Service Add");
            newWindow.setScene(new Scene(serviceParent));
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void refreshServiceData(String serviceName) {
        list.clear();
        List<ServiceInventory> allInventory = dao.ListServiceInventory();
        for (ServiceInventory inventory : allInventory) {
            if (inventory.getService_name().equals(serviceName)) {
                list.add(inventory);
            }
        }
        list.addAll(dao.ListServiceInventory());
    }
    
    @FXML
    private void HandleFresh(ActionEvent event) {
        if (currentService != null) {
            loadServiceInventory(currentService.getName());
        }
    }
}
