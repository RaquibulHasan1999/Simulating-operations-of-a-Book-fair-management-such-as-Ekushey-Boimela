package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.Book;
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

public class ManagerUpdateInventoryController {
    @FXML private TextField isbnTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField authorTextField;
    @FXML private TextField categoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField qtyChangeTextField;
    @FXML private Label statusLabel;
    @FXML private TableView<StallInventory> inventoryTableView;
    @FXML private TableColumn<StallInventory, String> stallCol;
    @FXML private TableColumn<StallInventory, String> isbnCol;
    @FXML private TableColumn<StallInventory, Integer> availCol;
    @FXML private TableColumn<StallInventory, Integer> soldCol;
    @FXML private TableColumn<StallInventory, Integer> reorderCol;

    public void initialize() {
        stallCol.setCellValueFactory(new PropertyValueFactory<StallInventory, String>("stallNo"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<StallInventory, String>("isbn"));
        availCol.setCellValueFactory(new PropertyValueFactory<StallInventory, Integer>("availableQty"));
        soldCol.setCellValueFactory(new PropertyValueFactory<StallInventory, Integer>("soldQty"));
        reorderCol.setCellValueFactory(new PropertyValueFactory<StallInventory, Integer>("reorderLevel"));
        loadInventory();
    }

    private void loadInventory() {
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        BinaryFileHelper<StallInventory> invFile = new BinaryFileHelper<StallInventory>("StallInventory.bin");
        ArrayList<StallInventory> list = invFile.readAll();
        inventoryTableView.getItems().clear();
        for (StallInventory inv : list) {
            if (inv.getStallNo().equals(manager.getAssignedStallNo())) {
                inventoryTableView.getItems().add(inv);
            }
        }
    }

    @FXML
    public void addNewBookButtonOnAction(ActionEvent event) {
        String isbn = isbnTextField.getText();
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        String cat = categoryTextField.getText();
        if (isbn.equals("") || title.equals("") || author.equals("") || cat.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Input", "ISBN, Title, Author, and Category are required.");
            return;
        }
        double price = 0.0;
        int qty = 0;
        try {
            price = Double.parseDouble(priceTextField.getText());
            qty = Integer.parseInt(qtyChangeTextField.getText());
        } catch (NumberFormatException e) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Numbers", "Price and Quantity must be valid numbers.");
            return;
        }
        if (price <= 0 || qty < 0) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Validation Error", "Price must be > 0 and Quantity must be >= 0.");
            return;
        }

        BinaryFileHelper<Book> bookFile = new BinaryFileHelper<Book>("Books.bin");
        ArrayList<Book> existingBooks = bookFile.readAll();
        for (Book b : existingBooks) {
            if (b.getIsbn().equals(isbn)) {
                SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Duplicate ISBN", "Book with ISBN " + isbn + " already exists.");
                return;
            }
        }

        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        Book newBook = new Book(isbn, title, author, cat, manager.getPublisherName(), price, true);
        bookFile.appendObject(newBook);
        manager.updateInventory(isbn, qty, price);

        statusLabel.setText("Added new book " + title + " (ISBN: " + isbn + ") with stock " + qty);
        loadInventory();
    }

    @FXML
    public void updateStockButtonOnAction(ActionEvent event) {
        String isbn = isbnTextField.getText();
        if (isbn.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Missing ISBN", "Enter ISBN to update stock.");
            return;
        }
        double price = 0.0;
        int qtyChange = 0;
        try {
            price = Double.parseDouble(priceTextField.getText());
            qtyChange = Integer.parseInt(qtyChangeTextField.getText());
        } catch (NumberFormatException e) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Numbers", "Price and Quantity Change must be numeric.");
            return;
        }
        if (price <= 0) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Price", "Price must be greater than 0.");
            return;
        }

        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        StallInventory updated = manager.updateInventory(isbn, qtyChange, price);
        statusLabel.setText("Updated ISBN " + isbn + " | Available Qty: " + updated.getAvailableQty() + " | New Price: BDT " + price);
        loadInventory();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "StallManagerDashboardView.fxml", "Ekushey Boimela - Stall Manager Dashboard");
    }
}
