package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.StaffPass;
import com.cse213project.bookfair.Raquibul.model.StallManager;
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

public class ManagerStaffPassController {
    @FXML private TextField staffNameTextField;
    @FXML private TextField nidTextField;
    @FXML private TextField contactTextField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label statusLabel;
    @FXML private TableView<StaffPass> passTableView;
    @FXML private TableColumn<StaffPass, String> passIdCol;
    @FXML private TableColumn<StaffPass, String> nameCol;
    @FXML private TableColumn<StaffPass, String> roleCol;
    @FXML private TableColumn<StaffPass, String> qrCol;
    @FXML private TableColumn<StaffPass, Boolean> activeCol;

    public void initialize() {
        roleComboBox.getItems().addAll("Sales Attendant", "Cashier", "Stock Helper", "Supervisor");
        roleComboBox.setValue("Sales Attendant");

        passIdCol.setCellValueFactory(new PropertyValueFactory<StaffPass, String>("passID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<StaffPass, String>("staffName"));
        roleCol.setCellValueFactory(new PropertyValueFactory<StaffPass, String>("staffRole"));
        qrCol.setCellValueFactory(new PropertyValueFactory<StaffPass, String>("qrCode"));
        activeCol.setCellValueFactory(new PropertyValueFactory<StaffPass, Boolean>("isActive"));

        loadPasses();
    }

    private void loadPasses() {
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        BinaryFileHelper<StaffPass> passFile = new BinaryFileHelper<StaffPass>("StaffPasses.bin");
        ArrayList<StaffPass> list = passFile.readAll();
        passTableView.getItems().clear();
        for (StaffPass sp : list) {
            if (sp.getStallNo().equals(manager.getAssignedStallNo())) {
                passTableView.getItems().add(sp);
            }
        }
    }

    @FXML
    public void issuePassButtonOnAction(ActionEvent event) {
        String name = staffNameTextField.getText();
        String nid = nidTextField.getText();
        String phone = contactTextField.getText();

        if (name.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Name", "Staff name cannot be empty.");
            return;
        }
        if (nid.length() != 10 && nid.length() != 17) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid NID", "NID must be 10 or 17 digits.");
            return;
        }
        if (phone.length() != 11) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Phone", "Contact phone must be 11 digits.");
            return;
        }

        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        StaffPass issued = manager.manageStaffPasses(name, roleComboBox.getValue(), "Issue");
        if (issued == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Quota Exceeded", "Maximum 5 active staff passes allowed per stall.");
            return;
        }
        statusLabel.setText("Badge Issued! Pass ID: " + issued.getPassID() + " | QR: " + issued.getQrCode());
        staffNameTextField.setText("");
        loadPasses();
    }

    @FXML
    public void revokePassButtonOnAction(ActionEvent event) {
        StaffPass selected = passTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "No Selection", "Select an active staff pass to revoke.");
            return;
        }
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        StaffPass revoked = manager.manageStaffPasses(selected.getStaffName(), selected.getStaffRole(), "Revoke");
        if (revoked != null) {
            statusLabel.setText("Pass " + revoked.getPassID() + " for " + revoked.getStaffName() + " has been revoked.");
            loadPasses();
        } else {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Already Inactive", "This pass is already revoked.");
        }
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "StallManagerDashboardView.fxml", "Ekushey Boimela - Stall Manager Dashboard");
    }
}
