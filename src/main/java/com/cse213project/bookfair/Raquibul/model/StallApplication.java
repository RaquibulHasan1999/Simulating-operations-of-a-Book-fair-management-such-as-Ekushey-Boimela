package com.cse213project.bookfair.Raquibul.model;

import java.io.Serializable;
import java.time.LocalDate;

public class StallApplication implements Serializable {
    private String applicationID;
    private String managerID;
    private String publisherName;
    private String stallType;
    private String tradeLicenseNo;
    private String status;
    private LocalDate submittedDate;

    public StallApplication() {
        this.applicationID = "";
        this.managerID = "";
        this.publisherName = "";
        this.stallType = "Single";
        this.tradeLicenseNo = "";
        this.status = "Pending Approval";
        this.submittedDate = LocalDate.now();
    }

    public StallApplication(String applicationID, String managerID, String publisherName,
                            String stallType, String tradeLicenseNo, String status, LocalDate submittedDate) {
        this.applicationID = applicationID;
        this.managerID = managerID;
        this.publisherName = publisherName;
        this.stallType = stallType;
        this.tradeLicenseNo = tradeLicenseNo;
        this.status = status;
        this.submittedDate = submittedDate;
    }

    public boolean validateLicense() {
        if (this.tradeLicenseNo == null || this.tradeLicenseNo.length() < 5) {
            return false;
        }
        return this.tradeLicenseNo.startsWith("TL-");
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public String getApplicationID() { return this.applicationID; }
    public void setApplicationID(String applicationID) { this.applicationID = applicationID; }

    public String getManagerID() { return this.managerID; }
    public void setManagerID(String managerID) { this.managerID = managerID; }

    public String getPublisherName() { return this.publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public String getStallType() { return this.stallType; }
    public void setStallType(String stallType) { this.stallType = stallType; }

    public String getTradeLicenseNo() { return this.tradeLicenseNo; }
    public void setTradeLicenseNo(String tradeLicenseNo) { this.tradeLicenseNo = tradeLicenseNo; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getSubmittedDate() { return this.submittedDate; }
    public void setSubmittedDate(LocalDate submittedDate) { this.submittedDate = submittedDate; }

    @Override
    public String toString() {
        return "StallApplication [ID=" + this.applicationID + ", Publisher=" + this.publisherName
                + ", Type=" + this.stallType + ", Status=" + this.status + "]";
    }
}
