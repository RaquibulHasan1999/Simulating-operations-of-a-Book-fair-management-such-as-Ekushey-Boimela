package com.cse213project.bookfair.Raquibul.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String userID;
    protected String name;
    protected String contact;
    protected String passwordHash;
    protected String role;

    public User() {
        this.userID = "";
        this.name = "";
        this.contact = "";
        this.passwordHash = "";
        this.role = "";
    }

    public User(String userID, String name, String contact, String passwordHash, String role) {
        this.userID = userID;
        this.name = name;
        this.contact = contact;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public boolean login(String inputUserID, String inputPassword) {
        if (inputUserID == null || inputPassword == null) {
            return false;
        }
        return this.userID.equals(inputUserID) && this.passwordHash.equals(inputPassword);
    }

    public void signOut() {
        System.out.println("User " + this.userID + " signed out.");
    }

    public boolean updateProfile(String newName, String newContact) {
        if (newName == null || newName.equals("") || newContact == null || newContact.equals("")) {
            return false;
        }
        this.name = newName;
        this.contact = newContact;
        return true;
    }

    public abstract String getDashboardFxml();

    public String getUserID() { return this.userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return this.contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getPasswordHash() { return this.passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return this.role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof User) {
            User other = (User) obj;
            return this.userID.equals(other.userID);
        }
        return false;
    }

    @Override
    public String toString() {
        return "User [ID=" + this.userID + ", Name=" + this.name + ", Role=" + this.role + "]";
    }
}
