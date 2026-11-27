package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.Event;
import com.cse213project.bookfair.Raquibul.model.EventRegistration;
import com.cse213project.bookfair.Raquibul.model.GeneralVisitor;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class VisitorRegisterEventController {
    @FXML private TableView<Event> eventTableView;
    @FXML private TableColumn<Event, String> eventIdCol;
    @FXML private TableColumn<Event, String> titleCol;
    @FXML private TableColumn<Event, LocalDate> dateCol;
    @FXML private TableColumn<Event, String> stageCol;
    @FXML private TableColumn<Event, Integer> seatsCol;
    @FXML private Label passLabel;

    public void initialize() {
        eventIdCol.setCellValueFactory(new PropertyValueFactory<Event, String>("eventId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Event, LocalDate>("scheduledDate"));
        stageCol.setCellValueFactory(new PropertyValueFactory<Event, String>("stageLocation"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<Event, Integer>("capacity"));
        loadEvents();
    }

    private void loadEvents() {
        BinaryFileHelper<Event> eventFile = new BinaryFileHelper<Event>("Events.bin");
        ArrayList<Event> events = eventFile.readAll();
        eventTableView.getItems().clear();
        for (Event e : events) {
            eventTableView.getItems().add(e);
        }
    }

    @FXML
    public void registerEventButtonOnAction(ActionEvent event) {
        Event selected = eventTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "No Selection", "Please select an event from the table.");
            return;
        }
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        EventRegistration reg = visitor.registerEvent(selected.getEventId());
        if (reg == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Full Capacity", "No seats available for this event.");
            return;
        }
        passLabel.setText("Registered! Reg ID: " + reg.getRegistrationID()
                + " | Event: " + selected.getTitle() + " | Entry Pass QR: " + reg.getSeatPassQR());
        SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Registration Confirmed",
                "Seat confirmed! Entry QR: " + reg.getSeatPassQR());
        loadEvents();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "GeneralVisitorDashboardView.fxml", "Ekushey Boimela - General Visitor Dashboard");
    }
}
