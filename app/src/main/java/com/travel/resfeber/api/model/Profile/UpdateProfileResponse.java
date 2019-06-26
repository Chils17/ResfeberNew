package com.travel.resfeber.api.model.Profile;

import java.util.List;

public class UpdateProfileResponse {

    /**
     * ResponseCode : 2147483647
     * ResponseData : {"Data":[{"UserID":"String content"}]}
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
        private List<DataBean> Data;

        public List<DataBean> getData() {
            return Data;
        }

        public void setData(List<DataBean> Data) {
            this.Data = Data;
        }

        public static class DataBean {
            /**
             * UserID : String content
             */

            private String UserID;

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }
        }
    }
}
