package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookListRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookListResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVehicleBookList {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetVehicleBookList onGetVehicleBookList;
    private VehicleBookListRequest vehicleBookListRequest;

    public GetVehicleBookList(Context context, VehicleBookListRequest vehicleBookListRequest, OnGetVehicleBookList onGetVehicleBookList) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.vehicleBookListRequest = vehicleBookListRequest;
        this.onGetVehicleBookList = onGetVehicleBookList;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getVehicleBookList(vehicleBookListRequest).enqueue(new Callback<VehicleBookListResponse>() {
            @Override
            public void onResponse(Call<VehicleBookListResponse> call, Response<VehicleBookListResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e(" update vehicle book ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetVehicleBookList.onSuccess(response.body());
                    } else {
                        onGetVehicleBookList.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetVehicleBookList.onFail();
                }
            }

            @Override
            public void onFailure(Call<VehicleBookListResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetVehicleBookList.onFail();
            }
        });

    }

    public interface OnGetVehicleBookList {
        void onSuccess(VehicleBookListResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
