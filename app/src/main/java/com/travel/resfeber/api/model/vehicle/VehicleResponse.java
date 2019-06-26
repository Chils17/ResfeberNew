package com.travel.resfeber.api.model.vehicle;

import java.io.Serializable;
import java.util.List;

public class VehicleResponse implements Serializable {
    /**
     * ResponseCode : 1
     * ResponseData : {"Data":[{"DriverID":1,"DriverName":"Test123","EmailID":"test@test.com","Facility":"AC","MobileNo":"7485961230","NoSeat":"5","Rate":45,"VehicalID":2,"VehicalImage":"http://resfeber.somee.com/Admin/Images/Vehicals/11b3a456-509c-42c5-89a6-538c920a8c61_300x225x44574352-hyundai_xcent.jpg.pagespeed.ic.JacYs8Gj55.jpg","VehicalName":"Ex-cent","VehicalNumber":"0001","VehicalType":"Car"},{"DriverID":1,"DriverName":"Test123","EmailID":"test@test.com","Facility":"AC","MobileNo":"7485961230","NoSeat":"5","Rate":20.3,"VehicalID":3,"VehicalImage":"http://resfeber.somee.com/Admin/Images/Vehicals/d8c46483-f15b-4000-8529-dea063393f44_maruti-suzuki-dzire.jpg","VehicalName":"Dzire","VehicalNumber":"0002","VehicalType":"Car"},{"DriverID":1,"DriverName":"Test123","EmailID":"test@test.com","Facility":"AC","MobileNo":"7485961230","NoSeat":"7","Rate":45,"VehicalID":4,"VehicalImage":"http://resfeber.somee.com/Admin/Images/Vehicals/7c09b2c3-dad3-4201-9659-1b7484eee085_Toyota-Innova-Crysta-Facelift-Rendering-1.jpg","VehicalName":"Innova","VehicalNumber":"0003","VehicalType":"Car"},{"DriverID":1,"DriverName":"Test123","EmailID":"test@test.com","Facility":"AC","MobileNo":"7485961230","NoSeat":"5","Rate":50,"VehicalID":5,"VehicalImage":"http://resfeber.somee.com/Admin/Images/Vehicals/bd85d64b-aa0c-4e73-af70-ba98bf2c782a_Ford-Figo-Aspire-Facelift-Render-Featured.jpg","VehicalName":"Ford-aspire","VehicalNumber":"0005","VehicalType":"Car"},{"DriverID":1,"DriverName":"Test123","EmailID":"test@test.com","Facility":"AC","MobileNo":"7485961230","NoSeat":"5","Rate":50,"VehicalID":6,"VehicalImage":"http://resfeber.somee.com/Admin/Images/Vehicals/d4ac2ae6-2b89-454d-8519-d90630790ef6_99qsora_1420941.jpg","VehicalName":"Tata zest","VehicalNumber":"0006","VehicalType":"Car"},{"DriverID":1,"DriverName":"Test123","EmailID":"test@test.com","Facility":"AC","MobileNo":"7485961230","NoSeat":"14","Rate":60,"VehicalID":7,"VehicalImage":"http://resfeber.somee.com/Admin/Images/Vehicals/3dcd0852-c597-47cf-9e2a-e53e967c6325_Force Tempo Traveller 3700 Super.jpg","VehicalName":"Tempo traveller","VehicalNumber":"0006","VehicalType":"Car"},{"DriverID":1,"DriverName":"Test123","EmailID":"test@test.com","Facility":"AC","MobileNo":"7485961230","NoSeat":"25","Rate":60,"VehicalID":8,"VehicalImage":"http://resfeber.somee.com/Admin/Images/Vehicals/54b5a9e4-c582-4db0-a45e-5675bad7a68c_Tata-Winger-Hire-in-Pune-Mumbai-e1503724158694.jpg","VehicalName":"Mini bus","VehicalNumber":"0007","VehicalType":"Car"},{"DriverID":1,"DriverName":"Test123","EmailID":"test@test.com","Facility":"AC","MobileNo":"7485961230","NoSeat":"56","Rate":70,"VehicalID":9,"VehicalImage":"http://resfeber.somee.com/Admin/Images/Vehicals/3550e121-8b58-4e78-8e92-e17b850c40dd_LNAUSAN706ATLVBT-SP-2.jpg","VehicalName":"Luxury bus","VehicalNumber":"0008","VehicalType":"Car"}]}
     * ResponseMsg : Success
     */

    private int ResponseCode;
    private ResponseDataBean ResponseData;
    private String ResponseMsg;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    public ResponseDataBean getResponseData() {
        return ResponseData;
    }

    public void setResponseData(ResponseDataBean ResponseData) {
        this.ResponseData = ResponseData;
    }

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String ResponseMsg) {
        this.ResponseMsg = ResponseMsg;
    }

    public static class ResponseDataBean {
        private List<Vehicle> Data;

        public List<Vehicle> getData() {
            return Data;
        }

        public void setData(List<Vehicle> Data) {
            this.Data = Data;
        }

    }
}
