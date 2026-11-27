package com.cse213project.bookfair.Raquibul.model;

import java.io.Serializable;

public class Stall implements Serializable {
    private String stallNo;
    private String managerID;
    private String publisherName;
    private String stallType;
    private double rentalAmount;
    private String status;

    public Stall() {
        this.stallNo = "";
        this.managerID = "";
        this.publisherName = "";
        this.stallType = "Single";
        this.rentalAmount = 0.0;
        this.status = "Operational";
    }

    public Stall(String stallNo, String managerID, String publisherName,
                 String stallType, double rentalAmount, String status) {
        this.stallNo = stallNo;
        this.managerID = managerID;
        this.publisherName = publisherName;
        this.stallType = stallType;
        this.rentalAmount = rentalAmount;
        this.status = status;
    }

    public double getRentalAmount() {
        return this.rentalAmount;
    }

    public boolean isOperational() {
        return this.status.equals("Operational");
    }

    public String getStallNo() { return this.stallNo; }
    public void setStallNo(String stallNo) { this.stallNo = stallNo; }

    public String getManagerID() { return this.managerID; }
    public void setManagerID(String managerID) { this.managerID = managerID; }

    public String getPublisherName() { return this.publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public String getStallType() { return this.stallType; }
    public void setStallType(String stallType) { this.stallType = stallType; }

    public void setRentalAmount(double rentalAmount) { this.rentalAmount = rentalAmount; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Stall) {
            Stall other = (Stall) obj;
            return this.stallNo.equals(other.stallNo);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Stall [No=" + this.stallNo + ", Publisher=" + this.publisherName
                + ", Type=" + this.stallType + ", Status=" + this.status + "]";
    }
}
