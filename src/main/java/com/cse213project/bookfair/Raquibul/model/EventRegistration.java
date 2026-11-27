package com.cse213project.bookfair.Raquibul.model;

import com.cse213project.bookfair.Raquibul.dummy.Event;
import com.cse213project.bookfair.Raquibul.util.BinaryFileHelper;
import java.io.Serializable;
import java.util.ArrayList;

public class EventRegistration implements Serializable {
    private String registrationID;
    private String visitorID;
    private String eventID;
    private String registeredAt;
    private String seatPassQR;
    private String attendanceStatus;

    public EventRegistration() {
        this.registrationID = "";
        this.visitorID = "";
        this.eventID = "";
        this.registeredAt = "";
        this.seatPassQR = "";
        this.attendanceStatus = "Registered";
    }

    public EventRegistration(String registrationID, String visitorID, String eventID,
                             String registeredAt, String seatPassQR, String attendanceStatus) {
        this.registrationID = registrationID;
        this.visitorID = visitorID;
        this.eventID = eventID;
        this.registeredAt = registeredAt;
        this.seatPassQR = seatPassQR;
        this.attendanceStatus = attendanceStatus;
    }

    public boolean checkCapacity() {
        BinaryFileHelper<Event> eventFile = new BinaryFileHelper<Event>("Events.bin");
        ArrayList<Event> events = eventFile.readAll();
        for (Event e : events) {
            if (e.getEventId().equals(this.eventID)) {
                return e.getCapacity() > 0;
            }
        }
        return false;
    }

    public void cancelRegistration() {
        this.attendanceStatus = "Cancelled";
    }

    public String getRegistrationID() { return this.registrationID; }
    public void setRegistrationID(String registrationID) { this.registrationID = registrationID; }

    public String getVisitorID() { return this.visitorID; }
    public void setVisitorID(String visitorID) { this.visitorID = visitorID; }

    public String getEventID() { return this.eventID; }
    public void setEventID(String eventID) { this.eventID = eventID; }

    public String getRegisteredAt() { return this.registeredAt; }
    public void setRegisteredAt(String registeredAt) { this.registeredAt = registeredAt; }

    public String getSeatPassQR() { return this.seatPassQR; }
    public void setSeatPassQR(String seatPassQR) { this.seatPassQR = seatPassQR; }

    public String getAttendanceStatus() { return this.attendanceStatus; }
    public void setAttendanceStatus(String attendanceStatus) { this.attendanceStatus = attendanceStatus; }

    @Override
    public String toString() {
        return "EventRegistration [ID=" + this.registrationID + ", Event=" + this.eventID
                + ", QR=" + this.seatPassQR + ", Status=" + this.attendanceStatus + "]";
    }
}
