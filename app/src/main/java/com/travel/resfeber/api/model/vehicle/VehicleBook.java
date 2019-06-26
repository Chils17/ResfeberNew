package com.travel.resfeber.api.model.vehicle;

import java.io.Serializable;

public class VehicleBook implements Serializable {
    /**
     * BookingID : 11
     * Description : null
     * Destination : Navsari, Gujarat, India
     * EDate : /Date(1555304400000-0500)/
     * EventName :
     * EventPlace :
     * FDate : /Date(1555218000000-0500)/
     * FromDate : 14-04-2019
     * IsCancel : false
     * Source : Vadodara, Gujarat, India
     * ToDate : 15-04-2019
     * TokenNumber : null
     * TotalAmount : 665.97
     * UserID : 5
     * VehicalID : 5
     * VehicalName : Ford-aspire
     * VehicalNumber : 0005
     */

    private int BookingID;
    private Object Description;
    private String Destination;
    private String EDate;
    private String EventName;
    private String EventPlace;
    private String FDate;
    private String FromDate;
    private boolean IsCancel;
    private String Source;
    private String ToDate;
    private Object TokenNumber;
    private double TotalAmount;
    private String UserID;
    private int VehicalID;
    private String VehicalName;
    private String VehicalNumber;

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int BookingID) {
        this.BookingID = BookingID;
    }

    public Object getDescription() {
        return Description;
    }

    public void setDescription(Object Description) {
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

    public Object getTokenNumber() {
        return TokenNumber;
    }

    public void setTokenNumber(Object TokenNumber) {
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

    public int getVehicalID() {
        return VehicalID;
    }

    public void setVehicalID(int VehicalID) {
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
