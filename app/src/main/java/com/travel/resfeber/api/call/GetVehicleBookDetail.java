package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVehicleBookDetail {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetVehicleBookDetail onGetVehicleBookDetail;
    private VehicleBookDetailRequest vehicleBookDetailRequest;

    public GetVehicleBookDetail(Context context, VehicleBookDetailRequest vehicleBookDetailRequest, OnGetVehicleBookDetail onGetVehicleBookDetail) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.vehicleBookDetailRequest = vehicleBookDetailRequest;
        this.onGetVehicleBookDetail = onGetVehicleBookDetail;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getVehicleBookDetail(vehicleBookDetailRequest).enqueue(new Callback<VehicleBookDetailResponse>() {
            @Override
            public void onResponse(Call<VehicleBookDetailResponse> call, Response<VehicleBookDetailResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("vehicle book ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetVehicleBookDetail.onSuccess(response.body());
                    } else {
                        onGetVehicleBookDetail.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetVehicleBookDetail.onFail();
                }
            }

            @Override
            public void onFailure(Call<VehicleBookDetailResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetVehicleBookDetail.onFail();
            }
        });

    }

    public interface OnGetVehicleBookDetail {
        void onSuccess(VehicleBookDetailResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
