package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.StallManager;
import com.cse213project.bookfair.Raquibul.model.StallSalesSummary;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class ManagerFinancialAnalyticsController {
    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;
    @FXML private Label summaryOutputLabel;
    @FXML private TableView<StallSalesSummary> summaryTableView;
    @FXML private TableColumn<StallSalesSummary, String> sumIdCol;
    @FXML private TableColumn<StallSalesSummary, LocalDate> fromCol;
    @FXML private TableColumn<StallSalesSummary, LocalDate> toCol;
    @FXML private TableColumn<StallSalesSummary, Double> counterCol;
    @FXML private TableColumn<StallSalesSummary, Double> preOrderCol;
    @FXML private TableColumn<StallSalesSummary, Integer> unitsCol;

    public void initialize() {
        fromDatePicker.setValue(LocalDate.of(2026, 2, 1));
        toDatePicker.setValue(LocalDate.now());

        sumIdCol.setCellValueFactory(new PropertyValueFactory<StallSalesSummary, String>("summaryID"));
        fromCol.setCellValueFactory(new PropertyValueFactory<StallSalesSummary, LocalDate>("fromDate"));
        toCol.setCellValueFactory(new PropertyValueFactory<StallSalesSummary, LocalDate>("toDate"));
        counterCol.setCellValueFactory(new PropertyValueFactory<StallSalesSummary, Double>("totalCounterSales"));
        preOrderCol.setCellValueFactory(new PropertyValueFactory<StallSalesSummary, Double>("totalPreOrderSales"));
        unitsCol.setCellValueFactory(new PropertyValueFactory<StallSalesSummary, Integer>("unitsSold"));

        loadSummaries();
    }

    private void loadSummaries() {
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        BinaryFileHelper<StallSalesSummary> sumFile = new BinaryFileHelper<StallSalesSummary>("StallSalesSummaries.bin");
        ArrayList<StallSalesSummary> list = sumFile.readAll();
        summaryTableView.getItems().clear();
        for (StallSalesSummary s : list) {
            if (s.getStallNo().equals(manager.getAssignedStallNo())) {
                summaryTableView.getItems().add(s);
            }
        }
    }

    @FXML
    public void generateAnalyticsButtonOnAction(ActionEvent event) {
        LocalDate from = fromDatePicker.getValue();
        LocalDate to = toDatePicker.getValue();
        if (from == null || to == null || to.isBefore(from)) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Date Range", "From Date must be on or before To Date.");
            return;
        }
        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        StallSalesSummary summary = manager.viewFinancialAnalytics(from, to);
        summaryOutputLabel.setText("Summary " + summary.getSummaryID()
                + " | Counter: BDT " + summary.getTotalCounterSales()
                + " | Pre-Orders: BDT " + summary.getTotalPreOrderSales()
                + " | Net Revenue: BDT " + summary.computeNetRevenue()
                + " | Units Sold: " + summary.getUnitsSold());
        loadSummaries();
    }

    @FXML
    public void exportSummaryButtonOnAction(ActionEvent event) {
        StallSalesSummary selected = summaryTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "No Selection", "Select a summary row to preview export data.");
            return;
        }
        SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Exported Report Preview", selected.exportCSV());
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "StallManagerDashboardView.fxml", "Ekushey Boimela - Stall Manager Dashboard");
    }
}
