package com.travel.resfeber.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.travel.resfeber.R;
import com.travel.resfeber.api.call.GetAddFeedback;
import com.travel.resfeber.api.model.Feedback.FeedbackRequest;
import com.travel.resfeber.api.model.Feedback.FeedbackResponse;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfEditText;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.Preferences;

public class FeedbackActivity extends AppCompatActivity {

    private Context context;
    private TfButton btnSubmit;
    private TfEditText edtFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initToolbar();
        init();
        actionListener();
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Feedback");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        context = FeedbackActivity.this;

        btnSubmit = (TfButton) findViewById(R.id.btnSubmit);
        edtFeedback = (TfEditText) findViewById(R.id.edtFeedback);
    }

    private void actionListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtFeedback.getText().toString().trim())) {
                    Function.showMessage(context, getResources().getString(R.string.err_enter_feedback), false);
                } else {
                    if (Function.checkNetworkConnection(context)) {
                        callAddFeedbackApi();
                    } else {
                        Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
                    }
                }

            }
        });
    }

    private void callAddFeedbackApi() {
        FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setUserID(Long.parseLong(Preferences.getInstance(context).getString(Preferences.KEY_USER_ID)));
        feedbackRequest.setFeedback(edtFeedback.getText().toString());

        new GetAddFeedback(context, feedbackRequest, new GetAddFeedback.OnGetAddFeedback() {
            @Override
            public void onSuccess(FeedbackResponse data) {
                if (data != null) {
                    Function.showToast(context, "Feedback send successfully");
                    finish();
                }
            }

            @Override
            public void onFail() {
                Function.showMessage(context, getResources().getString(R.string.err_something_went_wrong), false);
            }

            @Override
            public void onServerError(String responseMessage) {
                Function.showMessage(context, responseMessage, false);
            }
        });
    }

}
