package com.travel.resfeber.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.travel.resfeber.R;
import com.travel.resfeber.api.call.GetUpdateProfile;
import com.travel.resfeber.api.model.Profile.UpdateProfileRequest;
import com.travel.resfeber.api.model.Profile.UpdateProfileResponse;
import com.travel.resfeber.api.model.Profile.UserProfile;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.vehicle.Vehicle;
import com.travel.resfeber.api.model.vehicle.VehicleBookOrder;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfEditText;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.Preferences;

public class EditUserProfileActivity extends AppCompatActivity {

    private Context context;
    private TfButton btnUpdate;
    private UserProfile userProfile;
    private TfEditText edtFirstName, edtBirthDate, edtMobile, edtEmail, edtLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);


        getIntentData();
        initToolbar();
        init();
        actionListener();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            userProfile = (UserProfile) getIntent().getSerializableExtra(AppConstant.INTENT_USER_PROFILE);

        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Edit User Profile");
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
        context = EditUserProfileActivity.this;

        edtBirthDate = (TfEditText) findViewById(R.id.edtBirthDate);
        edtMobile = (TfEditText) findViewById(R.id.edtMobile);
        edtEmail = (TfEditText) findViewById(R.id.edtEmail);
        edtLastName = (TfEditText) findViewById(R.id.edtLastName);
        edtFirstName = (TfEditText) findViewById(R.id.edtFirstName);
        btnUpdate = (TfButton) findViewById(R.id.btnUpdate);

        if (userProfile != null) {
            edtFirstName.setText(userProfile.getFirstName());
            edtLastName.setText(userProfile.getLastName());
            edtEmail.setText(userProfile.getEmailId());
            edtMobile.setText(userProfile.getMobileNo());

            edtBirthDate.setText(userProfile.getBirthDate());

            setEnable(edtFirstName);
            setEnable(edtLastName);
            setDisable(edtEmail);
            setDisable(edtMobile);
            setEnable(edtBirthDate);
        }
    }

    private void setDisable(TfEditText et) {
        et.setFocusable(false);
        et.setClickable(true);
    }

    private void setEnable(TfEditText et) {
        et.setFocusable(true);
        et.setClickable(false);
    }

    private void actionListener() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Function.checkNetworkConnection(context)) {
                    callUpdateUserDetailApi();
                } else {
                    Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
                }
            }
        });
    }

    private void callUpdateUserDetailApi() {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setFirstName(edtFirstName.getText().toString().trim());
        updateProfileRequest.setLastName(edtLastName.getText().toString().trim());
        updateProfileRequest.setBirthDate(Function.formatDate(edtBirthDate.getText().toString().trim()));
        updateProfileRequest.setUserID(userProfile.getUserID());

        new GetUpdateProfile(context, updateProfileRequest, new GetUpdateProfile.OnGetUpdateProfile() {
            @Override
            public void onSuccess(UpdateProfileResponse data) {
                if (data != null) {
                    Function.showToast(context, "Update profile successfully");
                    Function.fireIntent(context, ResfeberDrawerActivity.class);
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
