package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.Profile.UpdateProfileRequest;
import com.travel.resfeber.api.model.Profile.UpdateProfileResponse;
import com.travel.resfeber.api.model.Profile.ViewProfileResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookListRequest;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUpdateProfile {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetUpdateProfile onGetUpdateProfile;
    private UpdateProfileRequest updateProfileRequest;

    public GetUpdateProfile(Context context, UpdateProfileRequest updateProfileRequest, OnGetUpdateProfile onGetUpdateProfile) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.updateProfileRequest = updateProfileRequest;
        this.onGetUpdateProfile = onGetUpdateProfile;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getUpdateProfile(updateProfileRequest).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("update profile ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetUpdateProfile.onSuccess(response.body());
                    } else {
                        onGetUpdateProfile.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetUpdateProfile.onFail();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetUpdateProfile.onFail();
            }
        });

    }

    public interface OnGetUpdateProfile{
        void onSuccess(UpdateProfileResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
