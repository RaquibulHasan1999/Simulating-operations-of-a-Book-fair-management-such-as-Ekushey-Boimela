package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;
import java.time.LocalDate;

// Dummy model class for User 3 (Event Coordinator - Erin Jahan Shovra)
public class LaunchRequest implements Serializable {
    private String requestId;
    private String publisherName;
    private String bookTitle;
    private LocalDate requestedTime;
    private String timeSlot;
    private String approvalStatus;

    public LaunchRequest() {
        this.requestId = "";
        this.publisherName = "";
        this.bookTitle = "";
        this.requestedTime = LocalDate.now();
        this.timeSlot = "";
        this.approvalStatus = "Pending";
    }

    public LaunchRequest(String requestId, String publisherName, String bookTitle, LocalDate requestedTime, String timeSlot, String approvalStatus) {
        this.requestId = requestId;
        this.publisherName = publisherName;
        this.bookTitle = bookTitle;
        this.requestedTime = requestedTime;
        this.timeSlot = timeSlot;
        this.approvalStatus = approvalStatus;
    }

    public String getRequestId() { return this.requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getPublisherName() { return this.publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public String getBookTitle() { return this.bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public LocalDate getRequestedTime() { return this.requestedTime; }
    public void setRequestedTime(LocalDate requestedTime) { this.requestedTime = requestedTime; }

    public String getTimeSlot() { return this.timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getApprovalStatus() { return this.approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    @Override
    public String toString() {
        return "LaunchRequest [ID=" + this.requestId + ", Book=" + this.bookTitle
                + ", Date=" + this.requestedTime + ", Slot=" + this.timeSlot + ", Status=" + this.approvalStatus + "]";
    }
}
