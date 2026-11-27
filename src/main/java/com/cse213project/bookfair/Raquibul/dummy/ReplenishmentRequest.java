package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;

// Dummy model class for User 5 (Book Inventory Officer - Ankur Das)
public class ReplenishmentRequest implements Serializable {
    private String requestID;
    private String stallNo;
    private String isbn;
    private int requestedQty;
    private String status;

    public ReplenishmentRequest() {
        this.requestID = "";
        this.stallNo = "";
        this.isbn = "";
        this.requestedQty = 0;
        this.status = "Pending";
    }

    public ReplenishmentRequest(String requestID, String stallNo, String isbn, int requestedQty, String status) {
        this.requestID = requestID;
        this.stallNo = stallNo;
        this.isbn = isbn;
        this.requestedQty = requestedQty;
        this.status = status;
    }

    public String getRequestID() { return this.requestID; }
    public void setRequestID(String requestID) { this.requestID = requestID; }

    public String getStallNo() { return this.stallNo; }
    public void setStallNo(String stallNo) { this.stallNo = stallNo; }

    public String getIsbn() { return this.isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getRequestedQty() { return this.requestedQty; }
    public void setRequestedQty(int requestedQty) { this.requestedQty = requestedQty; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ReplenishmentRequest [ID=" + this.requestID + ", Stall=" + this.stallNo
                + ", ISBN=" + this.isbn + ", Qty=" + this.requestedQty + ", Status=" + this.status + "]";
    }
}
