package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUpdateVehicleBook {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetUpdateVehicleBook onGetUpdateVehicleBook;
    private UpdateVehicleBookRequest updateVehicleBookRequest;

    public GetUpdateVehicleBook(Context context, UpdateVehicleBookRequest updateVehicleBookRequest, OnGetUpdateVehicleBook onGetUpdateVehicleBook) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.updateVehicleBookRequest = updateVehicleBookRequest;
        this.onGetUpdateVehicleBook = onGetUpdateVehicleBook;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getUpdateVehicleBook(updateVehicleBookRequest).enqueue(new Callback<UpdateVehicleBookResponse>() {
            @Override
            public void onResponse(Call<UpdateVehicleBookResponse> call, Response<UpdateVehicleBookResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e(" update vehicle book ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetUpdateVehicleBook.onSuccess(response.body());
                    } else {
                        onGetUpdateVehicleBook.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetUpdateVehicleBook.onFail();
                }
            }

            @Override
            public void onFailure(Call<UpdateVehicleBookResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetUpdateVehicleBook.onFail();
            }
        });

    }

    public interface OnGetUpdateVehicleBook {
        void onSuccess(UpdateVehicleBookResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
