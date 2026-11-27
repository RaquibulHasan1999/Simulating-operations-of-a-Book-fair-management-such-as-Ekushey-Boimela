package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.Book;
import com.cse213project.bookfair.Raquibul.dummy.StallInventory;
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

public class VisitorSearchBooksController {
    @FXML private TextField keywordTextField;
    @FXML private ComboBox<String> filterByComboBox;
    @FXML private TextField minPriceTextField;
    @FXML private TextField maxPriceTextField;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> isbnCol;
    @FXML private TableColumn<Book, String> titleCol;
    @FXML private TableColumn<Book, String> authorCol;
    @FXML private TableColumn<Book, String> publisherCol;
    @FXML private TableColumn<Book, Double> priceCol;
    @FXML private Label detailLabel;

    public void initialize() {
        filterByComboBox.getItems().addAll("All", "Title", "Author", "Publisher", "Category");
        filterByComboBox.setValue("All");

        isbnCol.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Book, Double>("price"));

        loadBooks("", "All", 0.0, 999999.0);
    }

    private void loadBooks(String keyword, String filterBy, double minPrice, double maxPrice) {
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        ArrayList<Book> results = visitor.searchBooksAndStalls(keyword, filterBy);
        bookTableView.getItems().clear();
        for (Book b : results) {
            if (b.getPrice() >= minPrice && b.getPrice() <= maxPrice) {
                bookTableView.getItems().add(b);
            }
        }
    }

    @FXML
    public void searchButtonOnAction(ActionEvent event) {
        double minP = 0.0;
        double maxP = 999999.0;
        try {
            if (!minPriceTextField.getText().equals("")) {
                minP = Double.parseDouble(minPriceTextField.getText());
            }
            if (!maxPriceTextField.getText().equals("")) {
                maxP = Double.parseDouble(maxPriceTextField.getText());
            }
        } catch (NumberFormatException e) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price range must be numeric.");
            return;
        }
        if (minP < 0 || maxP < 0 || minP > maxP) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Price Range", "Min price must be <= Max price and >= 0.");
            return;
        }
        loadBooks(keywordTextField.getText(), filterByComboBox.getValue(), minP, maxP);
    }

    @FXML
    public void viewDetailsButtonOnAction(ActionEvent event) {
        Book selected = bookTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a book row from the table.");
            return;
        }
        BinaryFileHelper<StallInventory> invFile = new BinaryFileHelper<StallInventory>("StallInventory.bin");
        ArrayList<StallInventory> invList = invFile.readAll();
        String stallInfo = "Stall: Not listed";
        for (StallInventory inv : invList) {
            if (inv.getIsbn().equals(selected.getIsbn())) {
                stallInfo = "Stall No: " + inv.getStallNo() + " | Stock Available: " + inv.getAvailableQty();
            }
        }
        detailLabel.setText("Selected: " + selected.getTitle() + " by " + selected.getAuthor()
                + " | Category: " + selected.getCategory() + " | Publisher: " + selected.getPublisher()
                + " | " + stallInfo);
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "GeneralVisitorDashboardView.fxml", "Ekushey Boimela - General Visitor Dashboard");
    }
}
