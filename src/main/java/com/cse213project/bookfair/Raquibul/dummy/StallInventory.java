package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;


public class StallInventory implements Serializable {
    private String stallNo;
    private String isbn;
    private int availableQty;
    private int soldQty;
    private int reorderLevel;

    public StallInventory() {
        this.stallNo = "";
        this.isbn = "";
        this.availableQty = 0;
        this.soldQty = 0;
        this.reorderLevel = 5;
    }

    public StallInventory(String stallNo, String isbn, int availableQty, int soldQty, int reorderLevel) {
        this.stallNo = stallNo;
        this.isbn = isbn;
        this.availableQty = availableQty;
        this.soldQty = soldQty;
        this.reorderLevel = reorderLevel;
    }

    public void adjustStock() {
        if (this.availableQty < 0) {
            this.availableQty = 0;
        }
    }

    public void adjustStock(int qtyChange) {
        this.availableQty = this.availableQty + qtyChange;
        if (this.availableQty < 0) {
            this.availableQty = 0;
        }
    }

    public String getStallNo() { return this.stallNo; }
    public void setStallNo(String stallNo) { this.stallNo = stallNo; }

    public String getIsbn() { return this.isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getAvailableQty() { return this.availableQty; }
    public void setAvailableQty(int availableQty) { this.availableQty = availableQty; }

    public int getSoldQty() { return this.soldQty; }
    public void setSoldQty(int soldQty) { this.soldQty = soldQty; }

    public int getReorderLevel() { return this.reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }

    @Override
    public String toString() {
        return "StallInventory [Stall=" + this.stallNo + ", ISBN=" + this.isbn
                + ", Available=" + this.availableQty + ", Sold=" + this.soldQty + "]";
    }
}
