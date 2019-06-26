package com.travel.resfeber.api.call;

import android.content.Context;
import android.util.Log;

import com.travel.resfeber.api.ApiCalling;
import com.travel.resfeber.api.model.Feedback.FeedbackRequest;
import com.travel.resfeber.api.model.Feedback.FeedbackResponse;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.login.LoginRequest;
import com.travel.resfeber.api.model.login.LoginResponse;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.ProgressBarHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAddFeedback {
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private OnGetAddFeedback onGetAddFeedback;
    private FeedbackRequest feedbackRequest;

    public GetAddFeedback(Context context, FeedbackRequest feedbackRequest, OnGetAddFeedback onGetAddFeedback) {
        progressBarHelper = new ProgressBarHelper(context, false);
        this.feedbackRequest = feedbackRequest;
        this.onGetAddFeedback = onGetAddFeedback;
        this.context = context;
        call();
    }

    private void call() {
        progressBarHelper.showProgressDialog();

        ApiCalling api = MyApplication.getRetrofit().create(ApiCalling.class);

        api.getAddFeedback(feedbackRequest).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                progressBarHelper.hideProgressDialog();
                if (response.body() != null) {
                    Log.e("feedback ", Function.jsonString(response.body()));
                    if (response.body().getResponseCode() == 1) {
                        onGetAddFeedback.onSuccess(response.body());
                    } else {
                        onGetAddFeedback.onServerError(response.body().getResponseMsg());
                    }

                } else {
                    onGetAddFeedback.onFail();
                }
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                progressBarHelper.hideProgressDialog();
                onGetAddFeedback.onFail();
                Log.d("Data", "Fail " + t.getMessage());
            }
        });


    }

    public interface OnGetAddFeedback {
        void onSuccess(FeedbackResponse data);

        void onFail();

        void onServerError(String responseMessage);
    }
}
