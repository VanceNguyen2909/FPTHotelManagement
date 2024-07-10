package Database;

import Controlle.EditInventoryController;
import Controlle.EditServiceController;
import Controlle.ImportDPController;
import static Database.ServiceDAO.rs;

import Model.ServiceInventory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ServiceInventoryDAO {
    static ConnectDB connect = new ConnectDB();
    static Connection cn = null;
    static Statement stm = null;
    static PreparedStatement pStm = null;
    
    private ImportDPController importDPController; // Add this to your ServiceInventoryDAO class

// Initialize importDPController appropriately where needed, maybe in a constructor or a setter method
    public void setImportDPController(ImportDPController importDPController) {
            this.importDPController = importDPController;
        }

    public List<ServiceInventory> ListServiceInventory() {
        List<ServiceInventory> services = new ArrayList<>();
        String sql = "SELECT ServiceInventory.*, Service.name, Service.importedDuringPeriod " +
                     "FROM ServiceInventory " +
                     "RIGHT JOIN Service ON ServiceInventory.service_name = Service.name " +
                     "WHERE ServiceInventory.service_name IS NOT NULL"; // Thêm điều kiện để chỉ lấy các bản ghi hợp lệ

        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                ServiceInventory service = new ServiceInventory();
                service.setId(rs.getInt("id"));
                service.setQuantity(rs.getInt("quantity"));
                service.setUnit_price(rs.getInt("unit_price"));
                service.updateTotalPrice();
                service.setTimestamp(rs.getTimestamp("timestamp"));
                service.setInput_person(rs.getString("input_person"));
                service.setNote(rs.getString("note"));
//                service.setAction(rs.getString("action"));
                service.setEditService(EditService(service));
                service.setDeletedService(DeletedService(service));

                // Thiết lập các trường bổ sung từ bảng Service
                service.setService_name(rs.getString("name"));
                service.setImportedDuringPeriod(rs.getInt("importedDuringPeriod"));

                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return services;
    }

    public void insertAndUpdate(ServiceInventory serviceInventory) {
        String insertSQL = "INSERT INTO ServiceInventory (service_name, quantity, unit_price, input_person, note) VALUES (?, ?, ?, ?, ?)";
        String updateServiceSQL = "UPDATE Service SET importedDuringPeriod = (SELECT SUM(quantity) FROM ServiceInventory WHERE service_name = ?) WHERE name =?";

        try {
            cn = connect.GetConnectDB();
            cn.setAutoCommit(false); // Start transaction

            // Insert into ServiceInventory
            pStm = cn.prepareStatement(insertSQL);
            pStm.setString(1, serviceInventory.getService_name());
            pStm.setInt(2, serviceInventory.getQuantity());
            pStm.setInt(3, serviceInventory.getUnit_price());
            pStm.setString(4, serviceInventory.getInput_person());
            pStm.setString(5, serviceInventory.getNote());
            pStm.executeUpdate();

            // Update Service
            pStm = cn.prepareStatement(updateServiceSQL);
            pStm.setString(1, serviceInventory.getService_name());
            pStm.setString(2, serviceInventory.getService_name());
            pStm.executeUpdate();

            cn.commit(); // Commit transaction
        } catch (SQLException e) {
            try {
                if (cn != null) {
                    cn.rollback(); // Rollback transaction on error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (pStm != null) {
                    pStm.close();
                }
                if (cn != null) {
                    cn.setAutoCommit(true); // Reset to default state
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void UpdateInventory(ServiceInventory serviceInventory) {
        String updateInventorySQL = "UPDATE ServiceInventory SET quantity = ?, unit_price = ?, note = ? WHERE id = ?";
        String updateServiceSQL = "UPDATE Service SET importedDuringPeriod =( SELECT SUM(quantity) FROM ServiceInventory WHERE service_name = ?) WHERE name = ?";

        try {
            cn = connect.GetConnectDB();
            cn.setAutoCommit(false); // Start transaction

            // Insert into ServiceInventory
            pStm = cn.prepareStatement(updateInventorySQL);
            pStm.setInt(1, serviceInventory.getQuantity());
            pStm.setInt(2, serviceInventory.getUnit_price());
            pStm.setString(3, serviceInventory.getNote());
            pStm.executeUpdate();

            // Update Service
            pStm = cn.prepareStatement(updateServiceSQL);
            pStm.setInt(1, serviceInventory.getQuantity());
            pStm.setString(2, serviceInventory.getService_name());
            pStm.executeUpdate();

            cn.commit(); // Commit transaction
        } catch (SQLException e) {
            try {
                if (cn != null) {
                    cn.rollback(); // Rollback transaction on error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (pStm != null) {
                    pStm.close();
                }
                if (cn != null) {
                    cn.setAutoCommit(true); // Reset to default state
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    

    private Button EditService(ServiceInventory service) {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> handleEditAction(service));
        return editButton;
    }

    private Button DeletedService(ServiceInventory service) {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> handleDeleteAction(service));
        return deleteButton;
    }
    
    private HBox EditActions(ServiceInventory service) {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> handleEditAction(service));
        return new HBox(5, editButton);
    }

    private HBox DeletedActions(ServiceInventory service) {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> handleDeleteAction(service));
        return new HBox(6, deleteButton);
    }

    private void handleEditAction(ServiceInventory service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EditInventory.fxml"));
            Parent serviceParent = loader.load();

            EditInventoryController controller = loader.getController();
            controller.setServiceInventory(service);

            Stage newWindow = new Stage();
            newWindow.setTitle("Edit Service");
            newWindow.setScene(new Scene(serviceParent));
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void handleDeleteAction(ServiceInventory service) {
//        String sql = "DELETE FROM ServiceInventory WHERE id = ?";
//        String updateServiceSQL = "UPDATE Service SET importedDuringPeriod = (SELECT SUM(quantity) FROM ServiceInventory WHERE service_name = ?) WHERE name =?";
//        try {
//            cn = connect.GetConnectDB();
//            pStm = cn.prepareStatement(sql);
//            pStm.setInt(1, service.getId());
//            pStm.executeUpdate();
//            // Làm mới danh sách dịch vụ sau khi xóa
//            
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    
    private void handleDeleteAction(ServiceInventory service) {
        String deleteSQL = "DELETE FROM ServiceInventory WHERE id = ?";
        String updateServiceSQL = "UPDATE Service SET importedDuringPeriod = (SELECT SUM(quantity) FROM ServiceInventory WHERE service_name = ?) WHERE name = ?";

        try {
            cn = connect.GetConnectDB();
            cn.setAutoCommit(false); // Start transaction

            // Delete from ServiceInventory
            pStm = cn.prepareStatement(deleteSQL);
            pStm.setInt(1, service.getId());
            int deleteRowsAffected = pStm.executeUpdate();

            // Update Service
            pStm = cn.prepareStatement(updateServiceSQL);
            pStm.setString(1, service.getService_name());
            pStm.setString(2, service.getService_name());
            int updateRowsAffected = pStm.executeUpdate();

            cn.commit(); // Commit transaction

            if (deleteRowsAffected > 0 && updateRowsAffected > 0) {
                showAlert("Success", "Service inventory deleted and service updated successfully.");
                // Refresh the service data after deletion
                
            } else {
                showAlert("Error", "Failed to delete service inventory or update service.");
            }
        } catch (SQLException e) {
            try {
                if (cn != null) {
                    cn.rollback(); // Rollback transaction on error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            showAlert("Error", "Failed to delete service inventory: " + e.getMessage());
        } finally {
            try {
                if (pStm != null) {
                    pStm.close();
                }
                if (cn != null) {
                    cn.setAutoCommit(true); // Reset to default state
                    cn.close();
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

