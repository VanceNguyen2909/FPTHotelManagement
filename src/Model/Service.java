package Model;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import Database.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Service {

    public Service() {
    }

  
    private int id;
    private String name;
    private int price;
    private int initialStock;
    private int importedDuringPeriod;
    private int soldDuringPeriod;
    private int revenueDuringPeriod;
    private int finalStock;


    private String note;
    private String serviceType;

  
     private Button EditService;
     private Button DeletedService;
    private Button importedDuringPeriodButton;
    private Button soldDuringPeriodButton;

    

    // Getters and Setters
   public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }

    public int getImportedDuringPeriod() {
        return importedDuringPeriod;
    }

    public void setImportedDuringPeriod(int importedDuringPeriod) {
        this.importedDuringPeriod = importedDuringPeriod;
    }

    public int getSoldDuringPeriod() {
        return soldDuringPeriod;
    }

    public void setSoldDuringPeriod(int soldDuringPeriod) {
        this.soldDuringPeriod = soldDuringPeriod;
    }

    public int getRevenueDuringPeriod() {
        return revenueDuringPeriod;
    }

    public void setRevenueDuringPeriod(int revenueDuringPeriod) {
        this.revenueDuringPeriod = revenueDuringPeriod;
    }

    public int getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(int finalStock) {
        this.finalStock = finalStock;
    }
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

    public Button getImportedDuringPeriodButton() {
        return importedDuringPeriodButton;
    }

    public void setImportedDuringPeriodButton(Button importedDuringPeriodButton) {
        this.importedDuringPeriodButton = importedDuringPeriodButton;
    }

    public Button getSoldDuringPeriodButton() {
        return soldDuringPeriodButton;
    }

    public void setSoldDuringPeriodButton(Button soldDuringPeriodButton) {
        this.soldDuringPeriodButton = soldDuringPeriodButton;
    }
    
}
