package com.travel.resfeber.api.model.vehicle;

import java.util.List;

public class VehicleBookDetailResponse {

    /**
     * ResponseCode : 1
     * ResponseData : {"Data":[{"BookingID":11,"Description":null,"Destination":"Navsari, Gujarat, India","EDate":"/Date(1555304400000-0500)/","EventName":"","EventPlace":"","FDate":"/Date(1555218000000-0500)/","FromDate":"14-04-2019","IsCancel":false,"Source":"Vadodara, Gujarat, India","ToDate":"15-04-2019","TokenNumber":null,"TotalAmount":665.97,"UserID":"5","VehicalID":5,"VehicalName":"Ford-aspire","VehicalNumber":"0005"}]}
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
        private List<VehicleBook> Data;

        public List<VehicleBook> getData() {
            return Data;
        }

        public void setData(List<VehicleBook> Data) {
            this.Data = Data;
        }


    }
}
