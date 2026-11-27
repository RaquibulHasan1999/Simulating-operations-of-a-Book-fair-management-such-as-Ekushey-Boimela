package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.LaunchRequest;
import com.cse213project.bookfair.Raquibul.model.StallManager;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class ManagerBookLaunchController {
    @FXML private TextField bookTitleTextField;
    @FXML private TextField guestAuthorTextField;
    @FXML private DatePicker preferredDatePicker;
    @FXML private ComboBox<String> timeSlotComboBox;
    @FXML private Label statusLabel;
    @FXML private TableView<LaunchRequest> launchTableView;
    @FXML private TableColumn<LaunchRequest, String> reqIdCol;
    @FXML private TableColumn<LaunchRequest, String> titleCol;
    @FXML private TableColumn<LaunchRequest, LocalDate> dateCol;
    @FXML private TableColumn<LaunchRequest, String> slotCol;
    @FXML private TableColumn<LaunchRequest, String> statusCol;

    public void initialize() {
        timeSlotComboBox.getItems().addAll("11:00 AM - 12:30 PM", "03:00 PM - 04:30 PM", "05:30 PM - 07:00 PM");
        timeSlotComboBox.setValue("03:00 PM - 04:30 PM");
        preferredDatePicker.setValue(LocalDate.of(2026, 2, 15));

        reqIdCol.setCellValueFactory(new PropertyValueFactory<LaunchRequest, String>("requestId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<LaunchRequest, String>("bookTitle"));
        dateCol.setCellValueFactory(new PropertyValueFactory<LaunchRequest, LocalDate>("requestedTime"));
        slotCol.setCellValueFactory(new PropertyValueFactory<LaunchRequest, String>("timeSlot"));
        statusCol.setCellValueFactory(new PropertyValueFactory<LaunchRequest, String>("approvalStatus"));

        loadLaunchRequests();
    }

    private void loadLaunchRequests() {
        BinaryFileHelper<LaunchRequest> launchFile = new BinaryFileHelper<LaunchRequest>("LaunchRequests.bin");
        ArrayList<LaunchRequest> list = launchFile.readAll();
        launchTableView.getItems().clear();
        for (LaunchRequest lr : list) {
            launchTableView.getItems().add(lr);
        }
    }

    @FXML
    public void submitLaunchRequestButtonOnAction(ActionEvent event) {
        String title = bookTitleTextField.getText();
        String guest = guestAuthorTextField.getText();
        LocalDate date = preferredDatePicker.getValue();

        if (title.equals("") || guest.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Book Title and Guest/Author name are required.");
            return;
        }
        LocalDate fairStart = LocalDate.of(2026, 2, 1);
        LocalDate fairEnd = LocalDate.of(2026, 2, 28);
        if (date == null || date.isBefore(fairStart) || fairEnd.isBefore(date)) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Fair Date", "Book launch date must be within Ekushey Boimela dates (Feb 1 - Feb 28, 2026).");
            return;
        }

        StallManager manager = (StallManager) SessionManager.getCurrentUser();
        LaunchRequest req = manager.organizeBookLaunch(title + " (Guest: " + guest + ")", date, timeSlotComboBox.getValue());
        if (req == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Slot Conflict", "Selected stage time slot on " + date + " is already booked.");
            return;
        }
        statusLabel.setText("Launch Request " + req.getRequestId() + " stored for Fair Committee review.");
        bookTitleTextField.setText("");
        guestAuthorTextField.setText("");
        loadLaunchRequests();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "StallManagerDashboardView.fxml", "Ekushey Boimela - Stall Manager Dashboard");
    }
}
