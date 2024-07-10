package Database;

import Controlle.EditServiceController;
import Controlle.ImportDPController;
import Model.Service;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ServiceDAO {
    static ConnectDB connect = new ConnectDB();
    static Connection cn = null;
    static Statement stm = null;
    static ResultSet rs = null;
    static PreparedStatement pStm = null;

    public List<Service> ListService() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Service";
        
        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);
            
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setServiceType(rs.getString("servicetype"));
                service.setPrice(rs.getInt("price"));
                service.setInitialStock(rs.getInt("initial_stock"));
                service.setImportedDuringPeriod(rs.getInt("importedDuringPeriod"));
                service.setSoldDuringPeriod(rs.getInt("soldDuringPeriod"));
                service.setRevenueDuringPeriod(rs.getInt("revenue_during_period"));
                service.setFinalStock(rs.getInt("final_stock"));
                service.setImportedDuringPeriodButton(createImportButton(service));
                service.setSoldDuringPeriodButton(createSoldButton(service));
                service.setEditService(EditService(service));
                service.setDeletedService(DeletedService(service));

                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        
        return services;
    }

    private Button EditService(Service service) {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> handleEditAction(service));
        return editButton;
    }

    private Button DeletedService(Service service) {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> handleDeleteAction(service));
        return deleteButton;
    }

    private Button createImportButton(Service service) {
        Button importButton = new Button("+");
        importButton.setOnAction(event -> handleImportAction(service));
        return importButton;
    }

    private Button createSoldButton(Service service) {
        Button soldButton = new Button("Show");
        soldButton.setOnAction(event -> handleShowAction(service));
        return soldButton;
    }

    private void handleEditAction(Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EditService.fxml"));
            Parent serviceParent = loader.load();

            EditServiceController controller = loader.getController();
            controller.setService(service);

            Stage newWindow = new Stage();
            newWindow.setTitle("Edit Service");
            newWindow.setScene(new Scene(serviceParent));
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteAction(Service service) {
        String deleteServiceInventorySQL = "DELETE FROM ServiceInventory WHERE service_name = ?";
        String deleteServiceSQL = "DELETE FROM Service WHERE id = ?";
        
        try (Connection cn = connect.GetConnectDB();
             PreparedStatement pStm1 = cn.prepareStatement(deleteServiceInventorySQL);
             PreparedStatement pStm2 = cn.prepareStatement(deleteServiceSQL)) {
            
            cn.setAutoCommit(false); // Bắt đầu giao dịch

            // Xóa bản ghi liên quan từ bảng ServiceInventory
            pStm1.setString(1, service.getName());
            pStm1.executeUpdate();
            
            // Xóa bản ghi từ bảng Service
            pStm2.setInt(1, service.getId());
            int rowsAffected = pStm2.executeUpdate();
            
            cn.commit(); // Hoàn tất giao dịch
            
            if (rowsAffected > 0) {
                // Xóa thành công, cập nhật lại danh sách
                showAlert("Success", "Service deleted successfully.");
                refreshServiceData();
                // Thiết lập lại giá trị IDENTITY
                resetServiceIdentity();
            } else {
                showAlert("Error", "No service found with the specified ID.");
            }
        } catch (SQLException e) {
            try {
                cn.rollback(); // Hoàn tác giao dịch nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            showAlert("Error", "Failed to delete service: " + e.getMessage());
        }
    }

    private void resetServiceIdentity() {
        String resetIdentitySQL = "DBCC CHECKIDENT ('Service', RESEED, 0)";
        try (Connection cn = connect.GetConnectDB();
             Statement stm = cn.createStatement()) {
            stm.execute(resetIdentitySQL);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to reset service IDENTITY: " + e.getMessage());
        }
    }

    public List<Service> refreshServiceData() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Service";
        
        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);
            
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setServiceType(rs.getString("servicetype"));
                service.setPrice(rs.getInt("price"));
                service.setInitialStock(rs.getInt("initial_stock"));
                service.setImportedDuringPeriod(rs.getInt("importedDuringPeriod"));
                service.setSoldDuringPeriod(rs.getInt("soldDuringPeriod"));
                service.setRevenueDuringPeriod(rs.getInt("revenue_during_period"));
                service.setFinalStock(rs.getInt("final_stock"));
                service.setImportedDuringPeriodButton(createImportButton(service));
                service.setSoldDuringPeriodButton(createSoldButton(service));
                service.setEditService(EditService(service));
                service.setDeletedService(DeletedService(service));

                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return services;
    }

    public void updateServiceInTableView(TableView<Service> tableView) {
        tableView.getItems().clear();
        tableView.getItems().addAll(refreshServiceData());
    }

    private void handleImportAction(Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ImportDP.fxml"));
            Parent serviceParent = loader.load();

            ImportDPController controller = loader.getController();
            controller.setService(service);  // Truyền đối tượng Service đến controller

            Stage newWindow = new Stage();
            newWindow.setTitle("Service Import");
            newWindow.setScene(new Scene(serviceParent));
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleShowAction(Service service) {
        // Logic for showing service details
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
