package com.travel.resfeber.api.model.register;

import com.travel.resfeber.api.model.login.Login;

import java.util.List;

public class RegisterResponse {

    /**
     * ResponseCode : 1 successfully register
     * ResponseCode : 2
     * ResponseCode : 3
     * ResponseCode : 4 already register
     * ResponseData : {"Data":[{"BirthDate":"2/17/1991 12:00:00 AM","EmailId":"ckp17291@gmail.com","FirstName":"Chirag","LastName":"Patel","MobileNo":"8140421408","UserID":5}]}
     * ResponseMsg : Successfully Registred
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
        private List<Login> Data;

        public List<Login> getData() {
            return Data;
        }

        public void setData(List<Login> Data) {
            this.Data = Data;
        }

    }
}
