package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.StallApplication;
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

import java.time.LocalDate;
import java.util.ArrayList;

public class ManagerApplyStallController {
    @FXML private ComboBox<String> stallTypeComboBox;
    @FXML private ComboBox<String> preferredZoneComboBox;
    @FXML private TextField tradeLicenseTextField;
    @FXML private TextField documentFileTextField;
    @FXML private Label statusLabel;
    @FXML private TableView<StallApplication> applicationTableView;
    @FXML private TableColumn<StallApplication, String> appIdCol;
    @FXML private TableColumn<StallApplication, String> publisherCol;
    @FXML private TableColumn<StallApplication, String> typeCol;
    @FXML private TableColumn<StallApplication, String> licenseCol;
    @FXML private TableColumn<StallApplication, String> statusCol;
    @FXML private TableColumn<StallApplication, LocalDate> dateCol;

    public void initialize() {
        stallTypeComboBox.getItems().addAll("Single", "Double", "Pavilion");
        stallTypeComboBox.setValue("Double");
        preferredZoneComboBox.getItems().addAll("Zone A - Bangla Academy", "Zone B - Suhrawardy Main", "Zone C - Children Corner");
        preferredZoneComboBox.setValue("Zone B - Suhrawardy Main");

        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        tradeLicenseTextField.setText(manager.getTradeLicenseNo());
        documentFileTextField.setText("trade_license.pdf");

        appIdCol.setCellValueFactory(new PropertyValueFactory<StallApplication, String>("applicationID"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<StallApplication, String>("publisherName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<StallApplication, String>("stallType"));
        licenseCol.setCellValueFactory(new PropertyValueFactory<StallApplication, String>("tradeLicenseNo"));
        statusCol.setCellValueFactory(new PropertyValueFactory<StallApplication, String>("status"));
        dateCol.setCellValueFactory(new PropertyValueFactory<StallApplication, LocalDate>("submittedDate"));

        loadApplications();
    }

    private void loadApplications() {
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        BinaryFileHelper<StallApplication> appFile = new BinaryFileHelper<StallApplication>("StallApplications.bin");
        ArrayList<StallApplication> list = appFile.readAll();
        applicationTableView.getItems().clear();
        for (StallApplication app : list) {
            if (app.getManagerID().equals(manager.getManagerID())) {
                applicationTableView.getItems().add(app);
            }
        }
    }

    @FXML
    public void submitApplicationButtonOnAction(ActionEvent event) {
        String license = tradeLicenseTextField.getText();
        String docFile = documentFileTextField.getText();
        if (docFile == null || (!docFile.endsWith(".pdf") && !docFile.endsWith(".jpg"))) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Document", "Document filename must end with .pdf or .jpg.");
            return;
        }
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        StallApplication app = manager.applyStallAllocation(stallTypeComboBox.getValue(),
                preferredZoneComboBox.getValue(), license);
        if (app == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid License", "Trade License must start with 'TL-' and be at least 5 characters.");
            return;
        }
        statusLabel.setText("Application submitted! Reference ID: " + app.getApplicationID() + " | Status: " + app.getStatus());
        loadApplications();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "StallManagerDashboardView.fxml", "Ekushey Boimela - Stall Manager Dashboard");
    }
}
