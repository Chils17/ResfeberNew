package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.Feedback.FeedbackRequest;
import com.travel.resfeber.api.model.Feedback.FeedbackResponse;
import com.travel.resfeber.api.model.Profile.ViewProfileResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookListRequest;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetViewProfile {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetViewProfile onGetViewProfile;
    private VehicleBookListRequest viewProfileRequest;

    public GetViewProfile(Context context, VehicleBookListRequest viewProfileRequest, OnGetViewProfile onGetViewProfile) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.viewProfileRequest = viewProfileRequest;
        this.onGetViewProfile = onGetViewProfile;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getViewProfile(viewProfileRequest).enqueue(new Callback<ViewProfileResponse>() {
            @Override
            public void onResponse(Call<ViewProfileResponse> call, Response<ViewProfileResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("view profile ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetViewProfile.onSuccess(response.body());
                    } else {
                        onGetViewProfile.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetViewProfile.onFail();
                }
            }

            @Override
            public void onFailure(Call<ViewProfileResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetViewProfile.onFail();
            }
        });

    }

    public interface OnGetViewProfile{
        void onSuccess(ViewProfileResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
