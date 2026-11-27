package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.GeneralVisitor;
import com.cse213project.bookfair.Raquibul.model.PreOrder;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class VisitorApplyVoucherController {
    @FXML private TextField voucherCodeTextField;
    @FXML private Label resultLabel;
    @FXML private TableView<PreOrder> orderTableView;
    @FXML private TableColumn<PreOrder, String> orderIdCol;
    @FXML private TableColumn<PreOrder, String> isbnCol;
    @FXML private TableColumn<PreOrder, String> voucherCol;
    @FXML private TableColumn<PreOrder, Double> discountCol;
    @FXML private TableColumn<PreOrder, Double> totalCol;

    public void initialize() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<PreOrder, String>("orderID"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<PreOrder, String>("isbn"));
        voucherCol.setCellValueFactory(new PropertyValueFactory<PreOrder, String>("voucherCode"));
        discountCol.setCellValueFactory(new PropertyValueFactory<PreOrder, Double>("discountAmount"));
        totalCol.setCellValueFactory(new PropertyValueFactory<PreOrder, Double>("total"));
        loadOrders();
    }

    private void loadOrders() {
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        ArrayList<PreOrder> orders = poFile.readAll();
        orderTableView.getItems().clear();
        for (PreOrder po : orders) {
            if (po.getVisitorID().equals(visitor.getVisitorID()) && po.getStatus().equals("Confirmed")) {
                orderTableView.getItems().add(po);
            }
        }
    }

    @FXML
    public void applyVoucherButtonOnAction(ActionEvent event) {
        PreOrder selected = orderTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "No Order Selected", "Select an active Pre-Order from the table.");
            return;
        }
        String code = voucherCodeTextField.getText();
        if (code == null || code.length() < 5) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Format", "Promo code must be at least 5 characters.");
            return;
        }
        if (!selected.getVoucherCode().equals("NONE")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Already Applied", "A voucher is already applied to this order.");
            return;
        }

        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        double updatedTotal = visitor.applyVoucher(selected.getOrderID(), code);
        if (updatedTotal < 0) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid or Expired", "Voucher code is invalid or expired.");
            return;
        }
        resultLabel.setText("Voucher " + code + " applied! New Payable Total: BDT " + updatedTotal);
        loadOrders();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "GeneralVisitorDashboardView.fxml", "Ekushey Boimela - General Visitor Dashboard");
    }
}
