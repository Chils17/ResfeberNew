package com.travel.resfeber.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.travel.resfeber.R;
import com.travel.resfeber.adapter.VehicleAdapter;
import com.travel.resfeber.adapter.VehicleBookAdapter;
import com.travel.resfeber.api.call.GetVehicleBookList;
import com.travel.resfeber.api.model.vehicle.VehicleBookListRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookListResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookOrder;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.Preferences;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderActivity extends AppCompatActivity {

    private Context context;
    private TfTextView txtAlert;
    private RecyclerView rvOrderVehicle;
    private List<VehicleBookOrder> vehicleBookOrderList;
    private VehicleBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        intToolbar();
        init();
        actionListener();
    }

    private void intToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Vehicle Orders");
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
        context = ViewOrderActivity.this;
        txtAlert = (TfTextView) findViewById(R.id.txtAlert);
        rvOrderVehicle = (RecyclerView) findViewById(R.id.rvOrderVehicle);

        intiRecyclerView();
    }

    private void intiRecyclerView() {
        vehicleBookOrderList = new ArrayList<>();

        rvOrderVehicle.setLayoutManager(new LinearLayoutManager(context));

        rvOrderVehicle.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 10);
            }
        });

        rvOrderVehicle.setItemAnimator(new DefaultItemAnimator());

        adapter = new VehicleBookAdapter(context, vehicleBookOrderList);
        rvOrderVehicle.setAdapter(adapter);

        checkInternetConnection();

    }

    private void checkInternetConnection() {
        if (Function.checkNetworkConnection(context)) {
            callVehicleBookApi();
        } else {
            Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
        }
    }

    private void callVehicleBookApi() {
        VehicleBookListRequest vehicleBookListRequest=new VehicleBookListRequest();
        vehicleBookListRequest.setUserID(Preferences.getInstance(context).getString(Preferences.KEY_USER_ID));

        new GetVehicleBookList(context, vehicleBookListRequest, new GetVehicleBookList.OnGetVehicleBookList() {
            @Override
            public void onSuccess(VehicleBookListResponse data) {
                if (data != null && data.getResponseData().getData().size() > 0) {
                    Log.d("vehicle ", Function.jsonString(data.getResponseData().getData()));
                    txtAlert.setVisibility(View.GONE);
                    rvOrderVehicle.setVisibility(View.VISIBLE);
                    vehicleBookOrderList.addAll(data.getResponseData().getData());
                    adapter.setDataList(vehicleBookOrderList);
                } else {
                    txtAlert.setVisibility(View.VISIBLE);
                    rvOrderVehicle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail() {
                txtAlert.setVisibility(View.VISIBLE);
                rvOrderVehicle.setVisibility(View.GONE);
                Function.showMessage(context, getResources().getString(R.string.err_something_went_wrong), false);
            }

            @Override
            public void onServerError(String responseMessage) {
                txtAlert.setVisibility(View.VISIBLE);
                rvOrderVehicle.setVisibility(View.GONE);
                Function.showMessage(context, responseMessage, false);
            }
        });
    }


    private void actionListener() {

    }
}
