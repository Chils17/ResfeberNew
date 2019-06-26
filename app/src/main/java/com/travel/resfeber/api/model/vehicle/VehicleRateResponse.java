package com.travel.resfeber.api.model.vehicle;

import java.util.List;

public class VehicleRateResponse {

    /**
     * ResponseCode : 1
     * ResponseData : {"Data":[{"Rate":15,"VehicalID":2}]}
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
             * Rate : 15
             * VehicalID : 2
             */

            private double Rate;
            private int VehicalID;

            public double getRate() {
                return Rate;
            }

            public void setRate(int Rate) {
                this.Rate = Rate;
            }

            public int getVehicalID() {
                return VehicalID;
            }

            public void setVehicalID(int VehicalID) {
                this.VehicalID = VehicalID;
            }
        }
    }
}
