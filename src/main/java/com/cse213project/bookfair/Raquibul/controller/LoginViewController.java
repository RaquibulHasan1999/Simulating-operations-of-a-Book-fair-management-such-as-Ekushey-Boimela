package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.User;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import com.cse213project.bookfair.Raquibul.util.SceneSwitcher;
import com.cse213project.bookfair.Raquibul.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class LoginViewController {
    @FXML private TextField userIdTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label statusLabel;

    public void initialize() {
        statusLabel.setText("Demo IDs: V101 (Visitor), M201 (Stall Manager), E301 (Dummy) | PW: 1234");
    }

    private boolean isValidInput() {
        String id = userIdTextField.getText();
        String pw = passwordTextField.getText();
        if (id == null || id.equals("") || id.length() < 3) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Validation Error", "User ID must be at least 3 characters.");
            return false;
        }
        if (pw == null || pw.equals("") || pw.length() < 4) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Validation Error", "Password must be at least 4 characters.");
            return false;
        }
        return true;
    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        if (!isValidInput()) {
            return;
        }
        String id = userIdTextField.getText();
        String pw = passwordTextField.getText();

        BinaryFileHelper<User> userFile = new BinaryFileHelper<User>("Users.bin");
        ArrayList<User> users = userFile.readAll();

        User authenticated = null;
        for (User u : users) {
            if (u.login(id, pw)) {
                authenticated = u;
            }
        }

        if (authenticated == null) {
            statusLabel.setText("Invalid User ID or Password.");
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid User ID or Password.");
            return;
        }

        SessionManager.setCurrentUser(authenticated);
        SceneSwitcher.switchScene(event, authenticated.getDashboardFxml(),
                "Ekushey Boimela - " + authenticated.getRole() + " Dashboard");
    }
}
