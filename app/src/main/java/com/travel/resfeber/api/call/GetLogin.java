package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.login.LoginRequest;
import com.travel.resfeber.api.model.login.LoginResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetLogin {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetLogin onGetLogin;
    private LoginRequest loginRequest;

    public GetLogin(Context context, LoginRequest loginRequest, OnGetLogin onGetLogin) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.loginRequest = loginRequest;
        this.onGetLogin = onGetLogin;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);
        api.getLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("login", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetLogin.onSuccess(response.body().getResponseData().getData().get(0));
                    } else {
                        onGetLogin.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetLogin.onFail();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetLogin.onFail();
            }
        });


    }

    public interface OnGetLogin {
        void onSuccess(Login data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
