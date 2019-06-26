package com.travel.resfeber.api.model.Profile;

public class UpdateProfileRequest {

    /**
     * BirthDate : String content
     * FirstName : String content
     * LastName : String content
     * UserID : 9223372036854775807
     */

    private String BirthDate;
    private String FirstName;
    private String LastName;
    private long UserID;

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
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

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }
}
