package Model;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ServiceInventory {
    private int id;
    private int quantity;
    private int unit_price;
    private int total_price;

    public ServiceInventory() {
    }
    
    
    private java.sql.Timestamp timestamp;
    private String input_person;
    private String note;
    private String action;
    private HBox actions;
    private String service_name;
    private int importedDuringPeriod;
    
    private Button EditService;
     private Button DeletedService;

    public Button getEditService() {
        return EditService;
    }

    public void setEditService(Button EditService) {
        this.EditService = EditService;
    }

    public Button getDeletedService() {
        return DeletedService;
    }

    public void setDeletedService(Button DeletedService) {
        this.DeletedService = DeletedService;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void updateTotalPrice() {
        this.total_price = this.quantity * this.unit_price;
    }

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(java.sql.Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getInput_person() {
        return input_person;
    }

    public void setInput_person(String input_person) {
        this.input_person = input_person;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public HBox getActions() {
        return actions;
    }

    public void setActions(HBox actions) {
        this.actions = actions;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public int getImportedDuringPeriod() {
        return importedDuringPeriod;
    }

    public void setImportedDuringPeriod(int importedDuringPeriod) {
        this.importedDuringPeriod = importedDuringPeriod;
    }
}
