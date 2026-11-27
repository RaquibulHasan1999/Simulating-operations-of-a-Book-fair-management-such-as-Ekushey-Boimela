package com.cse213project.bookfair.Raquibul.model;

import com.cse213project.bookfair.Raquibul.dummy.Voucher;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import java.io.Serializable;
import java.util.ArrayList;

public class PreOrder implements Serializable {
    private String orderID;
    private String visitorID;
    private String stallNo;
    private String isbn;
    private int quantity;
    private String voucherCode;
    private double discountAmount;
    private double total;
    private String pickupCode;
    private String status;

    public PreOrder() {
        this.orderID = "";
        this.visitorID = "";
        this.stallNo = "";
        this.isbn = "";
        this.quantity = 0;
        this.voucherCode = "NONE";
        this.discountAmount = 0.0;
        this.total = 0.0;
        this.pickupCode = "";
        this.status = "Confirmed";
    }

    public PreOrder(String orderID, String visitorID, String stallNo, String isbn, int quantity,
                    String voucherCode, double discountAmount, double total, String pickupCode, String status) {
        this.orderID = orderID;
        this.visitorID = visitorID;
        this.stallNo = stallNo;
        this.isbn = isbn;
        this.quantity = quantity;
        this.voucherCode = voucherCode;
        this.discountAmount = discountAmount;
        this.total = total;
        this.pickupCode = pickupCode;
        this.status = status;
    }

    public boolean applyVoucher(String code) {
        if (code == null || code.equals("")) {
            return false;
        }
        BinaryFileHelper<Voucher> voucherFile = new BinaryFileHelper<Voucher>("Vouchers.bin");
        ArrayList<Voucher> vouchers = voucherFile.readAll();
        for (Voucher v : vouchers) {
            if (v.getCode().equals(code) && v.getStatus().equals("Active")) {
                double discount = (this.total * v.getDiscountPercent()) / 100.0;
                this.voucherCode = code;
                this.discountAmount = discount;
                this.total = this.total - discount;
                return true;
            }
        }
        return false;
    }

    public void markCollected() {
        this.status = "Fulfilled";
    }

    public String getOrderID() { return this.orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public String getVisitorID() { return this.visitorID; }
    public void setVisitorID(String visitorID) { this.visitorID = visitorID; }

    public String getStallNo() { return this.stallNo; }
    public void setStallNo(String stallNo) { this.stallNo = stallNo; }

    public String getIsbn() { return this.isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getQuantity() { return this.quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getVoucherCode() { return this.voucherCode; }
    public void setVoucherCode(String voucherCode) { this.voucherCode = voucherCode; }

    public double getDiscountAmount() { return this.discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getTotal() { return this.total; }
    public void setTotal(double total) { this.total = total; }

    public String getPickupCode() { return this.pickupCode; }
    public void setPickupCode(String pickupCode) { this.pickupCode = pickupCode; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PreOrder) {
            PreOrder other = (PreOrder) obj;
            return this.orderID.equals(other.orderID);
        }
        return false;
    }

    @Override
    public String toString() {
        return "PreOrder [ID=" + this.orderID + ", ISBN=" + this.isbn + ", Qty=" + this.quantity
                + ", Total=" + this.total + ", Pickup=" + this.pickupCode + ", Status=" + this.status + "]";
    }
}
