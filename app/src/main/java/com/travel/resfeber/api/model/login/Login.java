package com.travel.resfeber.api.model.login;

import java.io.Serializable;

public class Login implements Serializable {
    /**
     * BirthDate : 2/17/1991 12:00:00 AM
     * EmailId : ckp17291@gmail.com
     * FirstName : Chirag
     * LastName : Patel
     * MobileNo : 8140421408
     * UserID : 5
     */

    private String BirthDate;
    private String EmailId;
    private String FirstName;
    private String LastName;
    private String MobileNo;
    private int UserID;

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

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

}
