package com.cse213project.bookfair.Raquibul.model;

import com.cse213project.bookfair.Raquibul.dummy.Book;
import com.cse213project.bookfair.Raquibul.dummy.Event;
import com.cse213project.bookfair.Raquibul.dummy.Feedback;
import com.cse213project.bookfair.Raquibul.dummy.LostFoundItem;
import com.cse213project.bookfair.Raquibul.dummy.OwnershipClaim;
import com.cse213project.bookfair.Raquibul.dummy.ParkingSlot;
import com.cse213project.bookfair.Raquibul.dummy.Ticket;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class GeneralVisitor extends User {
    private String visitorID;
    private String email;
    private String membershipTier;

    public GeneralVisitor() {
        this.userID = "";
        this.name = "";
        this.contact = "";
        this.passwordHash = "";
        this.role = "GeneralVisitor";
        this.visitorID = "";
        this.email = "";
        this.membershipTier = "Regular";
    }

    public GeneralVisitor(String userID, String name, String contact, String passwordHash,
                          String visitorID, String email, String membershipTier) {
        this.userID = userID;
        this.name = name;
        this.contact = contact;
        this.passwordHash = passwordHash;
        this.role = "GeneralVisitor";
        this.visitorID = visitorID;
        this.email = email;
        this.membershipTier = membershipTier;
    }

    @Override
    public String getDashboardFxml() {
        return "GeneralVisitorDashboardView.fxml";
    }

    public ArrayList<Book> searchBooksAndStalls(String keyword, String filterBy) {
        BinaryFileHelper<Book> bookFile = new BinaryFileHelper<Book>("Books.bin");
        ArrayList<Book> allBooks = bookFile.readAll();
        ArrayList<Book> matched = new ArrayList<Book>();
        String keyLower = keyword.toLowerCase();

        for (Book b : allBooks) {
            if (!b.getVisibleToPublic()) {
                continue;
            }
            if (keyLower.equals("")) {
                matched.add(b);
            } else if (filterBy.equals("Title") && b.getTitle().toLowerCase().contains(keyLower)) {
                matched.add(b);
            } else if (filterBy.equals("Author") && b.getAuthor().toLowerCase().contains(keyLower)) {
                matched.add(b);
            } else if (filterBy.equals("Publisher") && b.getPublisher().toLowerCase().contains(keyLower)) {
                matched.add(b);
            } else if (filterBy.equals("Category") && b.getCategory().toLowerCase().contains(keyLower)) {
                matched.add(b);
            } else if (filterBy.equals("All")) {
                if (b.getTitle().toLowerCase().contains(keyLower)
                        || b.getAuthor().toLowerCase().contains(keyLower)
                        || b.getPublisher().toLowerCase().contains(keyLower)
                        || b.getCategory().toLowerCase().contains(keyLower)) {
                    matched.add(b);
                }
            }
        }
        return matched;
    }

    public PreOrder preOrderBook(String stallNo, String isbn, int qty) {
        return preOrderBook(stallNo, isbn, qty, "Express Counter");
    }

    public PreOrder preOrderBook(String stallNo, String isbn, int qty, String pickupOption) {
        BinaryFileHelper<Book> bookFile = new BinaryFileHelper<Book>("Books.bin");
        ArrayList<Book> books = bookFile.readAll();
        double unitPrice = 0.0;
        boolean found = false;
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                unitPrice = b.getPrice();
                found = true;
            }
        }
        if (!found || qty <= 0) {
            return null;
        }
        double deliveryFee = 20.0;
        if (pickupOption.equals("Courier")) {
            deliveryFee = 60.0;
        }
        double totalCost = (unitPrice * qty) + deliveryFee;
        Random r = new Random();
        String orderId = "PO-" + (1000 + r.nextInt(9000));
        String pickupCode = "PK-" + (1000 + r.nextInt(9000));
        PreOrder po = new PreOrder(orderId, this.visitorID, stallNo, isbn, qty,
                "NONE", 0.0, totalCost, pickupCode, "Confirmed");
        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        poFile.appendObject(po);
        return po;
    }

    public double applyVoucher(String orderID, String voucherCode) {
        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        ArrayList<PreOrder> orders = poFile.readAll();
        double newTotal = -1.0;
        for (PreOrder po : orders) {
            if (po.getOrderID().equals(orderID) && po.getVisitorID().equals(this.visitorID)) {
                boolean applied = po.applyVoucher(voucherCode);
                if (applied) {
                    newTotal = po.getTotal();
                }
            }
        }
        if (newTotal >= 0.0) {
            poFile.overwriteAll(orders);
        }
        return newTotal;
    }

    public boolean cancelPreOrder(String orderID) {
        BinaryFileHelper<PreOrder> poFile = new BinaryFileHelper<PreOrder>("PreOrders.bin");
        ArrayList<PreOrder> orders = poFile.readAll();
        boolean cancelled = false;
        for (PreOrder po : orders) {
            if (po.getOrderID().equals(orderID) && po.getVisitorID().equals(this.visitorID)) {
                if (!po.getStatus().equals("Fulfilled")) {
                    po.setStatus("Cancelled");
                    cancelled = true;
                }
            }
        }
        if (cancelled) {
            poFile.overwriteAll(orders);
        }
        return cancelled;
    }

    public EventRegistration registerEvent(String eventID) {
        BinaryFileHelper<Event> eventFile = new BinaryFileHelper<Event>("Events.bin");
        ArrayList<Event> events = eventFile.readAll();
        Event targetEvent = null;
        for (Event e : events) {
            if (e.getEventId().equals(eventID)) {
                targetEvent = e;
            }
        }
        if (targetEvent == null || targetEvent.getCapacity() <= 0) {
            return null;
        }
        targetEvent.setCapacity(targetEvent.getCapacity() - 1);
        eventFile.overwriteAll(events);

        Random r = new Random();
        String regId = "REG-" + (1000 + r.nextInt(9000));
        String qr = "QR-EVT-" + eventID + "-" + (100 + r.nextInt(900));
        EventRegistration reg = new EventRegistration(regId, this.visitorID, eventID,
                LocalDate.now().toString(), qr, "Registered");
        BinaryFileHelper<EventRegistration> regFile = new BinaryFileHelper<EventRegistration>("EventRegistrations.bin");
        regFile.appendObject(reg);
        return reg;
    }

    public Ticket buyEntryPass(String passType, LocalDate visitDate, boolean reserveParking) {
        double ticketPrice = 50.0;
        if (passType.equals("VIP Entry Pass")) {
            ticketPrice = 150.0;
        } else if (passType.equals("Student Pass")) {
            ticketPrice = 20.0;
        }

        if (reserveParking) {
            BinaryFileHelper<ParkingSlot> slotFile = new BinaryFileHelper<ParkingSlot>("ParkingSlots.bin");
            ArrayList<ParkingSlot> slots = slotFile.readAll();
            boolean slotAssigned = false;
            for (ParkingSlot s : slots) {
                if (!s.getIsOccupied() && !slotAssigned) {
                    s.setIsOccupied(true);
                    ticketPrice = ticketPrice + s.getFee();
                    slotAssigned = true;
                }
            }
            if (slotAssigned) {
                slotFile.overwriteAll(slots);
            }
        }

        Random r = new Random();
        String ticketId = "TKT-" + (1000 + r.nextInt(9000));
        String qr = "QR-TKT-" + ticketId;
        Ticket ticket = new Ticket(ticketId, this.visitorID, passType, visitDate, ticketPrice, "Valid", qr);
        BinaryFileHelper<Ticket> ticketFile = new BinaryFileHelper<Ticket>("Tickets.bin");
        ticketFile.appendObject(ticket);
        return ticket;
    }

    public Review submitReview(String targetID, String targetType, int rating, String comment) {
        Random r = new Random();
        String revId = "REV-" + (1000 + r.nextInt(9000));
        Review rev = new Review(revId, this.visitorID, targetID, targetType, rating, comment, LocalDate.now(), "Published");
        if (!rev.validateRating()) {
            return null;
        }
        BinaryFileHelper<Review> revFile = new BinaryFileHelper<Review>("Reviews.bin");
        revFile.appendObject(rev);
        return rev;
    }

    public Feedback submitFeedback(String category, int rating, String comment) {
        Random r = new Random();
        String fbId = "FB-" + (1000 + r.nextInt(9000));
        Feedback fb = new Feedback(fbId, this.visitorID, category, rating, comment);
        BinaryFileHelper<Feedback> fbFile = new BinaryFileHelper<Feedback>("Feedbacks.bin");
        fbFile.appendObject(fb);
        return fb;
    }

    public OwnershipClaim reportOrClaimLostItem(String itemID, String proofDetails) {
        Random r = new Random();
        String claimId = "CLM-" + (1000 + r.nextInt(9000));
        OwnershipClaim claim = new OwnershipClaim(claimId, itemID, this.name, this.contact, proofDetails, false, "Pending");
        BinaryFileHelper<OwnershipClaim> claimFile = new BinaryFileHelper<OwnershipClaim>("OwnershipClaims.bin");
        claimFile.appendObject(claim);
        return claim;
    }

    public LostFoundItem reportLostOrFoundItem(String reportType, String category, String location, String description, String phone) {
        Random r = new Random();
        String itemId = "LFI-" + (1000 + r.nextInt(9000));
        LostFoundItem item = new LostFoundItem(itemId, reportType, category, description, location,
                LocalDate.now().toString(), this.visitorID, "Open", phone);
        BinaryFileHelper<LostFoundItem> lfiFile = new BinaryFileHelper<LostFoundItem>("LostFoundItems.bin");
        lfiFile.appendObject(item);
        return item;
    }

    public String getVisitorID() { return this.visitorID; }
    public void setVisitorID(String visitorID) { this.visitorID = visitorID; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getMembershipTier() { return this.membershipTier; }
    public void setMembershipTier(String membershipTier) { this.membershipTier = membershipTier; }
}
