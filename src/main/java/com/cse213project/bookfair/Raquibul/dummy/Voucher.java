package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;


public class Voucher implements Serializable {
    private String code;
    private double discountPercent;
    private String status;

    public Voucher() {
        this.code = "";
        this.discountPercent = 0.0;
        this.status = "Inactive";
    }

    public Voucher(String code, double discountPercent, String status) {
        this.code = code;
        this.discountPercent = discountPercent;
        this.status = status;
    }

    public String getCode() { return this.code; }
    public void setCode(String code) { this.code = code; }

    public double getDiscountPercent() { return this.discountPercent; }
    public void setDiscountPercent(double discountPercent) { this.discountPercent = discountPercent; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Voucher [Code=" + this.code + ", Discount=" + this.discountPercent + "%, Status=" + this.status + "]";
    }
}
