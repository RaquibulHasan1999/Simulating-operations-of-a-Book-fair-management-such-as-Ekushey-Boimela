package com.cse213project.bookfair.Raquibul.util;

import com.cse213project.bookfair.Raquibul.dummy.Book;
import com.cse213project.bookfair.Raquibul.dummy.DummyOtherUser;
import com.cse213project.bookfair.Raquibul.dummy.Event;
import com.cse213project.bookfair.Raquibul.dummy.LostFoundItem;
import com.cse213project.bookfair.Raquibul.dummy.ParkingSlot;
import com.cse213project.bookfair.Raquibul.dummy.StallInventory;
import com.cse213project.bookfair.Raquibul.dummy.Voucher;
import com.cse213project.bookfair.Raquibul.model.GeneralVisitor;
import com.cse213project.bookfair.Raquibul.model.PreOrder;
import com.cse213project.bookfair.Raquibul.model.Stall;
import com.cse213project.bookfair.Raquibul.model.StallManager;
import com.cse213project.bookfair.Raquibul.model.User;

import java.time.LocalDate;

public class DummyDataInitializer {

    public static void initializeIfMissing() {
        BinaryFileHelper<User> userFile = new BinaryFileHelper<User>("Users.bin");
        if (!userFile.fileExists()) {
            userFile.appendObject(new GeneralVisitor("V101", "MD Raquibul Hasan", "01711000001", "1234",
                    "V101", "raquibul@iub.edu.bd", "Gold"));
            userFile.appendObject(new StallManager("M201", "MD Raquibul Hasan", "01711000002", "1234",
                    "M201", "Prothoma Prokashon", "S-101", "TL-202601"));
            // Dummy users for other team members
            userFile.appendObject(new DummyOtherUser("E301", "Erin Jahan Shovra", "01711000003", "1234",
                    "EventCoordinator", "Erin Jahan Shovra (2310792)"));
            userFile.appendObject(new DummyOtherUser("MO401", "Erin Jahan Shovra", "01711000004", "1234",
                    "MaintenanceOfficer", "Erin Jahan Shovra (2310792)"));
            userFile.appendObject(new DummyOtherUser("B501", "Ankur Das", "01711000005", "1234",
                    "BookInventoryOfficer", "Ankur Das (2430944)"));
            userFile.appendObject(new DummyOtherUser("LF601", "Ankur Das", "01711000006", "1234",
                    "LostAndFoundOfficer", "Ankur Das (2430944)"));
            userFile.appendObject(new DummyOtherUser("TP701", "Nahin Walid Sabith", "01711000007", "1234",
                    "TicketAndParkingOfficer", "Nahin Walid Sabith (2311935)"));
            userFile.appendObject(new DummyOtherUser("RF801", "Nahin Walid Sabith", "01711000008", "1234",
                    "ReviewAndFeedbackOfficer", "Nahin Walid Sabith (2311935)"));
        }

        BinaryFileHelper<Stall> stallFile = new BinaryFileHelper<Stall>("Stalls.bin");
        if (!stallFile.fileExists()) {
            stallFile.appendObject(new Stall("S-101", "M201", "Prothoma Prokashon", "Pavilion", 50000.0, "Operational"));
            stallFile.appendObject(new Stall("S-102", "M202", "Anyaprokash", "Double", 30000.0, "Operational"));
            stallFile.appendObject(new Stall("S-103", "M203", "Somoy Prokashon", "Single", 15000.0, "Operational"));
        }

        BinaryFileHelper<Book> bookFile = new BinaryFileHelper<Book>("Books.bin");
        if (!bookFile.fileExists()) {
            bookFile.appendObject(new Book("978-984-01", "Himu Mama", "Humayun Ahmed", "Fiction", "Prothoma Prokashon", 350.0, true));
            bookFile.appendObject(new Book("978-984-02", "Pother Panchali", "Bibhutibhushan Bandyopadhyay", "Classic", "Prothoma Prokashon", 420.0, true));
            bookFile.appendObject(new Book("978-984-03", "Object Oriented Java", "Subrata Kumar Dey", "Academic", "Anyaprokash", 550.0, true));
            bookFile.appendObject(new Book("978-984-04", "Chander Pahar", "Bibhutibhushan Bandyopadhyay", "Adventure", "Somoy Prokashon", 300.0, true));
        }

        BinaryFileHelper<StallInventory> invFile = new BinaryFileHelper<StallInventory>("StallInventory.bin");
        if (!invFile.fileExists()) {
            invFile.appendObject(new StallInventory("S-101", "978-984-01", 25, 10, 5));
            invFile.appendObject(new StallInventory("S-101", "978-984-02", 4, 12, 5));
            invFile.appendObject(new StallInventory("S-102", "978-984-03", 18, 6, 5));
            invFile.appendObject(new StallInventory("S-103", "978-984-04", 3, 9, 5));
        }

        BinaryFileHelper<Event> eventFile = new BinaryFileHelper<Event>("Events.bin");
        if (!eventFile.fileExists()) {
            eventFile.appendObject(new Event("EVT-01", "Bangla Literature Panel", LocalDate.of(2026, 2, 10), "Main Stage", 120, "Scheduled"));
            eventFile.appendObject(new Event("EVT-02", "New Novel Unveiling", LocalDate.of(2026, 2, 14), "Nazrul Mancha", 60, "Scheduled"));
            eventFile.appendObject(new Event("EVT-03", "Young Authors Meet & Greet", LocalDate.of(2026, 2, 21), "Bangla Academy Hall", 45, "Scheduled"));
        }

        BinaryFileHelper<Voucher> voucherFile = new BinaryFileHelper<Voucher>("Vouchers.bin");
        if (!voucherFile.fileExists()) {
            voucherFile.appendObject(new Voucher("BOIMELA10", 10.0, "Active"));
            voucherFile.appendObject(new Voucher("EKUSHEY20", 20.0, "Active"));
            voucherFile.appendObject(new Voucher("EXPIRED50", 50.0, "Expired"));
        }

        BinaryFileHelper<ParkingSlot> slotFile = new BinaryFileHelper<ParkingSlot>("ParkingSlots.bin");
        if (!slotFile.fileExists()) {
            slotFile.appendObject(new ParkingSlot("P-01", false, 100.0f));
            slotFile.appendObject(new ParkingSlot("P-02", false, 100.0f));
            slotFile.appendObject(new ParkingSlot("P-03", false, 100.0f));
        }

        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        if (!poFile.fileExists()) {
            poFile.appendObject(new PreOrder("PO-1001", "V101", "S-101", "978-984-01", 1, "NONE", 0.0, 370.0, "PK-1111", "Fulfilled"));
            poFile.appendObject(new PreOrder("PO-1002", "V101", "S-101", "978-984-02", 2, "NONE", 0.0, 860.0, "PK-2222", "Confirmed"));
        }

        BinaryFileHelper<LostFoundItem> lfiFile = new BinaryFileHelper<LostFoundItem>("LostFoundItems.bin");
        if (!lfiFile.fileExists()) {
            lfiFile.appendObject(new LostFoundItem("LFI-5001", "Found Item", "Wallet", "Black leather wallet found near Gate 2",
                    "Gate 2", "2026-02-05", "V102", "Open", "01711999999"));
        }
    }
}
