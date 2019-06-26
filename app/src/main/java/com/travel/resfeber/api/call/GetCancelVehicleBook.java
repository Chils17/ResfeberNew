package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.vehicle.CancelVehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCancelVehicleBook {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetCancelVehicleBook onGetCancelVehicleBook;
    private VehicleBookDetailRequest vehicleBookDetailRequest;

    public GetCancelVehicleBook(Context context, VehicleBookDetailRequest vehicleBookDetailRequest, OnGetCancelVehicleBook onGetCancelVehicleBook) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.vehicleBookDetailRequest = vehicleBookDetailRequest;
        this.onGetCancelVehicleBook = onGetCancelVehicleBook;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getCancelVehicleBook(vehicleBookDetailRequest).enqueue(new Callback<CancelVehicleBookResponse>() {
            @Override
            public void onResponse(Call<CancelVehicleBookResponse> call, Response<CancelVehicleBookResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("vehicle book ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetCancelVehicleBook.onSuccess(response.body());
                    } else {
                        onGetCancelVehicleBook.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetCancelVehicleBook.onFail();
                }
            }

            @Override
            public void onFailure(Call<CancelVehicleBookResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetCancelVehicleBook.onFail();
            }
        });

    }

    public interface OnGetCancelVehicleBook {
        void onSuccess(CancelVehicleBookResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
