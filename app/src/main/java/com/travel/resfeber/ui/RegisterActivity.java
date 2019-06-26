package com.travel.resfeber.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.travel.resfeber.R;
import com.travel.resfeber.api.call.GetRegister;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.register.RegisterRequest;
import com.travel.resfeber.api.model.register.RegisterResponse;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfEditText;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.Preferences;

public class RegisterActivity extends AppCompatActivity {

    private Context context;
    private TfButton btnCancel, btnSignUp;
    private TfEditText edtConfirmPassword, edtPassword, edtEmail, edtFirstName, edtLastName, edtDob, edtMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        actionListener();
    }

    private void init() {
        context = RegisterActivity.this;
        btnCancel = (TfButton) findViewById(R.id.btnCancel);
        btnSignUp = (TfButton) findViewById(R.id.btnSignUp);
        edtConfirmPassword = (TfEditText) findViewById(R.id.edtConfirmPassword);
        edtPassword = (TfEditText) findViewById(R.id.edtPassword);
        edtEmail = (TfEditText) findViewById(R.id.edtEmail);
        edtMobile = (TfEditText) findViewById(R.id.edtMobile);
        edtDob = (TfEditText) findViewById(R.id.edtDob);
        edtFirstName = (TfEditText) findViewById(R.id.edtFirstName);
        edtLastName = (TfEditText) findViewById(R.id.edtLastName);
    }

    private void actionListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Function.fireIntent(context, LoginActivity.class);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        if (TextUtils.isEmpty(edtFirstName.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_fisrst_name));
        } else if (TextUtils.isEmpty(edtLastName.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_last_name));
        } else if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_email));
        } else if (!Function.isValidEmailAddress(edtEmail.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_valid_email_address));
        } else if (TextUtils.isEmpty(edtDob.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_birth_date));
        } else if (TextUtils.isEmpty(edtMobile.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_mobile));
        } else if (edtMobile.getText().toString().trim().length() < 10) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_valid_mobile));
        } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_password));
        } else if (edtPassword.getText().toString().trim().length() < 6 && edtPassword.getText().toString().trim().length() > 12) {
            Function.showMessage(this, getResources().getString(R.string.err_password_validation), false);
        } else if (TextUtils.isEmpty(edtConfirmPassword.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_confirm_password));
        } else if (!edtPassword.getText().toString().trim().equalsIgnoreCase(edtConfirmPassword.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_not_match_password_confirm_password));
        } /*else if (TextUtils.isEmpty(edtAddressFirst.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_address_first));
        } else if (TextUtils.isEmpty(edtAddressSecond.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_address_second));
        } else if (selectedCountryID == 0) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_select_country));
        } else if (selectedStateId == 0) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_select_state));
        } else if (selectedCityId == 0) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_select_city));
        } else if (TextUtils.isEmpty(edtZipcode.getText().toString().trim())) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_enter_zipcode));
        } else if (selectedPlanId == 0) {
            Function.showAlertDialogWithOk(this, getString(R.string.err_registration_failed), getString(R.string.err_select_membership_plan));
        }*/ else {
            if (Function.checkNetworkConnection(RegisterActivity.this)) {
                registerApi();
            } else {
                Function.showMessage(RegisterActivity.this, getResources().getString(R.string.err_no_internet_connection), false);
            }
        }
    }

    private void registerApi() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName(edtFirstName.getText().toString().trim());
        registerRequest.setLastName(edtLastName.getText().toString().trim());
        registerRequest.setEmailId(edtEmail.getText().toString().trim());
        registerRequest.setBirthDate(Function.formatDateOfBirth(edtDob.getText().toString().trim()));
        registerRequest.setMobileNo(edtMobile.getText().toString().trim());
        registerRequest.setPassword(edtPassword.getText().toString().trim());

        new GetRegister(context, registerRequest, new GetRegister.OnGetRegister() {
            @Override
            public void onSuccess(Login data) {
                onSuccessofRegister(data);
            }

            @Override
            public void onFail() {
                Function.showMessage(RegisterActivity.this, getResources().getString(R.string.err_something_went_wrong), false);
            }

            @Override
            public void onServerError(String responseMessage) {
                Function.showMessage(RegisterActivity.this, responseMessage, false);
            }
        });
    }

    private void onSuccessofRegister(Login data) {
        Preferences.getInstance(context).setBoolean(Preferences.KEY_IS_AUTO_LOGIN, true);
        Preferences.getInstance(context).setString(Preferences.KEY_USER_MODEL, new Gson().toJson(data));
        Function.fireIntent(RegisterActivity.this, ResfeberDrawerActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
