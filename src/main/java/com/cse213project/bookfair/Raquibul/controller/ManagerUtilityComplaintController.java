package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.StallManager;
import com.cse213project.bookfair.Raquibul.model.UtilityComplaint;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ManagerUtilityComplaintController {
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> priorityComboBox;
    @FXML private TextField descriptionTextField;
    @FXML private Label statusLabel;
    @FXML private TableView<UtilityComplaint> complaintTableView;
    @FXML private TableColumn<UtilityComplaint, String> compIdCol;
    @FXML private TableColumn<UtilityComplaint, String> typeCol;
    @FXML private TableColumn<UtilityComplaint, String> priorityCol;
    @FXML private TableColumn<UtilityComplaint, String> statusCol;
    @FXML private TableColumn<UtilityComplaint, String> descCol;

    public void initialize() {
        categoryComboBox.getItems().addAll("Electrical", "Waste", "Wi-Fi", "Security");
        categoryComboBox.setValue("Electrical");
        priorityComboBox.getItems().addAll("High", "Medium", "Low");
        priorityComboBox.setValue("High");

        compIdCol.setCellValueFactory(new PropertyValueFactory<UtilityComplaint, String>("complaintID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<UtilityComplaint, String>("damageType"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<UtilityComplaint, String>("priority"));
        statusCol.setCellValueFactory(new PropertyValueFactory<UtilityComplaint, String>("status"));
        descCol.setCellValueFactory(new PropertyValueFactory<UtilityComplaint, String>("description"));

        loadComplaints();
    }

    private void loadComplaints() {
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        BinaryFileHelper<UtilityComplaint> compFile = new BinaryFileHelper<UtilityComplaint>("UtilityComplaints.bin");
        ArrayList<UtilityComplaint> list = compFile.readAll();
        complaintTableView.getItems().clear();
        for (UtilityComplaint c : list) {
            if (c.getStallNo().equals(manager.getAssignedStallNo())) {
                complaintTableView.getItems().add(c);
            }
        }
    }

    @FXML
    public void submitComplaintButtonOnAction(ActionEvent event) {
        String desc = descriptionTextField.getText();
        if (desc == null || desc.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Description", "Complaint description cannot be empty.");
            return;
        }
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        UtilityComplaint complaint = manager.submitUtilityComplaint(categoryComboBox.getValue(),
                desc, priorityComboBox.getValue());
        statusLabel.setText("Ticket " + complaint.getComplaintID() + " filed and escalated to Maintenance Control Room.");
        descriptionTextField.setText("");
        loadComplaints();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "StallManagerDashboardView.fxml", "Ekushey Boimela - Stall Manager Dashboard");
    }
}
