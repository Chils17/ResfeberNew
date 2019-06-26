package com.travel.resfeber.api.model.vehicle;

import java.io.Serializable;

public class VehicleBookOrder implements Serializable {
    /**
     * BookingID : 9223372036854775807
     * Description : String content
     * Destination : String content
     * EDate : /Date(928167600000-0500)/
     * EventName : String content
     * EventPlace : String content
     * FDate : /Date(928167600000-0500)/
     * FromDate : String content
     * IsCancel : true
     * Source : String content
     * ToDate : String content
     * TokenNumber : String content
     * TotalAmount : 1.2678967543233E7
     * UserID : String content
     * VehicalID : 9223372036854775807
     * VehicalName : String content
     * VehicalNumber : String content
     */

    private long BookingID;
    private String Description;
    private String Destination;
    private String EDate;
    private String EventName;
    private String EventPlace;
    private String FDate;
    private String FromDate;
    private boolean IsCancel;
    private String PickupTime;
    private String Source;
    private String ToDate;
    private String TokenNumber;
    private double TotalAmount;
    private String TripType;
    private String UserID;
    private long VehicalID;
    private String VehicalName;
    private String VehicalNumber;

    public boolean isCancel() {
        return IsCancel;
    }

    public void setCancel(boolean cancel) {
        IsCancel = cancel;
    }

    public String getPickupTime() {
        return PickupTime;
    }

    public void setPickupTime(String pickupTime) {
        PickupTime = pickupTime;
    }

    public String getTripType() {
        return TripType;
    }

    public void setTripType(String tripType) {
        TripType = tripType;
    }

    public long getBookingID() {
        return BookingID;
    }

    public void setBookingID(long BookingID) {
        this.BookingID = BookingID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    public String getEDate() {
        return EDate;
    }

    public void setEDate(String EDate) {
        this.EDate = EDate;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public String getEventPlace() {
        return EventPlace;
    }

    public void setEventPlace(String EventPlace) {
        this.EventPlace = EventPlace;
    }

    public String getFDate() {
        return FDate;
    }

    public void setFDate(String FDate) {
        this.FDate = FDate;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }

    public boolean isIsCancel() {
        return IsCancel;
    }

    public void setIsCancel(boolean IsCancel) {
        this.IsCancel = IsCancel;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }

    public String getTokenNumber() {
        return TokenNumber;
    }

    public void setTokenNumber(String TokenNumber) {
        this.TokenNumber = TokenNumber;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public long getVehicalID() {
        return VehicalID;
    }

    public void setVehicalID(long VehicalID) {
        this.VehicalID = VehicalID;
    }

    public String getVehicalName() {
        return VehicalName;
    }

    public void setVehicalName(String VehicalName) {
        this.VehicalName = VehicalName;
    }

    public String getVehicalNumber() {
        return VehicalNumber;
    }

    public void setVehicalNumber(String VehicalNumber) {
        this.VehicalNumber = VehicalNumber;
    }
}
