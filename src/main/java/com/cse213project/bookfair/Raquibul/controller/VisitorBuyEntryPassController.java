package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.Ticket;
import com.cse213project.bookfair.Raquibul.model.GeneralVisitor;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class VisitorBuyEntryPassController {
    @FXML private ComboBox<String> passTypeComboBox;
    @FXML private DatePicker visitDatePicker;
    @FXML private CheckBox reserveParkingCheckBox;
    @FXML private Label ticketOutputLabel;
    @FXML private TableView<Ticket> ticketTableView;
    @FXML private TableColumn<Ticket, String> ticketIdCol;
    @FXML private TableColumn<Ticket, String> typeCol;
    @FXML private TableColumn<Ticket, LocalDate> dateCol;
    @FXML private TableColumn<Ticket, Double> priceCol;
    @FXML private TableColumn<Ticket, String> qrCol;

    public void initialize() {
        passTypeComboBox.getItems().addAll("Regular Entry Pass", "VIP Entry Pass", "Student Pass");
        passTypeComboBox.setValue("Regular Entry Pass");
        visitDatePicker.setValue(LocalDate.now());

        ticketIdCol.setCellValueFactory(new PropertyValueFactory<Ticket, String>("ticketID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Ticket, String>("type"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Ticket, LocalDate>("date"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Ticket, Double>("price"));
        qrCol.setCellValueFactory(new PropertyValueFactory<Ticket, String>("qrCode"));

        loadTickets();
    }

    private void loadTickets() {
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        BinaryFileHelper<Ticket> ticketFile = new BinaryFileHelper<Ticket>("Tickets.bin");
        ArrayList<Ticket> allTickets = ticketFile.readAll();
        ticketTableView.getItems().clear();
        for (Ticket t : allTickets) {
            if (t.getVisitorID().equals(visitor.getVisitorID())) {
                ticketTableView.getItems().add(t);
            }
        }
    }

    @FXML
    public void buyPassButtonOnAction(ActionEvent event) {
        LocalDate visitDate = visitDatePicker.getValue();
        if (visitDate == null || visitDate.isBefore(LocalDate.now())) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Date", "Please choose today or a future visit date.");
            return;
        }
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        Ticket ticket = visitor.buyEntryPass(passTypeComboBox.getValue(), visitDate, reserveParkingCheckBox.isSelected());
        ticketOutputLabel.setText("E-Ticket Generated! ID: " + ticket.getTicketID()
                + " | Total Paid: BDT " + ticket.getPrice() + " | QR: " + ticket.getQrCode());
        SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Ticket Purchased",
                "Your entry pass QR code is: " + ticket.getQrCode());
        loadTickets();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "GeneralVisitorDashboardView.fxml", "Ekushey Boimela - General Visitor Dashboard");
    }
}
