package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.Feedback.FeedbackRequest;
import com.travel.resfeber.api.model.Feedback.FeedbackResponse;
import com.travel.resfeber.api.model.login.ForgotPasswordRequest;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetForgotPassword {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnForgotPassword onForgotPassword;
    private ForgotPasswordRequest forgotPasswordRequest;

    public GetForgotPassword(Context context, ForgotPasswordRequest forgotPasswordRequest, OnForgotPassword onForgotPassword) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.forgotPasswordRequest = forgotPasswordRequest;
        this.onForgotPassword = onForgotPassword;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getForgotPassword(forgotPasswordRequest).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("login", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onForgotPassword.onSuccess(response.body());
                    } else {
                        onForgotPassword.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onForgotPassword.onFail();
                }
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onForgotPassword.onFail();
            }
        });


    }

    public interface OnForgotPassword {
        void onSuccess(FeedbackResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
