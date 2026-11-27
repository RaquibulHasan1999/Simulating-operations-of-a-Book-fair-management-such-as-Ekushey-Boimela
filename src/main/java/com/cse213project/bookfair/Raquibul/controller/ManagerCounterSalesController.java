package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.Book;
import com.cse213project.bookfair.Raquibul.model.CounterSale;
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

public class ManagerCounterSalesController {
    @FXML private TextField isbnTextField;
    @FXML private TextField qtyTextField;
    @FXML private ComboBox<String> paymentMethodComboBox;
    @FXML private TextField preOrderIdTextField;
    @FXML private TextField pickupCodeTextField;
    @FXML private Label receiptLabel;
    @FXML private TableView<CounterSale> salesTableView;
    @FXML private TableColumn<CounterSale, String> saleIdCol;
    @FXML private TableColumn<CounterSale, String> isbnCol;
    @FXML private TableColumn<CounterSale, Integer> qtyCol;
    @FXML private TableColumn<CounterSale, Double> totalCol;
    @FXML private TableColumn<CounterSale, String> paymentCol;
    @FXML private TableColumn<CounterSale, String> dateCol;

    public void initialize() {
        paymentMethodComboBox.getItems().addAll("Cash", "bKash", "Nagad", "Card");
        paymentMethodComboBox.setValue("Cash");

        saleIdCol.setCellValueFactory(new PropertyValueFactory<CounterSale, String>("saleID"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<CounterSale, String>("isbn"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<CounterSale, Integer>("quantity"));
        totalCol.setCellValueFactory(new PropertyValueFactory<CounterSale, Double>("totalAmount"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<CounterSale, String>("paymentMethod"));
        dateCol.setCellValueFactory(new PropertyValueFactory<CounterSale, String>("saleTimestamp"));

        loadSales();
    }

    private void loadSales() {
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        BinaryFileHelper<CounterSale> saleFile = new BinaryFileHelper<CounterSale>("CounterSales.bin");
        ArrayList<CounterSale> list = saleFile.readAll();
        salesTableView.getItems().clear();
        for (CounterSale cs : list) {
            if (cs.getStallNo().equals(manager.getAssignedStallNo())) {
                salesTableView.getItems().add(cs);
            }
        }
    }

    @FXML
    public void recordSaleButtonOnAction(ActionEvent event) {
        String isbn = isbnTextField.getText();
        int qty = 0;
        try {
            qty = Integer.parseInt(qtyTextField.getText());
        } catch (NumberFormatException e) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be an integer.");
            return;
        }
        if (isbn.equals("") || qty <= 0) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Provide valid ISBN and quantity > 0.");
            return;
        }

        BinaryFileHelper<Book> bookFile = new BinaryFileHelper<Book>("Books.bin");
        ArrayList<Book> books = bookFile.readAll();
        double unitPrice = -1.0;
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                unitPrice = b.getPrice();
            }
        }
        if (unitPrice < 0) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Book Not Found", "ISBN not found in catalog.");
            return;
        }

        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        CounterSale sale = manager.recordCounterSales(isbn, qty, unitPrice, paymentMethodComboBox.getValue());
        if (sale == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Insufficient Stock", "Not enough physical stock in your stall.");
            return;
        }

        receiptLabel.setText("Sale " + sale.getSaleID() + " recorded! Net Bill (25% Fair Discount): BDT " + sale.getTotalAmount());
        SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Digital E-Receipt", sale.generateReceipt());
        loadSales();
    }

    @FXML
    public void fulfillPreOrderButtonOnAction(ActionEvent event) {
        String orderId = preOrderIdTextField.getText();
        String code = pickupCodeTextField.getText();
        if (orderId.equals("") || code.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Missing Data", "Enter both Pre-Order ID and Pickup Code.");
            return;
        }
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        boolean done = manager.fulfillPreOrder(orderId, code);
        if (done) {
            receiptLabel.setText("Pre-Order " + orderId + " verified and marked Fulfilled!");
            SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Pre-Order Fulfilled", "Order " + orderId + " handed over.");
        } else {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Verification Failed", "Invalid Order ID / Pickup Code or already fulfilled.");
        }
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "StallManagerDashboardView.fxml", "Ekushey Boimela - Stall Manager Dashboard");
    }
}
