package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.vehicle.VehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.VehicleRateRequest;
import com.travel.resfeber.api.model.vehicle.VehicleRateResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVehicleBook {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetVehicleBook onGetVehicleBook;
    private VehicleBookRequest vehicleBookRequest;

    public GetVehicleBook(Context context, VehicleBookRequest vehicleBookRequest, OnGetVehicleBook onGetVehicleBook) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.vehicleBookRequest = vehicleBookRequest;
        this.onGetVehicleBook = onGetVehicleBook;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getVehicleBook(vehicleBookRequest).enqueue(new Callback<VehicleBookResponse>() {
            @Override
            public void onResponse(Call<VehicleBookResponse> call, Response<VehicleBookResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("vehicle book ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetVehicleBook.onSuccess(response.body());
                    } else {
                        onGetVehicleBook.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetVehicleBook.onFail();
                }
            }

            @Override
            public void onFailure(Call<VehicleBookResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetVehicleBook.onFail();
            }
        });

    }

    public interface OnGetVehicleBook {
        void onSuccess(VehicleBookResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
