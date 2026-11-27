package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.ReplenishmentRequest;
import com.cse213project.bookfair.Raquibul.dummy.StallInventory;
import com.cse213project.bookfair.Raquibul.model.StallManager;
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

public class ManagerReplenishmentController {
    @FXML private TextField isbnTextField;
    @FXML private TextField qtyTextField;
    @FXML private Label lowStockLabel;
    @FXML private TableView<ReplenishmentRequest> requestTableView;
    @FXML private TableColumn<ReplenishmentRequest, String> reqIdCol;
    @FXML private TableColumn<ReplenishmentRequest, String> stallCol;
    @FXML private TableColumn<ReplenishmentRequest, String> isbnCol;
    @FXML private TableColumn<ReplenishmentRequest, Integer> qtyCol;
    @FXML private TableColumn<ReplenishmentRequest, String> statusCol;

    public void initialize() {
        reqIdCol.setCellValueFactory(new PropertyValueFactory<ReplenishmentRequest, String>("requestID"));
        stallCol.setCellValueFactory(new PropertyValueFactory<ReplenishmentRequest, String>("stallNo"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<ReplenishmentRequest, String>("isbn"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<ReplenishmentRequest, Integer>("requestedQty"));
        statusCol.setCellValueFactory(new PropertyValueFactory<ReplenishmentRequest, String>("status"));

        showLowStockAlert();
        loadRequests();
    }

    private void showLowStockAlert() {
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        BinaryFileHelper<StallInventory> invFile = new BinaryFileHelper<StallInventory>("StallInventory.bin");
        ArrayList<StallInventory> list = invFile.readAll();
        String lowText = "Low-stock ISBNs in " + manager.getAssignedStallNo() + ": ";
        boolean foundLow = false;
        for (StallInventory inv : list) {
            if (inv.getStallNo().equals(manager.getAssignedStallNo()) && inv.getAvailableQty() <= inv.getReorderLevel()) {
                lowText = lowText + inv.getIsbn() + " (Qty: " + inv.getAvailableQty() + ")  ";
                foundLow = true;
            }
        }
        if (!foundLow) {
            lowText = "All books in " + manager.getAssignedStallNo() + " are above reorder level.";
        }
        lowStockLabel.setText(lowText);
    }

    private void loadRequests() {
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        BinaryFileHelper<ReplenishmentRequest> reqFile = new BinaryFileHelper<ReplenishmentRequest>("ReplenishmentRequests.bin");
        ArrayList<ReplenishmentRequest> list = reqFile.readAll();
        requestTableView.getItems().clear();
        for (ReplenishmentRequest req : list) {
            if (req.getStallNo().equals(manager.getAssignedStallNo())) {
                requestTableView.getItems().add(req);
            }
        }
    }

    @FXML
    public void dispatchRequisitionButtonOnAction(ActionEvent event) {
        String isbn = isbnTextField.getText();
        if (isbn.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid ISBN", "Please enter the Book ISBN.");
            return;
        }
        int qty = 0;
        try {
            qty = Integer.parseInt(qtyTextField.getText());
        } catch (NumberFormatException e) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Requested quantity must be a positive integer.");
            return;
        }
        if (qty <= 0) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be greater than 0.");
            return;
        }

        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        ReplenishmentRequest req = manager.requestReplenishment(isbn, qty);
        if (req == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Warehouse Limit", "Central warehouse max per requisition is 500 copies.");
            return;
        }
        SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Requisition Dispatched",
                "Ticket " + req.getRequestID() + " dispatched to Central Warehouse.");
        loadRequests();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "StallManagerDashboardView.fxml", "Ekushey Boimela - Stall Manager Dashboard");
    }
}
