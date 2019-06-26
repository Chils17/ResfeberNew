package com.travel.resfeber.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.travel.resfeber.R;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.fragments.BookingFragment;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.Preferences;

public class ResfeberDrawerActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private TfTextView txtName, txtHome, txtLogOut, txtEmail, txtVehicleBooking, txtEventBooking, txtViewOrder, txtContactUs;
    private Login userLoginModel;
    private Context context;
    /**
     * current Instance of home activity
     */
    private static ResfeberDrawerActivity dashboardActivity;
    private LinearLayout llHeader;
    private TfTextView txtFeedback, txtHelp;

    /**
     * Gets home activity.
     *
     * @return the home activity
     */
    public static ResfeberDrawerActivity getHomeActivity() {
        return dashboardActivity;
    }

    /**
     * Sets home activity.
     *
     * @param dashboardActivity the home activity
     */
    public static void setHomeActivity(ResfeberDrawerActivity dashboardActivity) {
        ResfeberDrawerActivity.dashboardActivity = dashboardActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resfeber_drawer);
        setHomeActivity(this);
        userLoginModel = new Gson().fromJson(Preferences.getInstance(ResfeberDrawerActivity.this).getString(Preferences.KEY_USER_MODEL), Login.class);

        initToolbar();
        init();
        actionListener();

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    private void selectItem(int position) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getFragments().clear();
        if (position == 0) {
            Fragment fragmentToPush = BookingFragment.getFragment(this);
            pushAddFragments(fragmentToPush, false, true);
        }
    }


    private void init() {
        context = ResfeberDrawerActivity.this;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        txtName = (TfTextView) findViewById(R.id.txtName);
        txtEmail = (TfTextView) findViewById(R.id.txtEmail);
        txtHome = (TfTextView) findViewById(R.id.txtHome);
        txtVehicleBooking = (TfTextView) findViewById(R.id.txtVehicleBooking);
        txtEventBooking = (TfTextView) findViewById(R.id.txtEventBooking);
        txtViewOrder = (TfTextView) findViewById(R.id.txtViewOrder);
        txtFeedback = (TfTextView) findViewById(R.id.txtFeedback);
        txtContactUs = (TfTextView) findViewById(R.id.txtContactUs);
        txtHelp = (TfTextView) findViewById(R.id.txtHelp);
        txtLogOut = (TfTextView) findViewById(R.id.txtLogOut);

        llHeader = (LinearLayout) findViewById(R.id.llHeader);

        if (userLoginModel != null) {
            txtName.setText(userLoginModel.getFirstName());
            txtEmail.setText(userLoginModel.getEmailId());
        }

        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        txtTitle.setText(getResources().getString(R.string.app_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_toolbar);
    }

    private void actionListener() {
        llHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // drawerLayout.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(context, UserProfileActivity.class);
                startActivity(intent);
            }
        });
        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });

        /*txtVehicleBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Function.fireIntent(context, BookingActivity.class);
            }
        });*/

        txtVehicleBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra(AppConstant.INTENT_EVENT, false);
                startActivity(intent);
            }
        });

        txtEventBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra(AppConstant.INTENT_EVENT, true);
                startActivity(intent);
            }
        });

        txtViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewOrderActivity.class);
                //   intent.putExtra(AppConstant.INTENT_EVENT, true);
                startActivity(intent);
            }
        });

        txtFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        txtContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactUsActivity.class);
                startActivity(intent);
            }
        });

        txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HelpActivity.class);
                startActivity(intent);
            }
        });

        txtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Function.showAlertDialogWithYesNo(context, getResources().getString(R.string.dialog_logout), new Function.DialogOptionsSelectedListener() {
                    @Override
                    public void onSelect(boolean isYes) {
                        if (isYes) {
                            logout();
                        }
                    }
                });
            }
        });
    }

    private void logout() {
        Preferences.getInstance(context).clearAll();
        Function.fireIntentWithClearFlagWithWithPendingTransition(ResfeberDrawerActivity.this, SplashActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void onDestroy() {
        setHomeActivity(null);
        super.onDestroy();
    }
}
