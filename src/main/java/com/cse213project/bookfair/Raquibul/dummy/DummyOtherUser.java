package com.cse213project.bookfair.Raquibul.dummy;

import com.cse213project.bookfair.Raquibul.model.User;

// Dummy placeholder user class for other group members (Users 3 to 8)
public class DummyOtherUser extends User {
    private String memberOwnerName;

    public DummyOtherUser() {
        this.userID = "";
        this.name = "";
        this.contact = "";
        this.passwordHash = "";
        this.role = "OtherUser";
        this.memberOwnerName = "";
    }

    public DummyOtherUser(String userID, String name, String contact, String passwordHash, String role, String memberOwnerName) {
        this.userID = userID;
        this.name = name;
        this.contact = contact;
        this.passwordHash = passwordHash;
        this.role = role;
        this.memberOwnerName = memberOwnerName;
    }

    public String getMemberOwnerName() {
        return this.memberOwnerName;
    }

    public void setMemberOwnerName(String memberOwnerName) {
        this.memberOwnerName = memberOwnerName;
    }

    @Override
    public String getDashboardFxml() {
        return "DummyUserDashboardView.fxml";
    }
}
