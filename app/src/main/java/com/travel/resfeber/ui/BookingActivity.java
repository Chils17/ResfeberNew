package com.travel.resfeber.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.travel.resfeber.R;
import com.travel.resfeber.adapter.CustomSpinnerAdapter;
import com.travel.resfeber.adapter.PlaceArrayAdapter;
import com.travel.resfeber.api.model.Event;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private Toolbar toolbar;
    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private AutoCompleteTextView autoCompleteSource, autoCompleteDestination;
    private TfButton btnSubmit;
    private double longitudeSource, latitudeSource, longitudeDestination, latitudeDestination;
    private Spinner spinEvent;
    private TfTextView txtEndDate, txtStartDate, txtPickUpTime;
    private int mYear, mMonth, mDay, mHour, mMinute, mAm;
    private boolean isEvent;
    private String selectedEvent;
    private RadioGroup rgTrip;
    private String trip;
    private LatLng destiLatlng,sourceLatlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        geIntentData();
        intToolbar();
        init();
        actionListener();
    }

    private void geIntentData() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            isEvent = extras.getBoolean(AppConstant.INTENT_EVENT, false);
            // destination = extras.getString(AppConstant.INTENT_DESTINATION, "");
        }
    }


    private void intToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);

        if (isEvent) {
            txtTitle.setText("Event Booking");
        } else {
            txtTitle.setText("Vehicle Booking");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        context = BookingActivity.this;

        btnSubmit = (TfButton) findViewById(R.id.btnSubmit);
        LinearLayout llEvent = (LinearLayout) findViewById(R.id.llEvent);
        spinEvent = (Spinner) findViewById(R.id.spinEvent);
        txtEndDate = (TfTextView) findViewById(R.id.txtEndDate);
        txtStartDate = (TfTextView) findViewById(R.id.txtStartDate);
        txtPickUpTime = (TfTextView) findViewById(R.id.txtPickUpTime);

        rgTrip = (RadioGroup) findViewById(R.id.rgTrip);

        ImageView imgView = (ImageView) findViewById(R.id.imgView);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgView);
        Glide.with(this).load(R.raw.car_mg).into(imageViewTarget);

        if (isEvent) {
            llEvent.setVisibility(View.VISIBLE);
        } else {
            llEvent.setVisibility(View.GONE);
        }

        mGoogleApiClient = new GoogleApiClient.Builder(BookingActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        autoCompleteDestination = (AutoCompleteTextView) findViewById(R.id.autoCompleteDestination);
        autoCompleteSource = (AutoCompleteTextView) findViewById(R.id.autoCompleteSource);
        autoCompleteSource.setThreshold(3);
        autoCompleteDestination.setThreshold(3);

        mPlaceArrayAdapter = new PlaceArrayAdapter(context, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        autoCompleteSource.setAdapter(mPlaceArrayAdapter);
        autoCompleteDestination.setAdapter(mPlaceArrayAdapter);

        // get selected radio button from radioGroup
        int selectedId = rgTrip.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        trip = radioButton.getText().toString();

        initSpinner();

    }

    private void initSpinner() {
        ArrayList<String> eventList = new ArrayList<String>();

        eventList.add("Select Event");
        eventList.add("Marriage");
        eventList.add("Birthday");

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, eventList);
        spinEvent.setAdapter(customSpinnerAdapter);

        selectedEvent = spinEvent.getSelectedItem().toString();

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
                if (latLng!=null){
                    sourceLatlng = latLng;
                    longitudeSource = latLng.longitude;
                    latitudeSource = latLng.latitude;
                }

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
                if (latLng!=null){
                    destiLatlng = latLng;
                    longitudeDestination = latLng.longitude;
                    latitudeDestination = latLng.latitude;
                }
            }
        });

        txtPickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mAm = c.get(Calendar.AM_PM);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String timeSet = "";
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            timeSet = "PM";
                        } else if (hourOfDay == 0) {
                            hourOfDay += 12;
                            timeSet = "AM";
                        } else if (hourOfDay == 12)
                            timeSet = "PM";
                        else
                            timeSet = "AM";

                        String minutes = "";
                        if (minute < 10) {
                            minutes = "0" + minute;
                        } else {
                            minutes = String.valueOf(minute);
                        }

                        String pickUpTime = hourOfDay + ":" + minutes + " " + timeSet;
                        Log.d("Data", "pickUpTime " + pickUpTime);

                        txtPickUpTime.setText(pickUpTime);

                    }
                }, mHour, mMinute, false);

                // timePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                timePickerDialog.show();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = txtStartDate.getText().toString().trim();
                String endDate = txtEndDate.getText().toString().trim();
                String pickUpTime = txtPickUpTime.getText().toString().trim();

                if (isEvent) {
                    if (TextUtils.isEmpty(autoCompleteSource.getText().toString())) {
                        Function.showMessage(context, "Enter the source for journey", false);
                    } else if (TextUtils.isEmpty(autoCompleteDestination.getText().toString())) {
                        Function.showMessage(context, "Enter the destination for journey", false);
                    } else if (startDate.equalsIgnoreCase("Start Date")) {
                        Function.showMessage(context, "Enter the start date", false);
                    } else if (endDate.equalsIgnoreCase("End Date")) {
                        Function.showMessage(context, "Enter the end date", false);
                    } else if (pickUpTime.equalsIgnoreCase("Pickup Time")) {
                        Function.showMessage(context, "Enter the pickup time", false);
                    } else if (selectedEvent != null && selectedEvent.equalsIgnoreCase("Select Event")) {
                        Function.showMessage(context, "Select atleast single Event", false);
                    } else {
                        String source = autoCompleteSource.getText().toString().trim();
                        String destination = autoCompleteDestination.getText().toString().trim();
                        //String distanceInkm = String.valueOf(distance(latitudeSource, longitudeSource, latitudeDestination, longitudeDestination));
                        String distanceInkm = String.valueOf(Function.calculationByDistance(sourceLatlng,destiLatlng));
                        Log.d("Data", "Distance in Km " + distanceInkm);
                        Intent intent = new Intent(context, VehicleActivity.class);
                        intent.putExtra(AppConstant.INTENT_SOURCE, source);
                        intent.putExtra(AppConstant.INTENT_DESTINATION, destination);
                        intent.putExtra(AppConstant.INTENT_START_DATE, startDate);
                        intent.putExtra(AppConstant.INTENT_END_DATE, endDate);
                        intent.putExtra(AppConstant.INTENT_DISTANCE, distanceInkm);
                        intent.putExtra(AppConstant.INTENT_PICKUP_TIME, pickUpTime);
                        intent.putExtra(AppConstant.INTENT_EVENT_NAME, selectedEvent);
                        intent.putExtra(AppConstant.INTENT_TRIP, trip);
                        startActivity(intent);
                    }
                } else {
                    if (TextUtils.isEmpty(autoCompleteSource.getText().toString())) {
                        Function.showMessage(context, "Enter the source for journey", false);
                    } else if (TextUtils.isEmpty(autoCompleteDestination.getText().toString())) {
                        Function.showMessage(context, "Enter the destination for journey", false);
                    } else if (startDate.equalsIgnoreCase("Start Date")) {
                        Function.showMessage(context, "Enter the start date", false);
                    } else if (endDate.equalsIgnoreCase("End Date")) {
                        Function.showMessage(context, "Enter the end date", false);
                    } else {
                        String source = autoCompleteSource.getText().toString().trim();
                        String destination = autoCompleteDestination.getText().toString().trim();
                        String distanceInkm = String.valueOf(distance(latitudeSource, longitudeSource, latitudeDestination, longitudeDestination));
                        Log.d("Data", "Distance in Km " + distanceInkm);
                        Intent intent = new Intent(context, VehicleActivity.class);
                        intent.putExtra(AppConstant.INTENT_SOURCE, source);
                        intent.putExtra(AppConstant.INTENT_DESTINATION, destination);
                        intent.putExtra(AppConstant.INTENT_START_DATE, startDate);
                        intent.putExtra(AppConstant.INTENT_END_DATE, endDate);
                        intent.putExtra(AppConstant.INTENT_DISTANCE, distanceInkm);
                        intent.putExtra(AppConstant.INTENT_PICKUP_TIME, pickUpTime);
                        intent.putExtra(AppConstant.INTENT_TRIP, trip);
                        startActivity(intent);
                    }
                }

            }
        });

        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartDate(txtStartDate);
            }
        });

        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndDate(txtEndDate);
            }
        });

        spinEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEvent = parent.getItemAtPosition(position).toString();
                /*if(selectedItem.equals("Add new category"))
                {
                    // do your stuff
                }*/
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rgTrip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioSingleTrip) {
                    trip = "Single Trip";
                } else if (checkedId == R.id.radioRoundTrip) {
                    trip = "Round Trip";
                }
            }

        });


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

        Toast.makeText(this,
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

    private void setStartDate(TfTextView txtDate) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // Create a Date variable/object with user chosen date
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                Date chosenDate = cal.getTime();

                // Format the date  using style medium and UK locale
                DateFormat df_medium_uk = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
                String df_medium_uk_str = df_medium_uk.format(chosenDate);
                // Display the formatted date
                // String date = Function.formatDate(df_medium_uk_str);
                Log.d("Data", "Date " + df_medium_uk_str);
                txtDate.setText(df_medium_uk_str);
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    private void setEndDate(TfTextView txtDate) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // Create a Date variable/object with user chosen date
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                Date chosenDate = cal.getTime();

                // Format the date  using style medium and UK locale
                DateFormat df_medium_uk = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
                String df_medium_uk_str = df_medium_uk.format(chosenDate);
                // Display the formatted date
                String date = Function.formatDate(df_medium_uk_str);
                txtDate.setText(df_medium_uk_str);
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }
}
