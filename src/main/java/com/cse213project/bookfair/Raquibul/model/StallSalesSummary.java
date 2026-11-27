package com.cse213project.bookfair.Raquibul.model;

import java.io.Serializable;
import java.time.LocalDate;

public class StallSalesSummary implements Serializable {
    private String summaryID;
    private String stallNo;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double totalCounterSales;
    private double totalPreOrderSales;
    private int unitsSold;

    public StallSalesSummary() {
        this.summaryID = "";
        this.stallNo = "";
        this.fromDate = LocalDate.now();
        this.toDate = LocalDate.now();
        this.totalCounterSales = 0.0;
        this.totalPreOrderSales = 0.0;
        this.unitsSold = 0;
    }

    public StallSalesSummary(String summaryID, String stallNo, LocalDate fromDate, LocalDate toDate,
                             double totalCounterSales, double totalPreOrderSales, int unitsSold) {
        this.summaryID = summaryID;
        this.stallNo = stallNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalCounterSales = totalCounterSales;
        this.totalPreOrderSales = totalPreOrderSales;
        this.unitsSold = unitsSold;
    }

    public double computeNetRevenue() {
        return this.totalCounterSales + this.totalPreOrderSales;
    }

    public String exportCSV() {
        return "SummaryID,StallNo,FromDate,ToDate,CounterSales,PreOrderSales,UnitsSold,NetRevenue\n"
                + this.summaryID + "," + this.stallNo + "," + this.fromDate + "," + this.toDate + ","
                + this.totalCounterSales + "," + this.totalPreOrderSales + "," + this.unitsSold + ","
                + computeNetRevenue();
    }

    public String getSummaryID() { return this.summaryID; }
    public void setSummaryID(String summaryID) { this.summaryID = summaryID; }

    public String getStallNo() { return this.stallNo; }
    public void setStallNo(String stallNo) { this.stallNo = stallNo; }

    public LocalDate getFromDate() { return this.fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return this.toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public double getTotalCounterSales() { return this.totalCounterSales; }
    public void setTotalCounterSales(double totalCounterSales) { this.totalCounterSales = totalCounterSales; }

    public double getTotalPreOrderSales() { return this.totalPreOrderSales; }
    public void setTotalPreOrderSales(double totalPreOrderSales) { this.totalPreOrderSales = totalPreOrderSales; }

    public int getUnitsSold() { return this.unitsSold; }
    public void setUnitsSold(int unitsSold) { this.unitsSold = unitsSold; }

    @Override
    public String toString() {
        return "StallSalesSummary [ID=" + this.summaryID + ", Stall=" + this.stallNo
                + ", NetRevenue=" + computeNetRevenue() + ", Units=" + this.unitsSold + "]";
    }
}
