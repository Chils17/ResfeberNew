package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.login.LoginRequest;
import com.travel.resfeber.api.model.login.LoginResponse;
import com.travel.resfeber.api.model.register.RegisterRequest;
import com.travel.resfeber.api.model.register.RegisterResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRegister {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetRegister onGetRegister;
    private RegisterRequest registerRequest;

    public GetRegister(Context context, RegisterRequest registerRequest, OnGetRegister onGetRegister) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.registerRequest = registerRequest;
        this.onGetRegister = onGetRegister;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getRegister(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("register", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetRegister.onSuccess(response.body().getResponseData().getData().get(0));
                    } else {
                        onGetRegister.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetRegister.onFail();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetRegister.onFail();
            }
        });
    }

    public interface OnGetRegister {
        void onSuccess(Login data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
