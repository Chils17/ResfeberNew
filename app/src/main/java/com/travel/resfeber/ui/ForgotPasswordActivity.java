package com.travel.resfeber.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.travel.resfeber.R;
import com.travel.resfeber.api.call.GetForgotPassword;
import com.travel.resfeber.api.model.Feedback.FeedbackResponse;
import com.travel.resfeber.api.model.login.ForgotPasswordRequest;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfEditText;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.Function;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Context context;
    private TfButton btnSubmit;
    private TfEditText edtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initToolbar();
        init();
        actionListener();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Forgot Password");
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
        context = ForgotPasswordActivity.this;
        btnSubmit = (TfButton) findViewById(R.id.btnSubmit);
        edtMail = (TfEditText) findViewById(R.id.edtMail);

    }

    private void actionListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtMail.getText().toString().trim())) {
                    Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), edtMail.getText().toString())) {
                    Toast.makeText(context, "Email format invalid", Toast.LENGTH_SHORT).show();
                } else {
                    if (Function.checkNetworkConnection(context)) {
                        callForgotPasswordApi();
                    } else {
                        Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
                    }
                }
            }
        });
    }

    private void callForgotPasswordApi() {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmailId(edtMail.getText().toString().trim());

        new GetForgotPassword(context, forgotPasswordRequest, new GetForgotPassword.OnForgotPassword() {
            @Override
            public void onSuccess(FeedbackResponse data) {
                if (data != null) {
                    Log.d("Data", "Forgot pass " + Function.jsonString(data));
                    Function.showToast(context, data.getResponseMsg());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
