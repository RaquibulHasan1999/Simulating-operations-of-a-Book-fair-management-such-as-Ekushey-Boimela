package com.cse213project.bookfair.Raquibul.controller;

import com.cse213project.bookfair.Raquibul.model.GeneralVisitor;
import com.cse213project.bookfair.Raquibul.model.PreOrder;
import com.cse213project.bookfair.Raquibul.model.Review;
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

public class VisitorSubmitReviewController {
    @FXML private ComboBox<String> fulfilledBookComboBox;
    @FXML private ComboBox<Integer> ratingComboBox;
    @FXML private TextField reviewTextField;
    @FXML private Label averageRatingLabel;
    @FXML private TableView<Review> reviewTableView;
    @FXML private TableColumn<Review, String> reviewIdCol;
    @FXML private TableColumn<Review, String> targetIdCol;
    @FXML private TableColumn<Review, Integer> ratingCol;
    @FXML private TableColumn<Review, String> commentCol;

    public void initialize() {
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingComboBox.setValue(5);

        reviewIdCol.setCellValueFactory(new PropertyValueFactory<Review, String>("reviewID"));
        targetIdCol.setCellValueFactory(new PropertyValueFactory<Review, String>("targetID"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        commentCol.setCellValueFactory(new PropertyValueFactory<Review, String>("commentText"));

        loadFulfilledBooks();
        loadReviews();
    }

    private void loadFulfilledBooks() {
        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        ArrayList<PreOrder> orders = poFile.readAll();
        fulfilledBookComboBox.getItems().clear();
        for (PreOrder po : orders) {
            if (po.getVisitorID().equals(visitor.getVisitorID()) && po.getStatus().equals("Fulfilled")) {
                fulfilledBookComboBox.getItems().add(po.getIsbn());
            }
        }
        if (fulfilledBookComboBox.getItems().size() > 0) {
            fulfilledBookComboBox.setValue(fulfilledBookComboBox.getItems().get(0));
        }
    }

    private void loadReviews() {
        BinaryFileHelper<Review> revFile = new BinaryFileHelper<Review>("Reviews.bin");
        ArrayList<Review> list = revFile.readAll();
        reviewTableView.getItems().clear();
        for (Review r : list) {
            reviewTableView.getItems().add(r);
        }
    }

    @FXML
    public void submitReviewButtonOnAction(ActionEvent event) {
        String bookIsbn = fulfilledBookComboBox.getValue();
        if (bookIsbn == null || bookIsbn.equals("")) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Not Eligible", "You have no fulfilled book purchases to review.");
            return;
        }
        String comment = reviewTextField.getText();
        if (comment == null || comment.equals("") || comment.length() > 500) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Invalid Review", "Review must be between 1 and 500 characters.");
            return;
        }

        GeneralVisitor visitor = (GeneralVisitor) SessionManager.getCurrentUser();
        Review rev = visitor.submitReview(bookIsbn, "Book", ratingComboBox.getValue(), comment);
        if (rev == null) {
            SceneSwitcher.showAlert(Alert.AlertType.ERROR, "Error", "Rating must be between 1 and 5.");
            return;
        }

        loadReviews();
        int count = 0;
        int sum = 0;
        for (Review r : reviewTableView.getItems()) {
            if (r.getTargetID().equals(bookIsbn)) {
                sum = sum + r.getRating();
                count = count + 1;
            }
        }
        double avg = 0.0;
        if (count > 0) {
            avg = (double) sum / count;
        }
        averageRatingLabel.setText("Review saved! Updated Average Rating for " + bookIsbn + ": " + avg + " / 5.0");
        reviewTextField.setText("");
    }

    @FXML
    public void backButtonOnAction(ActionEvent event) {
        SceneSwitcher.switchScene(event, "GeneralVisitorDashboardView.fxml", "Ekushey Boimela - General Visitor Dashboard");
    }
}
