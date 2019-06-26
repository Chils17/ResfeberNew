package com.travel.resfeber.api.model.vehicle;

import java.util.List;

public class VehicleBookListResponse {

    /**
     * ResponseCode : 2147483647
     * ResponseData : {"Data":[{"BookingID":9223372036854775807,"Description":"String content","Destination":"String content","EDate":"/Date(928167600000-0500)/","EventName":"String content","EventPlace":"String content","FDate":"/Date(928167600000-0500)/","FromDate":"String content","IsCancel":true,"Source":"String content","ToDate":"String content","TokenNumber":"String content","TotalAmount":1.2678967543233E7,"UserID":"String content","VehicalID":9223372036854775807,"VehicalName":"String content","VehicalNumber":"String content"}]}
     * ResponseMsg : String content
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
        private List<VehicleBookOrder> Data;

        public List<VehicleBookOrder> getData() {
            return Data;
        }

        public void setData(List<VehicleBookOrder> Data) {
            this.Data = Data;
        }


    }
}
