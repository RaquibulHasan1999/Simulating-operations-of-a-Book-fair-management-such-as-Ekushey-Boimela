package com.cse213project.bookfair.Raquibul.dummy;

import java.io.Serializable;


public class OwnershipClaim implements Serializable {
    private String claimID;
    private String itemID;
    private String claimantName;
    private String claimantContact;
    private String proofDetails;
    private boolean verified;
    private String verifiedDate;

    public OwnershipClaim() {
        this.claimID = "";
        this.itemID = "";
        this.claimantName = "";
        this.claimantContact = "";
        this.proofDetails = "";
        this.verified = false;
        this.verifiedDate = "Pending";
    }

    public OwnershipClaim(String claimID, String itemID, String claimantName,
                          String claimantContact, String proofDetails, boolean verified, String verifiedDate) {
        this.claimID = claimID;
        this.itemID = itemID;
        this.claimantName = claimantName;
        this.claimantContact = claimantContact;
        this.proofDetails = proofDetails;
        this.verified = verified;
        this.verifiedDate = verifiedDate;
    }

    public String getClaimID() { return this.claimID; }
    public void setClaimID(String claimID) { this.claimID = claimID; }

    public String getItemID() { return this.itemID; }
    public void setItemID(String itemID) { this.itemID = itemID; }

    public String getClaimantName() { return this.claimantName; }
    public void setClaimantName(String claimantName) { this.claimantName = claimantName; }

    public String getClaimantContact() { return this.claimantContact; }
    public void setClaimantContact(String claimantContact) { this.claimantContact = claimantContact; }

    public String getProofDetails() { return this.proofDetails; }
    public void setProofDetails(String proofDetails) { this.proofDetails = proofDetails; }

    public boolean getVerified() { return this.verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public String getVerifiedDate() { return this.verifiedDate; }
    public void setVerifiedDate(String verifiedDate) { this.verifiedDate = verifiedDate; }

    @Override
    public String toString() {
        return "OwnershipClaim [ID=" + this.claimID + ", Item=" + this.itemID
                + ", Claimant=" + this.claimantName + ", Verified=" + this.verified + "]";
    }
}
