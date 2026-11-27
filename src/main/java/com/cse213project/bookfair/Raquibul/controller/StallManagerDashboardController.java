package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.StallManager;
import com.cse213project.bookfair.Raquibul.model.User;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StallManagerDashboardController {
    @FXML private Label welcomeLabel;

    public void initialize() {
        User u = SessionManager.getCurrentUser();
        if (u instanceof StallManager) {
            StallManager sm = (StallManager) u;
            welcomeLabel.setText("Stall Manager: " + sm.getName() + " | Publisher: "
                    + sm.getPublisherName() + " | Stall: " + sm.getAssignedStallNo());
        }
    }

    @FXML public void openApplyStallOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "ManagerApplyStallView.fxml", "Goal 1: Apply for Stall Allocation");
    }

    @FXML public void openUpdateInventoryOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "ManagerUpdateInventoryView.fxml", "Goal 2: Update Inventory & Catalog");
    }

    @FXML public void openCounterSalesOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "ManagerCounterSalesView.fxml", "Goal 3: Counter Sales & Pre-Order Pickup");
    }

    @FXML public void openStaffPassOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "ManagerStaffPassView.fxml", "Goal 4: Manage Staff Passes");
    }

    @FXML public void openReplenishmentOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "ManagerReplenishmentView.fxml", "Goal 5: Request Stock Replenishment");
    }

    @FXML public void openBookLaunchOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "ManagerBookLaunchView.fxml", "Goal 6: Organize Book Launch Event");
    }

    @FXML public void openFinancialAnalyticsOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "ManagerFinancialAnalyticsView.fxml", "Goal 7: Financial Analytics & Sales");
    }

    @FXML public void openUtilityComplaintOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "ManagerUtilityComplaintView.fxml", "Goal 8: Submit Utility Complaint");
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
