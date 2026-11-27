package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;

// Dummy model class for User 6 (Lost and Found Officer - Ankur Das)
public class LostFoundItem implements Serializable {
    private String itemID;
    private String reportType;
    private String category;
    private String description;
    private String location;
    private String reportedDate;
    private String reportedByUserID;
    private String status;
    private String contactPhone;

    public LostFoundItem() {
        this.itemID = "";
        this.reportType = "Lost Item";
        this.category = "";
        this.description = "";
        this.location = "";
        this.reportedDate = "";
        this.reportedByUserID = "";
        this.status = "Open";
        this.contactPhone = "";
    }

    public LostFoundItem(String itemID, String reportType, String category, String description,
                         String location, String reportedDate, String reportedByUserID,
                         String status, String contactPhone) {
        this.itemID = itemID;
        this.reportType = reportType;
        this.category = category;
        this.description = description;
        this.location = location;
        this.reportedDate = reportedDate;
        this.reportedByUserID = reportedByUserID;
        this.status = status;
        this.contactPhone = contactPhone;
    }

    public String getItemID() { return this.itemID; }
    public void setItemID(String itemID) { this.itemID = itemID; }

    public String getReportType() { return this.reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return this.location; }
    public void setLocation(String location) { this.location = location; }

    public String getReportedDate() { return this.reportedDate; }
    public void setReportedDate(String reportedDate) { this.reportedDate = reportedDate; }

    public String getReportedByUserID() { return this.reportedByUserID; }
    public void setReportedByUserID(String reportedByUserID) { this.reportedByUserID = reportedByUserID; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    public String getContactPhone() { return this.contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    @Override
    public String toString() {
        return "LostFoundItem [ID=" + this.itemID + ", Type=" + this.reportType
                + ", Category=" + this.category + ", Location=" + this.location + ", Status=" + this.status + "]";
    }
}
