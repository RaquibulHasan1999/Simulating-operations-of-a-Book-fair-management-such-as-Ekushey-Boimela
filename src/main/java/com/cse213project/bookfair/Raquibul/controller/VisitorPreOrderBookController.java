package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.GeneralVisitor;
import com.cse213project.bookfair.Raquibul.model.PreOrder;
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

public class VisitorPreOrderBookController {
    @FXML private TextField stallNoTextField;
    @FXML private TextField isbnTextField;
    @FXML private TextField quantityTextField;
    @FXML private ComboBox<String> pickupOptionComboBox;
    @FXML private ComboBox<String> paymentMethodComboBox;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;
    @FXML private Label outputLabel;
    @FXML private TableView<PreOrder> preOrderTableView;
    @FXML private TableColumn<PreOrder, String> orderIdCol;
    @FXML private TableColumn<PreOrder, String> isbnCol;
    @FXML private TableColumn<PreOrder, Integer> qtyCol;
    @FXML private TableColumn<PreOrder, Double> totalCol;
    @FXML private TableColumn<PreOrder, String> pickupCodeCol;
    @FXML private TableColumn<PreOrder, String> statusCol;

    public void initialize() {
        pickupOptionComboBox.getItems().addAll("Express Counter", "Courier");
        pickupOptionComboBox.setValue("Express Counter");
        paymentMethodComboBox.getItems().addAll("bKash", "Nagad", "Card");
        paymentMethodComboBox.setValue("bKash");

        orderIdCol.setCellValueFactory(new PropertyValueFactory<PreOrder, String>("orderID"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<PreOrder, String>("isbn"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<PreOrder, Integer>("quantity"));
        totalCol.setCellValueFactory(new PropertyValueFactory<PreOrder, Double>("total"));
        pickupCodeCol.setCellValueFactory(new PropertyValueFactory<PreOrder, String>("pickupCode"));
        statusCol.setCellValueFactory(new PropertyValueFactory<PreOrder, String>("status"));

        refreshTable();
    }

    private void refreshTable() {
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        ArrayList<PreOrder> allOrders = poFile.readAll();
        preOrderTableView.getItems().clear();
        for (PreOrder po : allOrders) {
            if (po.getVisitorID().equals(visitor.getVisitorID())) {
                preOrderTableView.getItems().add(po);
            }
        }
    }

    @FXML
    public void confirmPreOrderButtonOnAction(ActionEvent event) {
        String stallNo = stallNoTextField.getText();
        String isbn = isbnTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();

        if (stallNo.equals("") || isbn.equals("") || address.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Stall No, ISBN, and Address are required.");
            return;
        }
        if (phone.length() != 11) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Phone", "Phone number must be 11 digits (e.g., 01711000001).");
            return;
        }
        int qty = 0;
        try {
            qty = Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException e) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be an integer.");
            return;
        }
        if (qty <= 0) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be greater than 0.");
            return;
        }

        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        PreOrder created = visitor.preOrderBook(stallNo, isbn, qty, pickupOptionComboBox.getValue());
        if (created == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Book Not Found", "Could not find book with ISBN: " + isbn);
            return;
        }

        outputLabel.setText("Confirmed! Order ID: " + created.getOrderID() + " | Total: BDT " + created.getTotal()
                + " | Pickup Token: " + created.getPickupCode() + " | Paid via " + paymentMethodComboBox.getValue());
        refreshTable();
    }

    @FXML
    public void cancelSelectedOrderButtonOnAction(ActionEvent event) {
        PreOrder selected = preOrderTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a Pre-Order to cancel.");
            return;
        }
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        boolean cancelled = visitor.cancelPreOrder(selected.getOrderID());
        if (cancelled) {
            SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Pre-Order " + selected.getOrderID() + " has been cancelled.");
            refreshTable();
        } else {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Cannot Cancel", "Fulfilled orders cannot be cancelled.");
        }
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "GeneralVisitorDashboardView.fxml", "Ekushey Boimela - General Visitor Dashboard");
    }
}
