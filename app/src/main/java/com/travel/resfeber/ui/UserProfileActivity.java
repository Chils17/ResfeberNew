package com.travel.resfeber.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.travel.resfeber.R;
import com.travel.resfeber.api.call.GetViewProfile;
import com.travel.resfeber.api.model.Profile.UserProfile;
import com.travel.resfeber.api.model.Profile.ViewProfileResponse;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.vehicle.VehicleBookListRequest;
import com.travel.resfeber.custom.TfEditText;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.Preferences;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private Context context;
    //   private Login userLoginModel;
    private ImageView imgEdit;
    private TfEditText edtFirstName, edtBirthDate, edtMobile, edtEmail, edtLastName;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //  userLoginModel = new Gson().fromJson(Preferences.getInstance(getApplicationContext()).getString(Preferences.KEY_USER_MODEL), Login.class);


        initToolbar();
        init();
        actionListener();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        imgEdit = (ImageView) findViewById(R.id.imgEdit);
        imgEdit.setVisibility(View.VISIBLE);
        txtTitle.setText("User Profile");
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
        context = UserProfileActivity.this;

        edtBirthDate = (TfEditText) findViewById(R.id.edtBirthDate);
        edtMobile = (TfEditText) findViewById(R.id.edtMobile);
        edtEmail = (TfEditText) findViewById(R.id.edtEmail);
        edtLastName = (TfEditText) findViewById(R.id.edtLastName);
        edtFirstName = (TfEditText) findViewById(R.id.edtFirstName);


        setEnable(edtFirstName);
        setEnable(edtLastName);
        setEnable(edtEmail);
        setEnable(edtMobile);
        setEnable(edtBirthDate);

        checkInternetConnection();
    }

    private void checkInternetConnection() {
        if (Function.checkNetworkConnection(context)) {
            callViewProfileApi();
        } else {
            Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
        }
    }

    private void callViewProfileApi() {
        VehicleBookListRequest viewProfileRequest = new VehicleBookListRequest();
        viewProfileRequest.setUserID(Preferences.getInstance(context).getString(Preferences.KEY_USER_ID));

        new GetViewProfile(context, viewProfileRequest, new GetViewProfile.OnGetViewProfile() {
            @Override
            public void onSuccess(ViewProfileResponse data) {
                if (data != null) {
                    userProfile = data.getResponseData().getData().get(0);
                    if (userProfile != null) {
                        edtFirstName.setText(userProfile.getFirstName());
                        edtLastName.setText(userProfile.getLastName());
                        edtEmail.setText(userProfile.getEmailId());
                        edtMobile.setText(userProfile.getMobileNo());
                       // Log.d("Data", "Birthdate " + userProfile.getBirthDate());

                        edtBirthDate.setText(userProfile.getBirthDate());


                    }
                } else {

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

    private void setEnable(TfEditText et) {
        et.setFocusable(false);
        et.setClickable(true);
    }

    private void actionListener() {
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditUserProfileActivity.class);
                intent.putExtra(AppConstant.INTENT_USER_PROFILE, userProfile);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
