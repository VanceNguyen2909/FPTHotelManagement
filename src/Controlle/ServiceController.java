package Controlle;

import Database.ServiceDAO;
import FXML.ServiceTest;
import Model.Service;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class ServiceController implements Initializable{

  @FXML
    private TableView<Service> ServicesTable;
    @FXML
    private TableColumn<Service, Integer> sttColumn;
    @FXML
    private TableColumn<Service, String> serviceNameColumn;
    @FXML
    private TableColumn<Service, Integer> servicePriceColumn;
    @FXML
    private TableColumn<Service, Integer> initialStockColumn;
    @FXML
    private TableColumn<Service, String> importedDuringPeriodColumn;
    @FXML
    private TableColumn<Service, Integer> soldDuringPeriodColumn;
    @FXML
    private TableColumn<Service, Integer> revenueDuringPeriodColumn;
    @FXML
    private TableColumn<Service, Integer> finalStockColumn;
    @FXML
    private TableColumn<Service, String> actionsColumn;
    @FXML
    private TableColumn<Service, String> ServiceTypeColumn;
      @FXML
    private TableColumn<Service, String> importedDuringPeriodButton;
    @FXML
    private TableColumn<Service, Integer> importedDuringPeriodInt;
    @FXML
    private TableColumn<Service, String> soldDuringPeriodButton;
    @FXML
    private TableColumn<Service, Integer> soldDuringPeriodButtonInt;
      @FXML
    private TableColumn<Service, String> EditService;
    @FXML
    private TableColumn<Service, String> DeletedService;
      @FXML
    private TableColumn<Service, String> ServiceNote;
       ServiceDAO dao = new ServiceDAO();//goi den ProducTDAO -> dao sẽ có tất cả thuộc tính của ProductDAO
    ObservableList<Model.Service> list = FXCollections.observableArrayList(dao.ListService());

    FilteredList<Model.Service> filteredList = new FilteredList<>(list, p -> true);

    ServiceTest proSelected = null;
    int indexSelected = 0;
  
  
  
  
    
    @FXML
    private void handleAddAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AddPage.fxml"));
            Parent ServiceParent = loader.load();
            
            AddPageController controller = loader.getController();
            controller.setServiceController(this); // Truyền đối tượng ServiceController

            Scene ServiceScene = new Scene(ServiceParent);

            // Tạo một Stage mới
            Stage newWindow = new Stage();
            newWindow.setTitle("Service Add");
            newWindow.setScene(ServiceScene);
            newWindow.initModality(Modality.NONE);

            // Hiển thị cửa sổ mới
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadService();
    } 
    
    private void loadService() {
        sttColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ServiceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        initialStockColumn.setCellValueFactory(new PropertyValueFactory<>("initialStock"));
        importedDuringPeriodButton.setCellValueFactory(new PropertyValueFactory<>("importedDuringPeriodButton"));
        importedDuringPeriodInt.setCellValueFactory(new PropertyValueFactory<>("importedDuringPeriod"));
        soldDuringPeriodButton.setCellValueFactory(new PropertyValueFactory<>("soldDuringPeriodButton"));
        soldDuringPeriodButtonInt.setCellValueFactory(new PropertyValueFactory<>("soldDuringPeriod"));
        revenueDuringPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("revenueDuringPeriod"));
        finalStockColumn.setCellValueFactory(new PropertyValueFactory<>("finalStock"));
        EditService.setCellValueFactory(new PropertyValueFactory<>("EditService"));
        DeletedService.setCellValueFactory(new PropertyValueFactory<>("DeletedService"));
        
        ServicesTable.setItems(filteredList);
    }
    
    public void refreshServiceData() {
        list.clear();
        list.addAll(dao.ListService());
    }
    
    @FXML
    private void HandleFresh() {
        refreshServiceData();
    }
}
