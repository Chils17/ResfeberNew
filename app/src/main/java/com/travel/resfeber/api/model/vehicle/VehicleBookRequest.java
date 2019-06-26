package com.travel.resfeber.api.model.vehicle;

import com.google.gson.annotations.SerializedName;

public class VehicleBookRequest {

    /**
     * BookingID : 0
     * BookingStatus : Pending
     * Description :
     * Destination : Vadodara, Gujarat, India
     * EventName :
     * EventPlace :
     * Facility : AC
     * FromDate : 04-13-19
     * Source : Surat, Gujarat, India
     * ToDate : 04-15-19
     * TotalAmount : 0
     * UserID : 5
     * VehicalID : 2
     */

    private int BookingID;
    private String BookingStatus;
    private String Description;
    private String Destination;
    private String EventName;
    private String EventPlace;
    private String Facility;
    private String FromDate;
    private String Source;
    private String ToDate;
    private double TotalAmount;
    private String UserID;
    private int VehicalID;
    @SerializedName("PickupTime")
    private String pickUpTime;
    @SerializedName("TripType")
    private String trip;

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int BookingID) {
        this.BookingID = BookingID;
    }

    public String getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(String BookingStatus) {
        this.BookingStatus = BookingStatus;
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

    public String getFacility() {
        return Facility;
    }

    public void setFacility(String Facility) {
        this.Facility = Facility;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
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

    public int getVehicalID() {
        return VehicalID;
    }

    public void setVehicalID(int VehicalID) {
        this.VehicalID = VehicalID;
    }
}
