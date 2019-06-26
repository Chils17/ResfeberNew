package com.travel.resfeber.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.travel.resfeber.R;
import com.travel.resfeber.adapter.CustomSpinnerAdapter;
import com.travel.resfeber.api.call.GetVehicleBook;
import com.travel.resfeber.api.call.GetVehicleRate;
import com.travel.resfeber.api.model.vehicle.Vehicle;
import com.travel.resfeber.api.model.vehicle.VehicleBookOrder;
import com.travel.resfeber.api.model.vehicle.VehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.VehicleRateRequest;
import com.travel.resfeber.api.model.vehicle.VehicleRateResponse;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.Preferences;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VehicleDetailActivity extends AppCompatActivity {

    private Context context;
    private Vehicle vehicle;
    private TfTextView txtTitle;
    private String source, destination, startDate, endDate, distance, eventName, pickupTime;
    private TfButton btnSubmit;
    private TfTextView txtTotalAmount, txtVehiclePricePerKm;
    private double totalAmount, finalAmount, eventAmount;
    private Spinner spinFacility;
    private String selectedFacility;
    private String rate;
    private VehicleBookOrder vehicleBookOrder;
    private String trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        getIntentData();
        intToolbar();
        init();
        actionListener();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            vehicleBookOrder = (VehicleBookOrder) getIntent().getSerializableExtra(AppConstant.INTENT_VEHICLE_BOOK_LIST);
            vehicle = (Vehicle) getIntent().getSerializableExtra(AppConstant.INTENT_VEHICLE);
            eventName = extras.getString(AppConstant.INTENT_EVENT_NAME, "");
            source = extras.getString(AppConstant.INTENT_SOURCE, "");
            destination = extras.getString(AppConstant.INTENT_DESTINATION, "");
            startDate = extras.getString(AppConstant.INTENT_START_DATE, "");
            endDate = extras.getString(AppConstant.INTENT_END_DATE, "");
            distance = extras.getString(AppConstant.INTENT_DISTANCE, "");
            pickupTime = extras.getString(AppConstant.INTENT_PICKUP_TIME, "");
            trip = extras.getString(AppConstant.INTENT_TRIP, "");

//            Log.d("Data", "Debug Data : cover : " + upcomingMatch.getId() + " : cms : " + upcomingMatch.getDate());
            Log.d("Data", "Debug Data : source : " + source + " : destination : " + destination);
            Log.d("Data", "Debug Data : startDate : " + startDate + " : endDate : " + endDate);
            Log.d("Data", "Debug Data : distance : " + distance);
            Log.d("Data", "Debug Data : trip : " + trip);
        }
    }

    private void intToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        //txtTitle.setText("Vehicle Detail");
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
        context = VehicleDetailActivity.this;

        TfTextView txtDriverName = (TfTextView) findViewById(R.id.txtDriverName);
        txtVehiclePricePerKm = (TfTextView) findViewById(R.id.txtVehiclePricePerKm);
        // TfTextView txtVehicleFacility = (TfTextView) findViewById(R.id.txtVehicleFacility);
        TfTextView txtVehicleNoOfSeat = (TfTextView) findViewById(R.id.txtVehicleNoOfSeat);
        TfTextView txtVehicleNumber = (TfTextView) findViewById(R.id.txtVehicleNumber);
        TfTextView txtVehicleType = (TfTextView) findViewById(R.id.txtVehicleType);
        TfTextView txtVehicleName = (TfTextView) findViewById(R.id.txtVehicleName);
        txtTotalAmount = (TfTextView) findViewById(R.id.txtTotalAmount);
        spinFacility = (Spinner) findViewById(R.id.spinFacility);

        btnSubmit = (TfButton) findViewById(R.id.btnSubmit);
        ImageView imgVehicle = (ImageView) findViewById(R.id.imgVehicle);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        LinearLayout llDriver = (LinearLayout) findViewById(R.id.llDriver);
        LinearLayout llPricePerKm = (LinearLayout) findViewById(R.id.llPricePerKm);
        LinearLayout llVehicleFacility = (LinearLayout) findViewById(R.id.llVehicleFacility);
        LinearLayout llVehicleSeat = (LinearLayout) findViewById(R.id.llVehicleSeat);
        LinearLayout llVehicleNumber = (LinearLayout) findViewById(R.id.llVehicleNumber);
        LinearLayout llVehicleType = (LinearLayout) findViewById(R.id.llVehicleType);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        if (vehicle != null) {
            llVehicleType.setVisibility(View.VISIBLE);
            llVehicleSeat.setVisibility(View.VISIBLE);
            llVehicleFacility.setVisibility(View.VISIBLE);
            llPricePerKm.setVisibility(View.VISIBLE);
            llDriver.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);

            Function.loadImage(context, vehicle.getVehicalImage(), imgVehicle, progressBar);

            txtTitle.setText(vehicle.getVehicalName());

            txtVehicleName.setText(vehicle.getVehicalName());
            txtVehicleType.setText(vehicle.getVehicalType());
            txtVehicleNumber.setText(vehicle.getVehicalNumber());
            txtVehicleNoOfSeat.setText(vehicle.getNoSeat());
            //  txtVehicleFacility.setText(vehicle.getFacility());
            txtVehiclePricePerKm.setText("" + vehicle.getRate());
            txtDriverName.setText(vehicle.getDriverName());

            //  setTotalAmount(vehicle.getRate());
        } else {
            //Function.loadImage(context, vehicle.getVehicalImage(), imgVehicle, progressBar);
            frameLayout.setVisibility(View.GONE);

            txtTitle.setText(vehicleBookOrder.getVehicalName());

            txtVehicleName.setText(vehicleBookOrder.getVehicalName());
            // txtVehicleType.setText(vehicleBookOrder.getVehicalType());
            txtVehicleNumber.setText(vehicleBookOrder.getVehicalNumber());
            //txtVehicleNoOfSeat.setText(vehicleBookOrder.getNoSeat());
            //  txtVehicleFacility.setText(vehicle.getFacility());
            // txtVehiclePricePerKm.setText("" + vehicleBookOrder.getRate());
            // txtDriverName.setText(vehicleBookOrder.getDriverName());

            txtTotalAmount.setText("" + vehicleBookOrder.getTotalAmount());

            llVehicleType.setVisibility(View.GONE);
            llVehicleSeat.setVisibility(View.GONE);
            llVehicleFacility.setVisibility(View.GONE);
            llPricePerKm.setVisibility(View.GONE);
            llDriver.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
        }


        //TextView textView = (TextView) spinFacility.getSelectedView();
        //textView.setText(vehicle.getFacility());

        initSpinner();

    }

    private void setTotalAmount(double vehicleRate) {

        if (!distance.isEmpty()) {
            finalAmount = 0;
            double dist = Double.parseDouble(distance);
            totalAmount = dist * vehicleRate;
            Log.d("Data", "Total " + totalAmount);

            if (trip.equalsIgnoreCase("Single Trip")) {
                Log.d("Data", "Single");
                finalAmount = totalAmount;
                Log.d("Data", "SAmount " + finalAmount);
            } else if (trip.equalsIgnoreCase("Round Trip")) {
                Log.d("Data", "Round");
                finalAmount = totalAmount * 2;
                Log.d("Data", "RAmount " + finalAmount);
            }

            if (!eventName.isEmpty()) {
                Log.d("Data", "Event avilable");
                eventAmount = finalAmount + 3500.00;
                Log.d("Data", "Event Amount " + eventAmount);
                txtTotalAmount.setText(new DecimalFormat("##.##").format(eventAmount) + " Rs");
            } else {
                Log.d("Data", "Event  not avilable");
                txtTotalAmount.setText(new DecimalFormat("##.##").format(finalAmount) + " Rs");
            }
            // txtTotalAmount.setText("" + totalAmount);
        }
    }


    private void initSpinner() {
        ArrayList<String> eventList = new ArrayList<String>();

        eventList.add("AC");
        eventList.add("Non AC");

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, eventList);
        spinFacility.setAdapter(customSpinnerAdapter);

        selectedFacility = spinFacility.getSelectedItem().toString();

    }

    private void actionListener() {
        spinFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFacility = parent.getItemAtPosition(position).toString();

                if (Function.checkNetworkConnection(context)) {
                    callVehicleRateApi();
                } else {
                    Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
                }
                /*if(selectedItem.equals("Add new category"))
                {
                    // do your stuff
                }*/
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Function.checkNetworkConnection(context)) {
                    callVehicleBookApi();
                } else {
                    Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
                }

            }
        });
    }

    private void callVehicleBookApi() {
        VehicleBookRequest vehicleBookRequest = new VehicleBookRequest();
        vehicleBookRequest.setBookingID(0);
        vehicleBookRequest.setBookingStatus("Pending");
        vehicleBookRequest.setDescription("");
        vehicleBookRequest.setSource(source);
        vehicleBookRequest.setDestination(destination);
        vehicleBookRequest.setEventName("");
        vehicleBookRequest.setEventPlace("");
        vehicleBookRequest.setFacility(selectedFacility);
        vehicleBookRequest.setFromDate(startDate);
        vehicleBookRequest.setToDate(endDate);
        vehicleBookRequest.setTotalAmount(0.0);
        vehicleBookRequest.setUserID(Preferences.getInstance(context).getString(Preferences.KEY_USER_ID));
        vehicleBookRequest.setVehicalID(vehicle.getVehicalID());
        vehicleBookRequest.setPickUpTime(pickupTime);
        vehicleBookRequest.setTrip(trip);

        new GetVehicleBook(context, vehicleBookRequest, new GetVehicleBook.OnGetVehicleBook() {
            @Override
            public void onSuccess(VehicleBookResponse data) {
                if (data != null && data.getResponseCode() == 1) {
                    int bookingId = data.getBookingID();

                    Intent intent = new Intent(context, ViewBookingActivity.class);
                    intent.putExtra(AppConstant.INTENT_VEHICLE, vehicle);
                    intent.putExtra(AppConstant.INTENT_SOURCE, source);
                    intent.putExtra(AppConstant.INTENT_DESTINATION, destination);
                    intent.putExtra(AppConstant.INTENT_START_DATE, startDate);
                    intent.putExtra(AppConstant.INTENT_END_DATE, endDate);
                    intent.putExtra(AppConstant.INTENT_EVENT_NAME, eventName);
                    if (!eventName.isEmpty()) {
                        intent.putExtra(AppConstant.INTENT_TOTAL_AMOUNT, eventAmount);
                    } else {
                        intent.putExtra(AppConstant.INTENT_TOTAL_AMOUNT, finalAmount);
                    }
                    intent.putExtra(AppConstant.INTENT_VEHICLE_RATE, rate);
                    intent.putExtra(AppConstant.INTENT_BOOKING_ID, bookingId);
                    intent.putExtra(AppConstant.INTENT_PICKUP_TIME, pickupTime);
                    intent.putExtra(AppConstant.INTENT_TRIP, trip);
                    startActivity(intent);

                } else {
                    Function.showMessage(context, getResources().getString(R.string.err_something_went_wrong), false);
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

    private void callVehicleRateApi() {
        VehicleRateRequest vehicleRateRequest = new VehicleRateRequest();
        vehicleRateRequest.setFacility(selectedFacility);
        vehicleRateRequest.setVehicalID(vehicle.getVehicalID());

        new GetVehicleRate(context, vehicleRateRequest, new GetVehicleRate.OnGetVehicleRate() {
            @Override
            public void onSuccess(VehicleRateResponse data) {
                if (data != null && data.getResponseData().getData().size() > 0) {
                    double vehicleRate = data.getResponseData().getData().get(0).getRate();
                    rate = String.valueOf(vehicleRate);
                    txtVehiclePricePerKm.setText(rate);
                    setTotalAmount(vehicleRate);
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
        finish();
    }
}
