package com.travel.resfeber.api.model.vehicle;

public class VehicleRateRequest {

    /**
     * Facility : AC
     * VehicalID : 2
     */

    private String Facility;
    private int VehicalID;

    public String getFacility() {
        return Facility;
    }

    public void setFacility(String Facility) {
        this.Facility = Facility;
    }

    public int getVehicalID() {
        return VehicalID;
    }

    public void setVehicalID(int VehicalID) {
        this.VehicalID = VehicalID;
    }
}
