package com.travel.resfeber.api.model.Profile;

import java.io.Serializable;

public class UserProfile implements Serializable {
    /**
     * BirthDate : String content
     * EmailId : String content
     * FirstName : String content
     * LastName : String content
     * MobileNo : String content
     * UserID : 9223372036854775807
     */

    private String BirthDate;
    private String EmailId;
    private String FirstName;
    private String LastName;
    private String MobileNo;
    private long UserID;

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String EmailId) {
        this.EmailId = EmailId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }
}
