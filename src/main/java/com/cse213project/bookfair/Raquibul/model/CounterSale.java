package com.cse213project.bookfair.Raquibul.model;

import java.io.Serializable;

public class CounterSale implements Serializable {
    private String saleID;
    private String stallNo;
    private String isbn;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String paymentMethod;
    private String saleTimestamp;

    public CounterSale() {
        this.saleID = "";
        this.stallNo = "";
        this.isbn = "";
        this.quantity = 0;
        this.unitPrice = 0.0;
        this.totalAmount = 0.0;
        this.paymentMethod = "Cash";
        this.saleTimestamp = "";
    }

    public CounterSale(String saleID, String stallNo, String isbn, int quantity,
                       double unitPrice, String paymentMethod, String saleTimestamp) {
        this.saleID = saleID;
        this.stallNo = stallNo;
        this.isbn = isbn;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.paymentMethod = paymentMethod;
        this.saleTimestamp = saleTimestamp;
        this.totalAmount = calculateTotal();
    }

    public double calculateTotal() {
        double gross = this.quantity * this.unitPrice;
        double fairDiscount = gross * 0.25;
        this.totalAmount = gross - fairDiscount;
        return this.totalAmount;
    }

    public String generateReceipt() {
        return "=== EKUSHEY BOIMELA E-RECEIPT ===\n"
                + "Sale ID: " + this.saleID + "\n"
                + "Stall No: " + this.stallNo + "\n"
                + "Book ISBN: " + this.isbn + "\n"
                + "Quantity: " + this.quantity + "\n"
                + "Unit Price: BDT " + this.unitPrice + "\n"
                + "Fair Discount: 25%\n"
                + "Net Payable: BDT " + this.totalAmount + "\n"
                + "Payment Method: " + this.paymentMethod + "\n"
                + "Date: " + this.saleTimestamp + "\n"
                + "QR Token: QR-" + this.saleID;
    }

    public String getSaleID() { return this.saleID; }
    public void setSaleID(String saleID) { this.saleID = saleID; }

    public String getStallNo() { return this.stallNo; }
    public void setStallNo(String stallNo) { this.stallNo = stallNo; }

    public String getIsbn() { return this.isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getQuantity() { return this.quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return this.unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getTotalAmount() { return this.totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return this.paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getSaleTimestamp() { return this.saleTimestamp; }
    public void setSaleTimestamp(String saleTimestamp) { this.saleTimestamp = saleTimestamp; }

    @Override
    public String toString() {
        return "CounterSale [ID=" + this.saleID + ", ISBN=" + this.isbn
                + ", Qty=" + this.quantity + ", Total=" + this.totalAmount + "]";
    }
}
