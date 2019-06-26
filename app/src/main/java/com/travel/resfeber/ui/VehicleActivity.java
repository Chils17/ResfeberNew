package com.travel.resfeber.ui;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.travel.resfeber.R;
import com.travel.resfeber.adapter.VehicleAdapter;
import com.travel.resfeber.api.call.GetVehicle;
import com.travel.resfeber.api.model.vehicle.Vehicle;
import com.travel.resfeber.api.model.vehicle.VehicleResponse;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;

import java.util.ArrayList;
import java.util.List;

public class VehicleActivity extends AppCompatActivity {

    private Context context;
    private TfTextView txtAlert;
    private RecyclerView rvVehicle;
    private List<Vehicle> vehicleList;
    private VehicleAdapter adapter;
    private String source, destination, startDate, endDate, distance, eventName, pickupTime;
    private String trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        geIntentData();
        intToolbar();
        init();
        actionListener();
    }

    private void geIntentData() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            source = extras.getString(AppConstant.INTENT_SOURCE, "");
            destination = extras.getString(AppConstant.INTENT_DESTINATION, "");
            startDate = extras.getString(AppConstant.INTENT_START_DATE, "");
            endDate = extras.getString(AppConstant.INTENT_END_DATE, "");
            distance = extras.getString(AppConstant.INTENT_DISTANCE, "");
            pickupTime = extras.getString(AppConstant.INTENT_PICKUP_TIME, "");
            eventName = extras.getString(AppConstant.INTENT_EVENT_NAME, "");
            trip = extras.getString(AppConstant.INTENT_TRIP, "");

            Log.d("Data", "eventName " + eventName);
        }
    }

    private void intToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Vehicle Selection");
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
        context = VehicleActivity.this;
        txtAlert = (TfTextView) findViewById(R.id.txtAlert);
        rvVehicle = (RecyclerView) findViewById(R.id.rvVehicle);

        intiRecyclerView();
    }

    private void intiRecyclerView() {
        vehicleList = new ArrayList<>();

       /* vehicleList.add(new Vehicle(0,"Ex-cent","Car","0001","5","AC","10 ₹","https://www.drivespark.com/car-image/640x480x100/car/300x225x44574352-hyundai_xcent.jpg.pagespeed.ic.JacYs8Gj55.jpg"));
        vehicleList.add(new Vehicle(1,"Dzire","Car","0002","5","AC","11 ₹","https://media.zigcdn.com/media/model/2017/May/dire_600x300.jpg"));
        vehicleList.add(new Vehicle(2,"Innova","Car","0003","7","AC","12 ₹","https://www.indiacarnews.com/wp-content/uploads/2018/07/Toyota-Innova-Crysta-Facelift-Rendering-1.jpg"));
        vehicleList.add(new Vehicle(3,"Tavera","Car","0004","10","AC","11 ₹","https://www.prayaanam.in/46fa69e4/uploads/45827158Tavera-Prayaanam-Car-Hire.jpg"));
        vehicleList.add(new Vehicle(4,"Ford-aspire","Car","0005","5","AC","12 ₹","https://imgd.aeplcdn.com/1280x720/cw/ec/35583/Ford-Aspire-Exterior-137688.jpg?wm=0&q=100"));
        vehicleList.add(new Vehicle(5,"Tata zest","Car","0006","5","AC","11 ₹","https://imgd.aeplcdn.com/0x0/n/99qsora_1420941.jpg"));
        vehicleList.add(new Vehicle(6,"Tempo traveller","Car","0006","14","AC","12 ₹","https://www.trucksbuses.com/uploads/Force%20Tempo%20Traveller%203700%20Super.jpg"));
        vehicleList.add(new Vehicle(7,"Mini bus","Car","0007","25","AC","20 ₹","https://punetours.com/wp-content/uploads/2017/01/Tata-Winger-Hire-in-Pune-Mumbai-e1503724158694.jpg"));
        vehicleList.add(new Vehicle(8,"Luxury bus","Car","0008","56","AC","20 ₹","https://www.toursales.com/v/vspfiles/photos/LNAUSAN706ATLVBT-SP-2.jpg"));
*/
        rvVehicle.setLayoutManager(new LinearLayoutManager(context));

        rvVehicle.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 10);
            }
        });

        rvVehicle.setItemAnimator(new DefaultItemAnimator());

        adapter = new VehicleAdapter(context, vehicleList, "", source, destination, startDate, endDate, distance, eventName, pickupTime, trip);
        rvVehicle.setAdapter(adapter);

        checkInternetConnection();

    }

    private void checkInternetConnection() {
        if (Function.checkNetworkConnection(context)) {
            callVehicleApi();
        } else {
            Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
        }
    }

    private void callVehicleApi() {
        new GetVehicle(context, new GetVehicle.OnGetVehicle() {
            @Override
            public void onSuccess(VehicleResponse data) {
                if (data != null && data.getResponseData().getData().size() > 0) {
                    Log.d("vehicle ", Function.jsonString(data.getResponseData().getData()));
                    txtAlert.setVisibility(View.GONE);
                    rvVehicle.setVisibility(View.VISIBLE);
                    vehicleList.addAll(data.getResponseData().getData());
                    adapter.setDataList(vehicleList);
                } else {
                    txtAlert.setVisibility(View.VISIBLE);
                    rvVehicle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail() {
                txtAlert.setVisibility(View.VISIBLE);
                rvVehicle.setVisibility(View.GONE);
                Function.showMessage(context, getResources().getString(R.string.err_something_went_wrong), false);
            }

            @Override
            public void onServerError(String responseMessage) {
                txtAlert.setVisibility(View.VISIBLE);
                rvVehicle.setVisibility(View.GONE);
                Function.showMessage(context, responseMessage, false);

            }
        });
    }

    private void actionListener() {


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
