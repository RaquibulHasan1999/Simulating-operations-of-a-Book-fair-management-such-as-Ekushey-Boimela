package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;

// Dummy model class for User 4 (Maintenance Officer - Erin Jahan Shovra)
public class InspectionReport implements Serializable {
    private String reportId;
    private String stallId;
    private String damageType;
    private String photoProof;
    private String priorityLevel;
    private String status;

    public InspectionReport() {
        this.reportId = "";
        this.stallId = "";
        this.damageType = "";
        this.photoProof = "N/A";
        this.priorityLevel = "Medium";
        this.status = "Logged";
    }

    public InspectionReport(String reportId, String stallId, String damageType, String photoProof, String priorityLevel, String status) {
        this.reportId = reportId;
        this.stallId = stallId;
        this.damageType = damageType;
        this.photoProof = photoProof;
        this.priorityLevel = priorityLevel;
        this.status = status;
    }

    public String getReportId() { return this.reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getStallId() { return this.stallId; }
    public void setStallId(String stallId) { this.stallId = stallId; }

    public String getDamageType() { return this.damageType; }
    public void setDamageType(String damageType) { this.damageType = damageType; }

    public String getPhotoProof() { return this.photoProof; }
    public void setPhotoProof(String photoProof) { this.photoProof = photoProof; }

    public String getPriorityLevel() { return this.priorityLevel; }
    public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "InspectionReport [ID=" + this.reportId + ", Stall=" + this.stallId
                + ", Damage=" + this.damageType + ", Priority=" + this.priorityLevel + "]";
    }
}
