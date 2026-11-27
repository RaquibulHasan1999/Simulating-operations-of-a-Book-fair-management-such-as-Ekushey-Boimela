package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;
import java.time.LocalDate;

// Dummy model class for User 3 (Event Coordinator - Erin Jahan Shovra)
public class Event implements Serializable {
    private String eventId;
    private String title;
    private LocalDate scheduledDate;
    private String stageLocation;
    private int capacity;
    private String status;

    public Event() {
        this.eventId = "";
        this.title = "";
        this.scheduledDate = LocalDate.now();
        this.stageLocation = "";
        this.capacity = 0;
        this.status = "Scheduled";
    }

    public Event(String eventId, String title, LocalDate scheduledDate, String stageLocation, int capacity, String status) {
        this.eventId = eventId;
        this.title = title;
        this.scheduledDate = scheduledDate;
        this.stageLocation = stageLocation;
        this.capacity = capacity;
        this.status = status;
    }

    public String getEventId() { return this.eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getScheduledDate() { return this.scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }

    public String getStageLocation() { return this.stageLocation; }
    public void setStageLocation(String stageLocation) { this.stageLocation = stageLocation; }

    public int getCapacity() { return this.capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Event [ID=" + this.eventId + ", Title=" + this.title + ", Date=" + this.scheduledDate
                + ", Stage=" + this.stageLocation + ", Seats=" + this.capacity + "]";
    }
}
