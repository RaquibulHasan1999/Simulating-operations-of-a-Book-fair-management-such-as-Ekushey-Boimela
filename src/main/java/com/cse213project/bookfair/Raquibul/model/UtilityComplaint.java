package com.cse213project.bookfair.Raquibul.model;

import com.cse213project.bookfair.Raquibul.dummy.InspectionReport;
import java.io.Serializable;
import java.util.Random;

public class UtilityComplaint implements Serializable {
    private String complaintID;
    private String stallNo;
    private String damageType;
    private String description;
    private String priority;
    private String status;
    private String loggedAt;

    public UtilityComplaint() {
        this.complaintID = "";
        this.stallNo = "";
        this.damageType = "";
        this.description = "";
        this.priority = "Medium";
        this.status = "Open";
        this.loggedAt = "";
    }

    public UtilityComplaint(String complaintID, String stallNo, String damageType,
                            String description, String priority, String status, String loggedAt) {
        this.complaintID = complaintID;
        this.stallNo = stallNo;
        this.damageType = damageType;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.loggedAt = loggedAt;
    }

    public InspectionReport escalateToMaintenance() {
        Random r = new Random();
        String repId = "IR-" + (1000 + r.nextInt(9000));
        this.status = "Escalated to Maintenance";
        return new InspectionReport(repId, this.stallNo, this.damageType, "Pending Photo", this.priority, "Open");
    }

    public String getComplaintID() { return this.complaintID; }
    public void setComplaintID(String complaintID) { this.complaintID = complaintID; }

    public String getStallNo() { return this.stallNo; }
    public void setStallNo(String stallNo) { this.stallNo = stallNo; }

    public String getDamageType() { return this.damageType; }
    public void setDamageType(String damageType) { this.damageType = damageType; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return this.priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    public String getLoggedAt() { return this.loggedAt; }
    public void setLoggedAt(String loggedAt) { this.loggedAt = loggedAt; }

    @Override
    public String toString() {
        return "UtilityComplaint [ID=" + this.complaintID + ", Stall=" + this.stallNo
                + ", Type=" + this.damageType + ", Priority=" + this.priority + ", Status=" + this.status + "]";
    }
}
