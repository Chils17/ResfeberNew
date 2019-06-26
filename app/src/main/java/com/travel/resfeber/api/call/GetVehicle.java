package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.register.RegisterRequest;
import com.travel.resfeber.api.model.register.RegisterResponse;
import com.travel.resfeber.api.model.vehicle.VehicleResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVehicle {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetVehicle onGetVehicle;

    public GetVehicle(Context context, OnGetVehicle onGetVehicle) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.onGetVehicle = onGetVehicle;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();
        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);
        api.getVehicle().enqueue(new Callback<VehicleResponse>() {
            @Override
            public void onResponse(Call<VehicleResponse> call, Response<VehicleResponse> response) {
                progressBarHelper.hideProgressDialog();
                Log.d("vehicle ", Function.jsonString(response.body()));
                if (response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        onGetVehicle.onSuccess(response.body());
                    } else {
                        onGetVehicle.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetVehicle.onFail();
                }
            }

            @Override
            public void onFailure(Call<VehicleResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetVehicle.onFail();
                Log.d("Data", "fail " + t.getMessage());
            }
        });

    }

    public interface OnGetVehicle {
        void onSuccess(VehicleResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
