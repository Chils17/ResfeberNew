package com.travel.resfeber.api.model.Profile;

import java.util.List;

public class ViewProfileResponse {

    /**
     * ResponseCode : 2147483647
     * ResponseData : {"Data":[{"BirthDate":"String content","EmailId":"String content","FirstName":"String content","LastName":"String content","MobileNo":"String content","UserID":9223372036854775807}]}
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
        private List<UserProfile> Data;

        public List<UserProfile> getData() {
            return Data;
        }

        public void setData(List<UserProfile> Data) {
            this.Data = Data;
        }


    }
}
