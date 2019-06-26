package com.travel.resfeber.api.model.vehicle;

import java.io.Serializable;

public class Vehicle implements Serializable {
    /**
     * DriverID : 1
     * DriverName : Test123
     * EmailID : test@test.com
     * Facility : AC
     * MobileNo : 7485961230
     * NoSeat : 5
     * Rate : 45
     * VehicalID : 2
     * VehicalImage : http://resfeber.somee.com/Admin/Images/Vehicals/11b3a456-509c-42c5-89a6-538c920a8c61_300x225x44574352-hyundai_xcent.jpg.pagespeed.ic.JacYs8Gj55.jpg
     * VehicalName : Ex-cent
     * VehicalNumber : 0001
     * VehicalType : Car
     */

    private int DriverID;
    private String DriverName;
    private String EmailID;
    private String Facility;
    private String MobileNo;
    private String NoSeat;
    private double Rate;
    private int VehicalID;
    private String VehicalImage;
    private String VehicalName;
    private String VehicalNumber;
    private String VehicalType;

    public int getDriverID() {
        return DriverID;
    }

    public void setDriverID(int DriverID) {
        this.DriverID = DriverID;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String DriverName) {
        this.DriverName = DriverName;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String EmailID) {
        this.EmailID = EmailID;
    }

    public String getFacility() {
        return Facility;
    }

    public void setFacility(String Facility) {
        this.Facility = Facility;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    public String getNoSeat() {
        return NoSeat;
    }

    public void setNoSeat(String NoSeat) {
        this.NoSeat = NoSeat;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double Rate) {
        this.Rate = Rate;
    }

    public int getVehicalID() {
        return VehicalID;
    }

    public void setVehicalID(int VehicalID) {
        this.VehicalID = VehicalID;
    }

    public String getVehicalImage() {
        return VehicalImage;
    }

    public void setVehicalImage(String VehicalImage) {
        this.VehicalImage = VehicalImage;
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

    public String getVehicalType() {
        return VehicalType;
    }

    public void setVehicalType(String VehicalType) {
        this.VehicalType = VehicalType;
    }

}
