package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.Feedback;
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

public class VisitorFeedbackHelplineController {
    @FXML private ComboBox<String> issueTypeComboBox;
    @FXML private ComboBox<Integer> ratingComboBox;
    @FXML private TextField zoneTextField;
    @FXML private TextField messageTextField;
    @FXML private Label dispatchLabel;
    @FXML private TableView<Feedback> feedbackTableView;
    @FXML private TableColumn<Feedback, String> fbIdCol;
    @FXML private TableColumn<Feedback, String> categoryCol;
    @FXML private TableColumn<Feedback, Integer> ratingCol;
    @FXML private TableColumn<Feedback, String> commentCol;

    public void initialize() {
        issueTypeComboBox.getItems().addAll("Feedback", "Medical", "Lost Child", "Security");
        issueTypeComboBox.setValue("Feedback");
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingComboBox.setValue(5);
        zoneTextField.setText("Zone A - Main Gate (Auto GPS)");

        fbIdCol.setCellValueFactory(new PropertyValueFactory<Feedback, String>("feedbackID"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Feedback, String>("category"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<Feedback, Integer>("rating"));
        commentCol.setCellValueFactory(new PropertyValueFactory<Feedback, String>("comment"));

        loadFeedbacks();
    }

    private void loadFeedbacks() {
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        BinaryFileHelper<Feedback> fbFile = new BinaryFileHelper<Feedback>("Feedbacks.bin");
        ArrayList<Feedback> list = fbFile.readAll();
        feedbackTableView.getItems().clear();
        for (Feedback fb : list) {
            if (fb.getVisitorID().equals(visitor.getVisitorID())) {
                feedbackTableView.getItems().add(fb);
            }
        }
    }

    @FXML
    public void submitAlertOrFeedbackButtonOnAction(ActionEvent event) {
        String msg = messageTextField.getText();
        String zone = zoneTextField.getText();
        if (msg == null || msg.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter details of your feedback or emergency.");
            return;
        }
        String issueType = issueTypeComboBox.getValue();
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        Feedback fb = visitor.submitFeedback(issueType, ratingComboBox.getValue(), "[" + zone + "] " + msg);

        if (issueType.equals("Feedback")) {
            dispatchLabel.setText("Feedback submitted! Reference ID: " + fb.getFeedbackID());
        } else {
            dispatchLabel.setText("EMERGENCY SIGNAL DISPATCHED to Security Control Desk! Ref: " + fb.getFeedbackID());
            SceneSwitcher.showAlert(Alert.AlertType.INFORMATION, "Emergency Alert Sent",
                    "Emergency signal (" + issueType + ") dispatched for " + zone);
        }
        messageTextField.setText("");
        loadFeedbacks();
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "GeneralVisitorDashboardView.fxml", "Ekushey Boimela - General Visitor Dashboard");
    }
}
