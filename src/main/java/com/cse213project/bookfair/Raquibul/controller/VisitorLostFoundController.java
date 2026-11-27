package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.LostFoundItem;
import com.cse213project.bookfair.Raquibul.dummy.OwnershipClaim;
import com.cse213project.bookfair.Raquibul.model.GeneralVisitor;
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

public class VisitorLostFoundController {
    @FXML private ComboBox<String> reportTypeComboBox;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextField locationTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField proofDetailsTextField;
    @FXML private Label statusLabel;
    @FXML private TableView<LostFoundItem> itemTableView;
    @FXML private TableColumn<LostFoundItem, String> itemIdCol;
    @FXML private TableColumn<LostFoundItem, String> typeCol;
    @FXML private TableColumn<LostFoundItem, String> categoryCol;
    @FXML private TableColumn<LostFoundItem, String> locationCol;
    @FXML private TableColumn<LostFoundItem, String> statusCol;

    public void initialize() {
        reportTypeComboBox.getItems().addAll("Lost Item", "Found Item");
        reportTypeComboBox.setValue("Lost Item");
        categoryComboBox.getItems().addAll("Wallet", "Phone", "Bag", "Book", "ID Card", "Other");
        categoryComboBox.setValue("Wallet");

        itemIdCol.setCellValueFactory(new PropertyValueFactory<LostFoundItem, String>("itemID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<LostFoundItem, String>("reportType"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<LostFoundItem, String>("category"));
        locationCol.setCellValueFactory(new PropertyValueFactory<LostFoundItem, String>("location"));
        statusCol.setCellValueFactory(new PropertyValueFactory<LostFoundItem, String>("status"));

        loadItems();
    }

    private void loadItems() {
        BinaryFileHelper<LostFoundItem> lfiFile = new BinaryFileHelper<LostFoundItem>("LostFoundItems.bin");
        ArrayList<LostFoundItem> items = lfiFile.readAll();
        itemTableView.getItems().clear();
        for (LostFoundItem item : items) {
            itemTableView.getItems().add(item);
        }
    }

    @FXML
    public void submitReportButtonOnAction(ActionEvent event) {
        String loc = locationTextField.getText();
        String desc = descriptionTextField.getText();
        String phone = phoneTextField.getText();

        if (loc.equals("") || desc.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Location and Description cannot be empty.");
            return;
        }
        if (phone.length() != 11) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Phone", "Phone number must be 11 digits.");
            return;
        }
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        LostFoundItem item = visitor.reportLostOrFoundItem(reportTypeComboBox.getValue(),
                categoryComboBox.getValue(), loc, desc, phone);
        statusLabel.setText("Report submitted! Item ID: " + item.getItemID() + " | Security Control Room notified.");
        loadItems();
    }

    @FXML
    public void claimSelectedItemButtonOnAction(ActionEvent event) {
        LostFoundItem selected = itemTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "No Selection", "Select an item from the table to claim.");
            return;
        }
        String proof = proofDetailsTextField.getText();
        if (proof.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Missing Proof", "Please enter proof of ownership details.");
            return;
        }
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        OwnershipClaim claim = visitor.reportOrClaimLostItem(selected.getItemID(), proof);
        statusLabel.setText("Ownership Claim filed! Claim ID: " + claim.getClaimID() + " for Item: " + selected.getItemID());
        SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Claim Submitted", "Claim ID: " + claim.getClaimID());
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "GeneralVisitorDashboardView.fxml", "Ekushey Boimela - General Visitor Dashboard");
    }
}
