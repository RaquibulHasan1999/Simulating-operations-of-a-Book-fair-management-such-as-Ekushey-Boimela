package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.User;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GeneralVisitorDashboardController {
    @FXML private Label welcomeLabel;

    public void initialize() {
        User u = SessionManager.getCurrentUser();
        if (u != null) {
            welcomeLabel.setText("Welcome General Visitor: " + u.getName() + " (" + u.getUserID() + ")");
        }
    }

    @FXML public void openSearchBooksOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "VisitorSearchBooksView.fxml", "Goal 1: Search Books & Stalls");
    }

    @FXML public void openPreOrderBookOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "VisitorPreOrderBookView.fxml", "Goal 2: Pre-Order Books");
    }

    @FXML public void openRegisterEventOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "VisitorRegisterEventView.fxml", "Goal 3: Register for Events");
    }

    @FXML public void openSubmitReviewOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "VisitorSubmitReviewView.fxml", "Goal 4: Submit Book Review");
    }

    @FXML public void openBuyEntryPassOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "VisitorBuyEntryPassView.fxml", "Goal 5: Buy Entry & Parking Pass");
    }

    @FXML public void openLostFoundOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "VisitorLostFoundView.fxml", "Goal 6: Lost & Found Portal");
    }

    @FXML public void openApplyVoucherOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "VisitorApplyVoucherView.fxml", "Goal 7: Apply Promo Voucher");
    }

    @FXML public void openFeedbackHelplineOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "VisitorFeedbackHelplineView.fxml", "Goal 8: Feedback & Emergency Helpline");
    }

    @FXML public void signOutButtonOnAction(ActionEvent event) {
        User u = SessionManager.getCurrentUser();
        if (u != null) {
            u.signOut();
        }
        SessionManager.clearSession();
        SceneSwitcher.switchScene(event, "LoginView.fxml", "Ekushey Boimela Management System - Login");
    }
}
