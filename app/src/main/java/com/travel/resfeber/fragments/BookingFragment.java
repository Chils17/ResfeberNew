package com.travel.resfeber.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.travel.resfeber.R;
import com.travel.resfeber.adapter.PlaceArrayAdapter;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.ui.BaseActivity;
import com.travel.resfeber.ui.BookingActivity;
import com.travel.resfeber.ui.VehicleActivity;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private AutoCompleteTextView autoCompleteSource, autoCompleteDestination;
    private TfButton btnSubmit;
    private double longitudeSource, latitudeSource, longitudeDestination, latitudeDestination;
    private int mYear, mMonth, mDay, mHour, mMinute, mAm;
    private TfTextView txtStartDate, txtEndDate, txtPickUpTime;
    private ImageView imgView;
    private RadioButton radioSingleTrip, radioRoundTrip;
    private RadioGroup rgTrip;
    private String trip;
    private LatLng destiLatlng, sourceLatlng;

    @SuppressLint({"ValidFragment", "Unused"})
    private BookingFragment() {
    }

    @SuppressLint("ValidFragment")
    private BookingFragment(BaseActivity activity) {
        setBaseActivity(activity);
    }

    /**
     * This method is used to create Fragment instance . It is used to restrict developer to provide
     * following parameters which are mandatory for this appMapFragment
     *
     * @param activity instance of activity
     * @return instance of this {@link BookingFragment}
     */
    public static BaseFragment getFragment(BaseActivity activity) {
        BaseFragment fragment = new BookingFragment(activity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseActivity((BaseActivity) getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initToolbar();
        init(view);
        actionListener();
    }

    private void initToolbar() {

    }

    private void init(View view) {
        btnSubmit = (TfButton) view.findViewById(R.id.btnSubmit);
        LinearLayout llEvent = (LinearLayout) view.findViewById(R.id.llEvent);
        Spinner spinEvent = (Spinner) view.findViewById(R.id.spinEvent);
        txtEndDate = (TfTextView) view.findViewById(R.id.txtEndDate);
        txtStartDate = (TfTextView) view.findViewById(R.id.txtStartDate);
        txtPickUpTime = (TfTextView) view.findViewById(R.id.txtPickUpTime);

        radioSingleTrip = (RadioButton) view.findViewById(R.id.radioSingleTrip);
        radioRoundTrip = (RadioButton) view.findViewById(R.id.radioRoundTrip);
        rgTrip = (RadioGroup) view.findViewById(R.id.rgTrip);


        imgView = (ImageView) view.findViewById(R.id.imgView);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgView);
        Glide.with(this).load(R.raw.car_mg).into(imageViewTarget);

        mGoogleApiClient = new GoogleApiClient.Builder(getBaseActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getBaseActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        autoCompleteDestination = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteDestination);
        autoCompleteSource = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteSource);
        autoCompleteSource.setThreshold(3);
        autoCompleteDestination.setThreshold(3);

        mPlaceArrayAdapter = new PlaceArrayAdapter(getBaseActivity(), android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        autoCompleteSource.setAdapter(mPlaceArrayAdapter);
        autoCompleteDestination.setAdapter(mPlaceArrayAdapter);

        // get selected radio button from radioGroup
        int selectedId = rgTrip.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
        trip = radioButton.getText().toString();

        //  Log.d("Data", "RG " + radioButton.getText().toString());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        return view;
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

                Log.d("Data", "Get latlong " + getLocationFromAddress(getBaseActivity(), placeAddress));

                LatLng latLng = getLocationFromAddress(getBaseActivity(), placeAddress);
                if (latLng != null) {
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

                Log.d("Data", "Get latlong " + getLocationFromAddress(getBaseActivity(), placeAddress));

                LatLng latLng = getLocationFromAddress(getBaseActivity(), placeAddress);
                if (latLng != null) {
                    destiLatlng = latLng;
                    longitudeDestination = latLng.longitude;
                    latitudeDestination = latLng.latitude;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = txtStartDate.getText().toString().trim();
                String endDate = txtEndDate.getText().toString().trim();
                String pickUpTime = txtPickUpTime.getText().toString().trim();
                if (TextUtils.isEmpty(autoCompleteSource.getText().toString())) {
                    Function.showMessage(getBaseActivity(), "Enter the source for journey", false);
                } else if (TextUtils.isEmpty(autoCompleteDestination.getText().toString())) {
                    Function.showMessage(getBaseActivity(), "Enter the destination for journey", false);
                } else if (startDate.equalsIgnoreCase("Start Date")) {
                    Function.showMessage(getBaseActivity(), "Enter the start date", false);
                } else if (endDate.equalsIgnoreCase("End Date")) {
                    Function.showMessage(getBaseActivity(), "Enter the end date", false);
                } else if (pickUpTime.equalsIgnoreCase("Pickup Time")) {
                    Function.showMessage(getBaseActivity(), "Enter the pickup time", false);
                } else {
                    String source = autoCompleteSource.getText().toString().trim();
                    String destination = autoCompleteDestination.getText().toString().trim();
                    //String distanceInkm = String.valueOf(distance(latitudeSource, longitudeSource, latitudeDestination, longitudeDestination));
                    String distanceInkm = String.valueOf(Function.calculationByDistance(sourceLatlng, destiLatlng));
                    Log.d("Data", "Distance in Km " + distanceInkm);
                    Intent intent = new Intent(getBaseActivity(), VehicleActivity.class);
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

        txtPickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mAm = c.get(Calendar.AM_PM);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getBaseActivity(), new TimePickerDialog.OnTimeSetListener() {

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

            Log.d("Data", "Get latlong " + getLocationFromAddress(getBaseActivity(), placeAddress));

            LatLng latLng = getLocationFromAddress(getBaseActivity(), placeAddress);
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

        Toast.makeText(getBaseActivity(),
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


        DatePickerDialog datePickerDialog = new DatePickerDialog(getBaseActivity(), new DatePickerDialog.OnDateSetListener() {

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


        DatePickerDialog datePickerDialog = new DatePickerDialog(getBaseActivity(), new DatePickerDialog.OnDateSetListener() {

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
