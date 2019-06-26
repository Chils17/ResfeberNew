package com.travel.resfeber.api.model.register;

public class RegisterRequest {

    /**
     * BirthDate : 02-17-1991
     * EmailId : ckp17291@gmail.com
     * FirstName : Chirag
     * LastName : Patel
     * MobileNo : 8140421408
     * Password : chils
     */

    private String BirthDate;
    private String EmailId;
    private String FirstName;
    private String LastName;
    private String MobileNo;
    private String Password;

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
