package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;


public class Feedback implements Serializable {
    private String feedbackID;
    private String visitorID;
    private String category;
    private int rating;
    private String comment;

    public Feedback() {
        this.feedbackID = "";
        this.visitorID = "";
        this.category = "";
        this.rating = 5;
        this.comment = "";
    }

    public Feedback(String feedbackID, String visitorID, String category, int rating, String comment) {
        this.feedbackID = feedbackID;
        this.visitorID = visitorID;
        this.category = category;
        this.rating = rating;
        this.comment = comment;
    }

    public String getFeedbackID() { return this.feedbackID; }
    public void setFeedbackID(String feedbackID) { this.feedbackID = feedbackID; }

    public String getVisitorID() { return this.visitorID; }
    public void setVisitorID(String visitorID) { this.visitorID = visitorID; }

    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }

    public int getRating() { return this.rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return this.comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Feedback [ID=" + this.feedbackID + ", Category=" + this.category
                + ", Rating=" + this.rating + ", Comment=" + this.comment + "]";
    }
}
