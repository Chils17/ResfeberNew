package com.travel.resfeber.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.travel.resfeber.R;
import com.travel.resfeber.api.call.GetUpdateVehicleBook;
import com.travel.resfeber.api.call.GetVehicleBookDetail;
import com.travel.resfeber.api.model.login.Login;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.Vehicle;
import com.travel.resfeber.api.model.vehicle.VehicleBook;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailResponse;
import com.travel.resfeber.custom.TfButton;
import com.travel.resfeber.custom.TfEditText;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.helper.MyApplication;
import com.travel.resfeber.helper.Preferences;
import com.travel.resfeber.payumoney.AppEnvironment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ViewBookingActivity extends AppCompatActivity {

    private String source, destination, startDate, endDate, distance, eventName;
    private Context context;
    private ImageView imgEdit;
    private double totalAmount;
    private Vehicle vehicle;
    private TfButton btnPay;
    private String rate;
    private int bookingId;
    private TfEditText edtTotalAmount, edtDriverName, edtPricePerKm, edtVehicleFacility, edtNoSeats, edtVehicleNumber, edtVehicleType, edtVehicleName, edtDestination, edtSource;
    private List<VehicleBook> vehicleBookList;
    private Login userLoginModel;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    private String pickupTime, trip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        userLoginModel = new Gson().fromJson(Preferences.getInstance(getApplicationContext()).getString(Preferences.KEY_USER_MODEL), Login.class);


        geIntentData();
        intToolbar();
        init();
        actionListener();
    }

    private void geIntentData() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            vehicle = (Vehicle) getIntent().getSerializableExtra(AppConstant.INTENT_VEHICLE);
            eventName = extras.getString(AppConstant.INTENT_EVENT_NAME, "");
            source = extras.getString(AppConstant.INTENT_SOURCE, "");
            destination = extras.getString(AppConstant.INTENT_DESTINATION, "");
            startDate = extras.getString(AppConstant.INTENT_START_DATE, "");
            endDate = extras.getString(AppConstant.INTENT_END_DATE, "");
            distance = extras.getString(AppConstant.INTENT_DISTANCE, "");
            rate = extras.getString(AppConstant.INTENT_VEHICLE_RATE, "");
            pickupTime = extras.getString(AppConstant.INTENT_PICKUP_TIME, "");
            trip = extras.getString(AppConstant.INTENT_TRIP, "");
            totalAmount = extras.getDouble(AppConstant.INTENT_TOTAL_AMOUNT, 0);
            bookingId = extras.getInt(AppConstant.INTENT_BOOKING_ID, 0);

            Log.d("Data", "bookingId " + bookingId);
            Log.d("Data", "EventName " + eventName);
        }
    }

    private void intToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TfTextView txtTitle = (TfTextView) findViewById(R.id.txtTitle);
        imgEdit = (ImageView) findViewById(R.id.imgEdit);
        txtTitle.setText("View Booking");
        imgEdit.setVisibility(View.VISIBLE);
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
        ((MyApplication) getApplication()).setAppEnvironment(AppEnvironment.SANDBOX);

        context = ViewBookingActivity.this;
        btnPay = (TfButton) findViewById(R.id.btnPay);
        edtTotalAmount = (TfEditText) findViewById(R.id.edtTotalAmount);
        edtDriverName = (TfEditText) findViewById(R.id.edtDriverName);
        edtPricePerKm = (TfEditText) findViewById(R.id.edtPricePerKm);
        edtVehicleFacility = (TfEditText) findViewById(R.id.edtVehicleFacility);
        edtNoSeats = (TfEditText) findViewById(R.id.edtNoSeats);
        edtVehicleNumber = (TfEditText) findViewById(R.id.edtVehicleNumber);
        edtVehicleType = (TfEditText) findViewById(R.id.edtVehicleType);
        edtVehicleName = (TfEditText) findViewById(R.id.edtVehicleName);
        edtDestination = (TfEditText) findViewById(R.id.edtDestination);
        edtSource = (TfEditText) findViewById(R.id.edtSource);

        checkInternetConnection();

        setEnable(edtVehicleName);
        setEnable(edtVehicleType);
        setEnable(edtVehicleNumber);
        setEnable(edtNoSeats);
        setEnable(edtVehicleFacility);
        setEnable(edtPricePerKm);
        setEnable(edtDriverName);
        setEnable(edtSource);
        setEnable(edtDestination);
        setEnable(edtTotalAmount);


    }

    private void checkInternetConnection() {
        if (Function.checkNetworkConnection(context)) {
            callViewVehicleApi();
        } else {
            Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
        }
    }

    private void callViewVehicleApi() {
        VehicleBookDetailRequest vehicleBookDetailRequest = new VehicleBookDetailRequest();
        vehicleBookDetailRequest.setBookingID(bookingId);

        Log.d("Data", "vehicle book req " + Function.jsonString(vehicleBookDetailRequest));

        new GetVehicleBookDetail(context, vehicleBookDetailRequest, new GetVehicleBookDetail.OnGetVehicleBookDetail() {
            @Override
            public void onSuccess(VehicleBookDetailResponse data) {
                if (data != null && data.getResponseData().getData().size() > 0) {
                    Log.d("vehicle book Detail ", Function.jsonString(data));

                    vehicleBookList = data.getResponseData().getData();

                    edtVehicleName.setText(vehicleBookList.get(0).getVehicalName());
                    edtVehicleType.setText(vehicle.getVehicalType());
                    edtVehicleNumber.setText(vehicleBookList.get(0).getVehicalNumber());
                    edtNoSeats.setText(vehicle.getNoSeat());
                    edtVehicleFacility.setText(vehicle.getFacility());
                    // edtPricePerKm.setText("" + vehicle.getRate());
                    edtPricePerKm.setText(rate);
                    edtDriverName.setText(vehicle.getDriverName());

                    edtSource.setText(vehicleBookList.get(0).getSource());
                    edtDestination.setText(vehicleBookList.get(0).getDestination());
                    // edtTotalAmount.setText("" + totalAmount);
                    Log.d("Data", "Total " + vehicleBookList.get(0).getTotalAmount());
                    if (vehicleBookList.get(0).getTotalAmount() != 0.0) {
                        Log.d("Data", "update");
                        edtTotalAmount.setText(new DecimalFormat("##.##").format(vehicleBookList.get(0).getTotalAmount()) + " Rs");
                    } else {
                        Log.d("Data", "existing");
                        edtTotalAmount.setText(new DecimalFormat("##.##").format(totalAmount) + " Rs");
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

    private void actionListener() {
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditBookingActivity.class);
                intent.putExtra(AppConstant.INTENT_VEHICLE, vehicle);
                intent.putExtra(AppConstant.INTENT_SOURCE, source);
                intent.putExtra(AppConstant.INTENT_DESTINATION, destination);
                intent.putExtra(AppConstant.INTENT_START_DATE, startDate);
                intent.putExtra(AppConstant.INTENT_END_DATE, endDate);
                intent.putExtra(AppConstant.INTENT_TOTAL_AMOUNT, totalAmount);
                intent.putExtra(AppConstant.INTENT_VEHICLE_RATE, rate);
                intent.putExtra(AppConstant.INTENT_VEHICLE_BOOK, vehicleBookList.get(0));
                startActivity(intent);
                //  finish();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPayUMoneyFlow();
            }
        });
    }

    private void callBookVehicleApi() {
        if (Function.checkNetworkConnection(context)) {
            callUpdateVehicleBookApi();
        } else {
            Function.showMessage(context, getResources().getString(R.string.err_no_internet_connection), false);
        }
    }

    private void callUpdateVehicleBookApi() {
        UpdateVehicleBookRequest updateVehicleBookRequest = new UpdateVehicleBookRequest();
        updateVehicleBookRequest.setBookingID(vehicleBookList.get(0).getBookingID());
        updateVehicleBookRequest.setBookingStatus("Complete");
        updateVehicleBookRequest.setDestination(edtDestination.getText().toString().trim());
        updateVehicleBookRequest.setSource(edtSource.getText().toString().trim());
        updateVehicleBookRequest.setFacility(edtVehicleFacility.getText().toString().trim());
        updateVehicleBookRequest.setFromDate(startDate);
        updateVehicleBookRequest.setToDate(endDate);
        updateVehicleBookRequest.setPickupTime(pickupTime);
        updateVehicleBookRequest.setTripType(trip);
        updateVehicleBookRequest.setTotalAmount(totalAmount);

        /*double finalAmount;
        if (trip.equalsIgnoreCase("Single Trip")) {
            finalAmount = totalAmount;
            updateVehicleBookRequest.setTotalAmount(finalAmount);
        } else {
            finalAmount = totalAmount + totalAmount;
            updateVehicleBookRequest.setTotalAmount(finalAmount);
        }*/

        updateVehicleBookRequest.setVehicalID(vehicleBookList.get(0).getVehicalID());

        Log.d("Data", "vehicle update req " + Function.jsonString(updateVehicleBookRequest));

        new GetUpdateVehicleBook(context, updateVehicleBookRequest, new GetUpdateVehicleBook.OnGetUpdateVehicleBook() {
            @Override
            public void onSuccess(UpdateVehicleBookResponse data) {
                if (data != null && data.getResponseData().getData().size() > 0) {
                    Log.d("update vehicle book ", Function.jsonString(data));

                    int bookingId = data.getResponseData().getData().get(0).getBookingID();

                    String sourceName = edtSource.getText().toString();
                    String destinationName = edtDestination.getText().toString();
                    String totalAmount = edtTotalAmount.getText().toString();

                    Function.showToastLong(context,
                            "Your vehicle booking is successful. Your ride is from " + sourceName + " to " + destinationName + "On time " + pickupTime + " and amount is Rs " + totalAmount);

                    Intent intent = new Intent(context, ResfeberDrawerActivity.class);
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


    private void launchPayUMoneyFlow() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("RESFEBER");

        payUmoneyConfig.disableExitConfirmation(false);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble("500");

        } catch (Exception e) {
            e.printStackTrace();
        }
        String txnId = System.currentTimeMillis() + "";
        //String txnId = "TXNID720431525261327973";
        String phone = userLoginModel.getMobileNo();
        String productName = "product_info";
        String firstName = userLoginModel.getFirstName();
        String email = userLoginModel.getEmailId();
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        AppEnvironment appEnvironment = ((MyApplication) getApplication()).getAppEnvironment();
        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
            mPaymentParams = builder.build();

            /*
             * Hash should always be generated from your server side.
             * */
            //    generateHashFromServer(mPaymentParams);

            /*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);
            // Invoke the following function to open the checkout page.
            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, ViewBookingActivity.this, R.style.AppTheme_default, false);

            //  PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,context, R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            // payNowButton.setEnabled(true);
        }
    }

    private void setEnable(TfEditText et) {
        et.setFocusable(false);
        et.setClickable(true);
    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    /*public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }*/

    /**
     * Thus function calculates the hash for transaction
     *
     * @param paymentParam payment params of transaction
     * @return payment params along with calculated merchant hash
     */
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        AppEnvironment appEnvironment = ((MyApplication) getApplication()).getAppEnvironment();
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    /**
     * This method generates hash from server.
     *
     * @param paymentParam payments params used for hash generation
     */
    public void generateHashFromServer(PayUmoneySdkInitializer.PaymentParam paymentParam) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.

        HashMap<String, String> params = paymentParam.getParams();

        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayUmoneyConstants.KEY, params.get(PayUmoneyConstants.KEY)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.AMOUNT, params.get(PayUmoneyConstants.AMOUNT)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.TXNID, params.get(PayUmoneyConstants.TXNID)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.EMAIL, params.get(PayUmoneyConstants.EMAIL)));
        postParamsBuffer.append(concatParams("productinfo", params.get(PayUmoneyConstants.PRODUCT_INFO)));
        postParamsBuffer.append(concatParams("firstname", params.get(PayUmoneyConstants.FIRSTNAME)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF1, params.get(PayUmoneyConstants.UDF1)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5)));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();

        // lets make an api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }

    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    /**
     * This AsyncTask generates hash from server.
     */
    private class GetHashesFromServerTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... postParams) {

            String merchantHash = "";
            try {
                //TODO Below url is just for testing purpose, merchant needs to replace this with their server side hash generation url
                URL url = new URL("https://payu.herokuapp.com/get_hash");

                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                JSONObject response = new JSONObject(responseStringBuffer.toString());

                Iterator<String> payuHashIterator = response.keys();
                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();
                    switch (key) {
                        /**
                         * This hash is mandatory and needs to be generated from merchant's server side
                         *
                         */
                        case "payment_hash":
                            merchantHash = response.getString(key);
                            break;
                        default:
                            break;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return merchantHash;
        }

        @Override
        protected void onPostExecute(String merchantHash) {
            super.onPostExecute(merchantHash);

            progressDialog.dismiss();
            //payNowButton.setEnabled(true);

            if (merchantHash.isEmpty() || merchantHash.equals("")) {
                Toast.makeText(context, "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                mPaymentParams.setMerchantHash(merchantHash);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("Data", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    Log.d("Data", "Payment Success");
                } else {
                    //Failure Transaction
                    Log.d("Data", "Payment Fail");
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();

                if (payuResponse != null) {
                    callBookVehicleApi();
                }

               /* new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();*/

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d("Data", "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d("Data", "Both objects are null!");
            }
        }
    }

}
