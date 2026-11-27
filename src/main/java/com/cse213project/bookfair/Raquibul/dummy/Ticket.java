package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;
import java.time.LocalDate;


public class Ticket implements Serializable {
    private String ticketID;
    private String visitorID;
    private String type;
    private LocalDate date;
    private double price;
    private String status;
    private String qrCode;

    public Ticket() {
        this.ticketID = "";
        this.visitorID = "";
        this.type = "Standard";
        this.date = LocalDate.now();
        this.price = 0.0;
        this.status = "Valid";
        this.qrCode = "";
    }

    public Ticket(String ticketID, String visitorID, String type, LocalDate date, double price, String status, String qrCode) {
        this.ticketID = ticketID;
        this.visitorID = visitorID;
        this.type = type;
        this.date = date;
        this.price = price;
        this.status = status;
        this.qrCode = qrCode;
    }

    public String getTicketID() { return this.ticketID; }
    public void setTicketID(String ticketID) { this.ticketID = ticketID; }

    public String getVisitorID() { return this.visitorID; }
    public void setVisitorID(String visitorID) { this.visitorID = visitorID; }

    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getDate() { return this.date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getPrice() { return this.price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    public String getQrCode() { return this.qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    @Override
    public String toString() {
        return "Ticket [ID=" + this.ticketID + ", Type=" + this.type + ", Date=" + this.date
                + ", Price=" + this.price + ", QR=" + this.qrCode + "]";
    }
}
