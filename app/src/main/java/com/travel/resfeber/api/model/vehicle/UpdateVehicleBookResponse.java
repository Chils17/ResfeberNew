package com.travel.resfeber.api.model.vehicle;

import java.util.List;

public class UpdateVehicleBookResponse {

    /**
     * ResponseCode : 1
     * ResponseData : {"Data":[{"BookingID":2}]}
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
        private List<DataBean> Data;

        public List<DataBean> getData() {
            return Data;
        }

        public void setData(List<DataBean> Data) {
            this.Data = Data;
        }

        public static class DataBean {
            /**
             * BookingID : 2
             */

            private int BookingID;

            public int getBookingID() {
                return BookingID;
            }

            public void setBookingID(int BookingID) {
                this.BookingID = BookingID;
            }
        }
    }
}

