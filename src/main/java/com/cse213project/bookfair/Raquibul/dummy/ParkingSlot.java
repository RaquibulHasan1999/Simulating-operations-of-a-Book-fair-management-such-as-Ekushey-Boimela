package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;

// Dummy model class for User 7 (Ticket and Parking Management - Nahin Walid Sabith)
public class ParkingSlot implements Serializable {
    private String slotNumber;
    private boolean isOccupied;
    private float fee;

    public ParkingSlot() {
        this.slotNumber = "";
        this.isOccupied = false;
        this.fee = 0.0f;
    }

    public ParkingSlot(String slotNumber, boolean isOccupied, float fee) {
        this.slotNumber = slotNumber;
        this.isOccupied = isOccupied;
        this.fee = fee;
    }

    public String getSlotNumber() { return this.slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }

    public boolean getIsOccupied() { return this.isOccupied; }
    public void setIsOccupied(boolean isOccupied) { this.isOccupied = isOccupied; }

    public float getFee() { return this.fee; }
    public void setFee(float fee) { this.fee = fee; }

    @Override
    public String toString() {
        return "ParkingSlot [Slot=" + this.slotNumber + ", Occupied=" + this.isOccupied + ", Fee=" + this.fee + "]";
    }
}
