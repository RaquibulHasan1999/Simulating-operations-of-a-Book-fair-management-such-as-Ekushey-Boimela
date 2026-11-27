package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.dummy.DummyOtherUser;
import com.cse213project.bookfair.Raquibul.model.User;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DummyUserDashboardController {
    @FXML private Label titleLabel;
    @FXML private Label infoLabel;

    public void initialize() {
        User u = SessionManager.getCurrentUser();
        if (u != null) {
            titleLabel.setText("Dummy Dashboard - " + u.getRole());
            if (u instanceof DummyOtherUser) {
                DummyOtherUser dummy = (DummyOtherUser) u;
                infoLabel.setText("This module is assigned to: " + dummy.getMemberOwnerName()
                        + ". Dummy placeholder loaded for Group 39 integration.");
            } else {
                infoLabel.setText("Dummy placeholder dashboard for other group member.");
            }
        }
    }

    @FXML
    public void signOutButtonOnAction(ActionEvent event) {
        User u = SessionManager.getCurrentUser();
        if (u != null) {
            u.signOut();
        }
        SessionManager.clearSession();
        SceneSwitcher.switchScene(event, "LoginView.fxml", "Ekushey Boimela Management System - Login");
    }
}
