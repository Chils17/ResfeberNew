package com.travel.resfeber.api.model.vehicle;

public class VehicleBookResponse {

    /**
     * ResponseCode : 1
     * ResponseMsg : Success
     * BookingID : 2
     * UserID : 5
     */

    private int ResponseCode;
    private String ResponseMsg;
    private int BookingID;
    private String UserID;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String ResponseMsg) {
        this.ResponseMsg = ResponseMsg;
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int BookingID) {
        this.BookingID = BookingID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }
}
