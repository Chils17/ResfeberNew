package com.travel.resfeber.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.travel.resfeber.R;
import com.travel.resfeber.api.call.GetLogin;
import com.travel.resfeber.api.model.login.ForgotPasswordRequest;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.login.LoginRequest;
import com.travel.resfeber.api.model.login.LoginResponse;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfEditText;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.Preferences;


public class LoginActivity extends AppCompatActivity {

    private Context context;
    private LinearLayout llSignUp;
    private TfTextView txtForgotPassword;
    private TfButton btnSignIn;
    private TfEditText edtPassword, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        actionListener();
    }

    private void init() {
        context = LoginActivity.this;
        llSignUp = (LinearLayout) findViewById(R.id.llSignUp);
        txtForgotPassword = (TfTextView) findViewById(R.id.txtForgotPassword);
        btnSignIn = (TfButton) findViewById(R.id.btnSignIn);
        edtPassword = (TfEditText) findViewById(R.id.edtPassword);
        edtEmail = (TfEditText) findViewById(R.id.edtEmail);

        if (Function.checkNetworkConnection(context)) {
            if (Preferences.getInstance(context).getBoolean(Preferences.KEY_IS_AUTO_LOGIN)) {
                Function.fireIntent(context, ResfeberDrawerActivity.class);
            }
        } else {
            Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
        }

    }

    private void actionListener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                    Function.showMessage(context, getResources().getString(R.string.err_enter_email), false);
                } else if (!Function.isValidEmailAddress(edtEmail.getText().toString().trim())) {
                    Function.showMessage(context, getResources().getString(R.string.err_enter_valid_email_address), false);
                } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                    Function.showMessage(context, getResources().getString(R.string.err_enter_password), false);
                } /*else if (edtPassword.getText().toString().trim().length() < 6) {
                    Function.showMessage(context, getResources().getString(R.string.err_password_validation), false);
                } */ else {
                    if (Function.checkNetworkConnection(context)) {
                        loginApi();
                    } else {
                        Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
                    }
                }
            }
        });

        llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Function.fireIntent(context, RegisterActivity.class);
                finish();
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Function.showToast(context, "Coming Soon");
                Function.fireIntent(context, ForgotPasswordActivity.class);
            }
        });
    }

    private void loginApi() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(edtEmail.getText().toString().trim());
        loginRequest.setPassWord(edtPassword.getText().toString().trim());

        new GetLogin(context, loginRequest, new GetLogin.OnGetLogin() {
            @Override
            public void onSuccess(Login data) {
                onLoginSuccessResponse(data);
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

    private void onLoginSuccessResponse(Login data) {
        if (data != null) {
            Preferences.getInstance(context).setString(Preferences.KEY_USER_ID, String.valueOf(data.getUserID()));
            Preferences.getInstance(context).setString(Preferences.KEY_USER_LOGIN_EMAIL, edtEmail.getText().toString().trim().trim());
            Preferences.getInstance(context).setString(Preferences.KEY_USER_LOGIN_PASS_WORD, edtPassword.getText().toString().trim().trim());
            Preferences.getInstance(context).setBoolean(Preferences.KEY_IS_AUTO_LOGIN, true);
            Preferences.getInstance(context).setString(Preferences.KEY_USER_MODEL, new Gson().toJson(data));
            Function.fireIntent(context, ResfeberDrawerActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }
}
