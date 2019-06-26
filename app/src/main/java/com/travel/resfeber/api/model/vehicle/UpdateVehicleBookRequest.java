package com.travel.resfeber.api.model.vehicle;

import com.google.gson.annotations.SerializedName;

public class UpdateVehicleBookRequest {

    /**
     * BookingID : 2
     * BookingStatus : Pending
     * Destination : Vadodara, Gujarat, India
     * Facility :
     * FromDate : 04-13-2019
     * Source : Surat, Gujarat, India
     * ToDate : 04-15-2019
     * TotalAmount : 0.0
     * VehicalID : 2
     */

    private int BookingID;
    private String BookingStatus;
    private String Destination;
    private String Facility;
    private String FromDate;
    private String Source;
    private String ToDate;
    private double TotalAmount;
    private int VehicalID;
    private String PickupTime;
    private String TripType;

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

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
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

    public int getVehicalID() {
        return VehicalID;
    }

    public void setVehicalID(int VehicalID) {
        this.VehicalID = VehicalID;
    }
}
