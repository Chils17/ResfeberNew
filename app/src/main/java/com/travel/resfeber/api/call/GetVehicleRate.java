package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.register.RegisterRequest;
import com.travel.resfeber.api.model.register.RegisterResponse;
import com.travel.resfeber.api.model.vehicle.VehicleRateRequest;
import com.travel.resfeber.api.model.vehicle.VehicleRateResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVehicleRate {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetVehicleRate getVehicleRate;
    private VehicleRateRequest vehicleRateRequest;

    public GetVehicleRate(Context context, VehicleRateRequest vehicleRateRequest, OnGetVehicleRate getVehicleRate) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.vehicleRateRequest = vehicleRateRequest;
        this.getVehicleRate = getVehicleRate;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getVehicleRate(vehicleRateRequest).enqueue(new Callback<VehicleRateResponse>() {
            @Override
            public void onResponse(Call<VehicleRateResponse> call, Response<VehicleRateResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("vehicle rate ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        getVehicleRate.onSuccess(response.body());
                    } else {
                        getVehicleRate.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    getVehicleRate.onFail();
                }
            }

            @Override
            public void onFailure(Call<VehicleRateResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                getVehicleRate.onFail();
            }
        });

    }

    public interface OnGetVehicleRate {
        void onSuccess(VehicleRateResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
