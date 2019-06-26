package com.travel.resfeber.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.travel.resfeber.R;
import com.travel.resfeber.adapter.CustomSpinnerAdapter;
import com.travel.resfeber.adapter.PlaceArrayAdapter;
import com.travel.resfeber.adapter.VehicleSpinnerAdapter;
import com.travel.resfeber.api.call.GetUpdateVehicleBook;
import com.travel.resfeber.api.call.GetVehicle;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.Vehicle;
import com.travel.resfeber.api.model.vehicle.VehicleBook;
import com.travel.resfeber.api.model.vehicle.VehicleResponse;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfEditText;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EditBookingActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private String source, destination, startDate, endDate, distance;
    private Context context;
    private ImageView imgEdit;
    private double totalAmount;
    private Vehicle vehicle;
    private TfButton btnUpdate;
    private AutoCompleteTextView autoCompleteSource, autoCompleteDestination;
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private double longitudeSource, latitudeSource, longitudeDestination, latitudeDestination;
    private String rate;
    private VehicleBook vehicleBook;
    private Spinner spinVehicle;
    private int vehicleId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking);

        geIntentData();
        intToolbar();
        init();
        actionListener();
    }

    private void geIntentData() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            vehicle = (Vehicle) getIntent().getSerializableExtra(AppConstant.INTENT_VEHICLE);
            vehicleBook = (VehicleBook) getIntent().getSerializableExtra(AppConstant.INTENT_VEHICLE_BOOK);
            source = extras.getString(AppConstant.INTENT_SOURCE, "");
            destination = extras.getString(AppConstant.INTENT_DESTINATION, "");
            startDate = extras.getString(AppConstant.INTENT_START_DATE, "");
            endDate = extras.getString(AppConstant.INTENT_END_DATE, "");
            rate = extras.getString(AppConstant.INTENT_VEHICLE_RATE, "");
            distance = extras.getString(AppConstant.INTENT_DISTANCE, "");
            totalAmount = extras.getDouble(AppConstant.INTENT_TOTAL_AMOUNT, 0);
        }
    }

    private void intToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        imgEdit = (ImageView) findViewById(R.id.imgEdit);
        txtTitle.setText("Edit Booking");
        imgEdit.setVisibility(View.GONE);
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
        context = EditBookingActivity.this;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        btnUpdate = (TfButton) findViewById(R.id.btnUpdate);
        TfEditText edtDriverName = (TfEditText) findViewById(R.id.edtDriverName);
       /* TfEditText edtTotalAmount = (TfEditText) findViewById(R.id.edtTotalAmount);
        TfEditText edtPricePerKm = (TfEditText) findViewById(R.id.edtPricePerKm);
        TfEditText edtVehicleFacility = (TfEditText) findViewById(R.id.edtVehicleFacility);
        TfEditText edtNoSeats = (TfEditText) findViewById(R.id.edtNoSeats);
        TfEditText edtVehicleNumber = (TfEditText) findViewById(R.id.edtVehicleNumber);
        TfEditText edtVehicleType = (TfEditText) findViewById(R.id.edtVehicleType);
        TfEditText edtVehicleName = (TfEditText) findViewById(R.id.edtVehicleName);*/
        autoCompleteDestination = (AutoCompleteTextView) findViewById(R.id.autoCompleteDestination);
        autoCompleteSource = (AutoCompleteTextView) findViewById(R.id.autoCompleteSource);
        spinVehicle = (Spinner) findViewById(R.id.spinVehicle);

        if (vehicleBook != null) {
            // edtVehicleName.setText(vehicleBook.getVehicalName());
            // edtVehicleNumber.setText(vehicleBook.getVehicalNumber());

            autoCompleteSource.setText(vehicleBook.getSource());
            autoCompleteDestination.setText(vehicleBook.getDestination());
        }
        // edtVehicleType.setText(vehicle.getVehicalType());
        // edtNoSeats.setText(vehicle.getNoSeat());
        // edtVehicleFacility.setText(vehicle.getFacility());
        // edtPricePerKm.setText("" + vehicle.getRate());
        edtDriverName.setText(vehicle.getDriverName());


        //edtTotalAmount.setText("" + totalAmount);
        //   edtTotalAmount.setText(new DecimalFormat("##.##").format(totalAmount) +" Rs");

        //setEnable(edtVehicleName);
        //setEnable(edtVehicleType);
        //setEnable(edtVehicleNumber);
        //setEnable(edtNoSeats);
        //setEnable(edtVehicleFacility);
        //setEnable(edtPricePerKm);
        setEnable(edtDriverName);
        //setEnable(edtTotalAmount);

        autoCompleteSource.setThreshold(3);
        autoCompleteDestination.setThreshold(3);

        mPlaceArrayAdapter = new PlaceArrayAdapter(context, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        autoCompleteSource.setAdapter(mPlaceArrayAdapter);
        autoCompleteDestination.setAdapter(mPlaceArrayAdapter);

        checkInternetConnection();


    }

    private void checkInternetConnection() {
        if (Function.checkNetworkConnection(context)) {
            callViewVehicleApi();
        } else {
            Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
        }
    }

    private void callViewVehicleApi() {
        new GetVehicle(context, new GetVehicle.OnGetVehicle() {
            @Override
            public void onSuccess(VehicleResponse data) {
                if (data != null && data.getResponseData().getData().size() > 0) {
                    Log.d("vehicle ", Function.jsonString(data.getResponseData().getData()));

                    List<Vehicle> vehicleList = data.getResponseData().getData();

                    VehicleSpinnerAdapter customSpinnerAdapter = new VehicleSpinnerAdapter(context, vehicleList);
                    spinVehicle.setAdapter(customSpinnerAdapter);
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

    private void actionListener() {
        autoCompleteSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                final String placeAddress = String.valueOf(item.description);
                Log.d("Data", "Selected: " + item.description);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                Log.d("Data", "Fetching details for ID: " + item.placeId);

                Log.d("Data", "Get latlong " + getLocationFromAddress(context, placeAddress));

                LatLng latLng = getLocationFromAddress(context, placeAddress);
                longitudeSource = latLng.longitude;
                latitudeSource = latLng.latitude;

            }
        });

        autoCompleteDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                final String placeAddress = String.valueOf(item.description);
                Log.d("Data", "Selected: " + item.description);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                Log.d("Data", "Fetching details for ID: " + item.placeId);

                Log.d("Data", "Get latlong " + getLocationFromAddress(context, placeAddress));

                LatLng latLng = getLocationFromAddress(context, placeAddress);
                longitudeDestination = latLng.longitude;
                latitudeDestination = latLng.latitude;
            }
        });

        spinVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedVehicle = parent.getItemAtPosition(position).toString();
                Vehicle vehicles = (Vehicle) parent.getItemAtPosition(position);
                vehicleId = vehicles.getVehicalID();
                Log.d("Data", "vehicleId " + vehicleId);
                /*if(selectedItem.equals("Add new category"))
                {
                    // do your stuff
                }*/
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                if (Function.checkNetworkConnection(context)) {
                    callUpdateVehicleBookApi();
                } else {
                    Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
                }
            }
        });
    }

    private void callUpdateVehicleBookApi() {
        UpdateVehicleBookRequest updateVehicleBookRequest = new UpdateVehicleBookRequest();
        updateVehicleBookRequest.setBookingID(vehicleBook.getBookingID());
        updateVehicleBookRequest.setBookingStatus("Pending");
        updateVehicleBookRequest.setDestination(autoCompleteDestination.getText().toString().trim());
        updateVehicleBookRequest.setSource(autoCompleteSource.getText().toString().trim());
        updateVehicleBookRequest.setFacility("");
        updateVehicleBookRequest.setFromDate(startDate);
        updateVehicleBookRequest.setToDate(endDate);
        updateVehicleBookRequest.setTotalAmount(totalAmount);

        if (vehicleId != 0) {
            updateVehicleBookRequest.setVehicalID(vehicleId);
        } else {
            updateVehicleBookRequest.setVehicalID(vehicleBook.getVehicalID());
        }

        Log.d("Data", "vehicle update req " + Function.jsonString(updateVehicleBookRequest));

        new GetUpdateVehicleBook(context, updateVehicleBookRequest, new GetUpdateVehicleBook.OnGetUpdateVehicleBook() {
            @Override
            public void onSuccess(UpdateVehicleBookResponse data) {
                if (data != null && data.getResponseData().getData().size() > 0) {
                    Log.d("update vehicle book ", Function.jsonString(data));

                    int bookingId = data.getResponseData().getData().get(0).getBookingID();

                    Intent intent = new Intent(context, ViewBookingActivity.class);
                    intent.putExtra(AppConstant.INTENT_VEHICLE, vehicle);
                    intent.putExtra(AppConstant.INTENT_SOURCE, source);
                    intent.putExtra(AppConstant.INTENT_DESTINATION, destination);
                    intent.putExtra(AppConstant.INTENT_START_DATE, startDate);
                    intent.putExtra(AppConstant.INTENT_END_DATE, endDate);
                    intent.putExtra(AppConstant.INTENT_TOTAL_AMOUNT, totalAmount);
                    intent.putExtra(AppConstant.INTENT_VEHICLE_RATE, rate);
                    intent.putExtra(AppConstant.INTENT_VEHICLE_BOOK, vehicleBook);
                    intent.putExtra(AppConstant.INTENT_BOOKING_ID, bookingId);
                    startActivity(intent);
                    finish();
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
        et.setFocusable(true);
        et.setClickable(false);
    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            final String placeAddress = String.valueOf(item.description);
            Log.d("Data", "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.d("Data", "Fetching details for ID: " + item.placeId);

            Log.d("Data", "Get latlong " + getLocationFromAddress(context, placeAddress));

            LatLng latLng = getLocationFromAddress(context, placeAddress);
            double longitude = latLng.longitude;
            double latitude = latLng.latitude;
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d("Data", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.d("Data", "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Data", "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(context,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.d("Data", "Google Places API connection suspended.");
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
