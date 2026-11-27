package com.cse213project.bookfair.Raquibul.model;

import com.cse213project.bookfair.Raquibul.dummy.Book;
import com.cse213project.bookfair.Raquibul.dummy.InspectionReport;
import com.cse213project.bookfair.Raquibul.dummy.LaunchRequest;
import com.cse213project.bookfair.Raquibul.dummy.ReplenishmentRequest;
import com.cse213project.bookfair.Raquibul.dummy.StallInventory;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class StallManager extends User {
    private String managerID;
    private String publisherName;
    private String assignedStallNo;
    private String tradeLicenseNo;

    public StallManager() {
        this.userID = "";
        this.name = "";
        this.contact = "";
        this.passwordHash = "";
        this.role = "StallManager";
        this.managerID = "";
        this.publisherName = "";
        this.assignedStallNo = "";
        this.tradeLicenseNo = "";
    }

    public StallManager(String userID, String name, String contact, String passwordHash,
                        String managerID, String publisherName, String assignedStallNo, String tradeLicenseNo) {
        this.userID = userID;
        this.name = name;
        this.contact = contact;
        this.passwordHash = passwordHash;
        this.role = "StallManager";
        this.managerID = managerID;
        this.publisherName = publisherName;
        this.assignedStallNo = assignedStallNo;
        this.tradeLicenseNo = tradeLicenseNo;
    }

    @Override
    public String getDashboardFxml() {
        return "StallManagerDashboardView.fxml";
    }

    public StallApplication applyStallAllocation(String stallType, String preferredZone, String licenseNo) {
        Random r = new Random();
        String appId = "APP-" + (1000 + r.nextInt(9000));
        StallApplication app = new StallApplication(appId, this.managerID, this.publisherName,
                stallType + " (" + preferredZone + ")", licenseNo, "Pending Approval", LocalDate.now());
        if (!app.validateLicense()) {
            return null;
        }
        BinaryFileHelper<StallApplication> appFile = new BinaryFileHelper<StallApplication>("StallApplications.bin");
        appFile.appendObject(app);
        return app;
    }

    public StallInventory updateInventory(String isbn, int qtyChange, double price) {
        BinaryFileHelper<Book> bookFile = new BinaryFileHelper<Book>("Books.bin");
        ArrayList<Book> books = bookFile.readAll();
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                b.setPrice(price);
            }
        }
        bookFile.overwriteAll(books);

        BinaryFileHelper<StallInventory> invFile = new BinaryFileHelper<StallInventory>("StallInventory.bin");
        ArrayList<StallInventory> invList = invFile.readAll();
        StallInventory updated = null;
        for (StallInventory inv : invList) {
            if (inv.getStallNo().equals(this.assignedStallNo) && inv.getIsbn().equals(isbn)) {
                inv.adjustStock(qtyChange);
                updated = inv;
            }
        }
        if (updated == null) {
            int startQty = qtyChange;
            if (startQty < 0) {
                startQty = 0;
            }
            updated = new StallInventory(this.assignedStallNo, isbn, startQty, 0, 5);
            invList.add(updated);
        }
        invFile.overwriteAll(invList);
        return updated;
    }

    public CounterSale recordCounterSales(String isbn, int qty, double unitPrice, String paymentMethod) {
        BinaryFileHelper<StallInventory> invFile = new BinaryFileHelper<StallInventory>("StallInventory.bin");
        ArrayList<StallInventory> invList = invFile.readAll();
        StallInventory matched = null;
        for (StallInventory inv : invList) {
            if (inv.getStallNo().equals(this.assignedStallNo) && inv.getIsbn().equals(isbn)) {
                matched = inv;
            }
        }
        if (matched == null || matched.getAvailableQty() < qty || qty <= 0) {
            return null;
        }
        matched.setAvailableQty(matched.getAvailableQty() - qty);
        matched.setSoldQty(matched.getSoldQty() + qty);
        invFile.overwriteAll(invList);

        Random r = new Random();
        String saleId = "SALE-" + (1000 + r.nextInt(9000));
        CounterSale sale = new CounterSale(saleId, this.assignedStallNo, isbn, qty,
                unitPrice, paymentMethod, LocalDate.now().toString());
        BinaryFileHelper<CounterSale> saleFile = new BinaryFileHelper<CounterSale>("CounterSales.bin");
        saleFile.appendObject(sale);
        return sale;
    }

    public boolean fulfillPreOrder(String orderID, String pickupCode) {
        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        ArrayList<PreOrder> orders = poFile.readAll();
        boolean fulfilled = false;
        for (PreOrder po : orders) {
            if (po.getOrderID().equals(orderID)
                    && po.getStallNo().equals(this.assignedStallNo)
                    && po.getPickupCode().equals(pickupCode)
                    && po.getStatus().equals("Confirmed")) {
                po.markCollected();
                fulfilled = true;
            }
        }
        if (fulfilled) {
            poFile.overwriteAll(orders);
        }
        return fulfilled;
    }

    public StaffPass manageStaffPasses(String staffName, String staffRole, String action) {
        BinaryFileHelper<StaffPass> passFile = new BinaryFileHelper<StaffPass>("StaffPasses.bin");
        ArrayList<StaffPass> passes = passFile.readAll();

        if (action.equals("Revoke")) {
            StaffPass revoked = null;
            for (StaffPass sp : passes) {
                if (sp.getStallNo().equals(this.assignedStallNo)
                        && sp.getStaffName().equals(staffName)
                        && sp.getIsActive()) {
                    sp.revokePass();
                    revoked = sp;
                }
            }
            if (revoked != null) {
                passFile.overwriteAll(passes);
            }
            return revoked;
        }

        int activeCount = 0;
        for (StaffPass sp : passes) {
            if (sp.getStallNo().equals(this.assignedStallNo) && sp.getIsActive()) {
                activeCount = activeCount + 1;
            }
        }
        if (activeCount >= 5) {
            return null;
        }

        Random r = new Random();
        String passId = "PASS-" + (1000 + r.nextInt(9000));
        StaffPass newPass = new StaffPass(passId, this.assignedStallNo, staffName, staffRole, LocalDate.now(), "", true);
        newPass.generateQR();
        passFile.appendObject(newPass);
        return newPass;
    }

    public ReplenishmentRequest requestReplenishment(String isbn, int requestedQty) {
        if (requestedQty <= 0 || requestedQty > 500) {
            return null;
        }
        Random r = new Random();
        String reqId = "REQ-" + (1000 + r.nextInt(9000));
        ReplenishmentRequest req = new ReplenishmentRequest(reqId, this.assignedStallNo, isbn, requestedQty, "Pending");
        BinaryFileHelper<ReplenishmentRequest> reqFile = new BinaryFileHelper<ReplenishmentRequest>("ReplenishmentRequests.bin");
        reqFile.appendObject(req);
        return req;
    }

    public LaunchRequest organizeBookLaunch(String bookTitle, LocalDate preferredDate, String timeSlot) {
        BinaryFileHelper<LaunchRequest> launchFile = new BinaryFileHelper<LaunchRequest>("LaunchRequests.bin");
        ArrayList<LaunchRequest> existing = launchFile.readAll();
        for (LaunchRequest lr : existing) {
            if (lr.getRequestedTime().equals(preferredDate) && lr.getTimeSlot().equals(timeSlot)) {
                return null;
            }
        }
        Random r = new Random();
        String reqId = "LR-" + (1000 + r.nextInt(9000));
        LaunchRequest lr = new LaunchRequest(reqId, this.publisherName, bookTitle, preferredDate, timeSlot, "Pending Review");
        launchFile.appendObject(lr);
        return lr;
    }

    public StallSalesSummary viewFinancialAnalytics(LocalDate fromDate, LocalDate toDate) {
        BinaryFileHelper<CounterSale> saleFile = new BinaryFileHelper<CounterSale>("CounterSales.bin");
        ArrayList<CounterSale> sales = saleFile.readAll();
        double totalCounter = 0.0;
        int totalUnits = 0;

        for (CounterSale cs : sales) {
            if (cs.getStallNo().equals(this.assignedStallNo)) {
                totalCounter = totalCounter + cs.getTotalAmount();
                totalUnits = totalUnits + cs.getQuantity();
            }
        }

        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        ArrayList<PreOrder> orders = poFile.readAll();
        double totalPreOrder = 0.0;
        for (PreOrder po : orders) {
            if (po.getStallNo().equals(this.assignedStallNo) && !po.getStatus().equals("Cancelled")) {
                totalPreOrder = totalPreOrder + po.getTotal();
                totalUnits = totalUnits + po.getQuantity();
            }
        }

        Random r = new Random();
        String sumId = "SUM-" + (1000 + r.nextInt(9000));
        StallSalesSummary summary = new StallSalesSummary(sumId, this.assignedStallNo, fromDate, toDate,
                totalCounter, totalPreOrder, totalUnits);
        BinaryFileHelper<StallSalesSummary> sumFile = new BinaryFileHelper<StallSalesSummary>("StallSalesSummaries.bin");
        sumFile.appendObject(summary);
        return summary;
    }

    public UtilityComplaint submitUtilityComplaint(String damageType, String description, String priority) {
        Random r = new Random();
        String compId = "CMP-" + (1000 + r.nextInt(9000));
        UtilityComplaint complaint = new UtilityComplaint(compId, this.assignedStallNo, damageType,
                description, priority, "Open", LocalDate.now().toString());
        InspectionReport report = complaint.escalateToMaintenance();

        BinaryFileHelper<UtilityComplaint> compFile = new BinaryFileHelper<UtilityComplaint>("UtilityComplaints.bin");
        compFile.appendObject(complaint);

        BinaryFileHelper<InspectionReport> repFile = new BinaryFileHelper<InspectionReport>("InspectionReports.bin");
        repFile.appendObject(report);

        return complaint;
    }

    public String getManagerID() { return this.managerID; }
    public void setManagerID(String managerID) { this.managerID = managerID; }

    public String getPublisherName() { return this.publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public String getAssignedStallNo() { return this.assignedStallNo; }
    public void setAssignedStallNo(String assignedStallNo) { this.assignedStallNo = assignedStallNo; }

    public String getTradeLicenseNo() { return this.tradeLicenseNo; }
    public void setTradeLicenseNo(String tradeLicenseNo) { this.tradeLicenseNo = tradeLicenseNo; }
}
