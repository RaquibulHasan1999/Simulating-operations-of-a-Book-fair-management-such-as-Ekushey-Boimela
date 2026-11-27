package com.cse213project.bookfair.Raquibul.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Review implements Serializable {
    private String reviewID;
    private String visitorID;
    private String targetID;
    private String targetType;
    private int rating;
    private String commentText;
    private LocalDate createdAt;
    private String status;

    public Review() {
        this.reviewID = "";
        this.visitorID = "";
        this.targetID = "";
        this.targetType = "Book";
        this.rating = 5;
        this.commentText = "";
        this.createdAt = LocalDate.now();
        this.status = "Published";
    }

    public Review(String reviewID, String visitorID, String targetID, String targetType,
                  int rating, String commentText, LocalDate createdAt, String status) {
        this.reviewID = reviewID;
        this.visitorID = visitorID;
        this.targetID = targetID;
        this.targetType = targetType;
        this.rating = rating;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.status = status;
    }

    public boolean validateRating() {
        return this.rating >= 1 && this.rating <= 5;
    }

    public void flagInappropriate() {
        this.status = "Flagged";
    }

    public String getReviewID() { return this.reviewID; }
    public void setReviewID(String reviewID) { this.reviewID = reviewID; }

    public String getVisitorID() { return this.visitorID; }
    public void setVisitorID(String visitorID) { this.visitorID = visitorID; }

    public String getTargetID() { return this.targetID; }
    public void setTargetID(String targetID) { this.targetID = targetID; }

    public String getTargetType() { return this.targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }

    public int getRating() { return this.rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getCommentText() { return this.commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }

    public LocalDate getCreatedAt() { return this.createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Review [ID=" + this.reviewID + ", Target=" + this.targetID
                + ", Rating=" + this.rating + "/5, Comment=" + this.commentText + "]";
    }
}
