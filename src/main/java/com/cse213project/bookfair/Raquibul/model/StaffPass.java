package com.cse213project.bookfair.Raquibul.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

public class StaffPass implements Serializable {
    private String passID;
    private String stallNo;
    private String staffName;
    private String staffRole;
    private LocalDate validDate;
    private String qrCode;
    private boolean isActive;

    public StaffPass() {
        this.passID = "";
        this.stallNo = "";
        this.staffName = "";
        this.staffRole = "Sales Attendant";
        this.validDate = LocalDate.now();
        this.qrCode = "";
        this.isActive = true;
    }

    public StaffPass(String passID, String stallNo, String staffName, String staffRole,
                     LocalDate validDate, String qrCode, boolean isActive) {
        this.passID = passID;
        this.stallNo = stallNo;
        this.staffName = staffName;
        this.staffRole = staffRole;
        this.validDate = validDate;
        this.qrCode = qrCode;
        this.isActive = isActive;
    }

    public String generateQR() {
        Random r = new Random();
        int codeNum = 1000 + r.nextInt(9000);
        this.qrCode = "QR-STAFF-" + this.stallNo + "-" + codeNum;
        return this.qrCode;
    }

    public void revokePass() {
        this.isActive = false;
    }

    public String getPassID() { return this.passID; }
    public void setPassID(String passID) { this.passID = passID; }

    public String getStallNo() { return this.stallNo; }
    public void setStallNo(String stallNo) { this.stallNo = stallNo; }

    public String getStaffName() { return this.staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getStaffRole() { return this.staffRole; }
    public void setStaffRole(String staffRole) { this.staffRole = staffRole; }

    public LocalDate getValidDate() { return this.validDate; }
    public void setValidDate(LocalDate validDate) { this.validDate = validDate; }

    public String getQrCode() { return this.qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public boolean getIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    @Override
    public String toString() {
        return "StaffPass [ID=" + this.passID + ", Name=" + this.staffName
                + ", Role=" + this.staffRole + ", Active=" + this.isActive + ", QR=" + this.qrCode + "]";
    }
}
