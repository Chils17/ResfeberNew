package com.travel.resfeber.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.travel.resfeber.R;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by its7 on 11/1/18.
 */

public class Function {
    public static AppProgressLoader progressLoader;
    public static DialogOptionsSelectedListener dialogOptionsSelectedListener;
    public static Function.OkDialogDismissListener okDialogDismissListener;

    /**
     * This method is used to check is email is valid or not.
     *
     * @param email email of user
     * @return boolean that email is valid or not
     */
    public static boolean isValidEmailAddress(String email) {
        return Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}").matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        return Pattern.compile("[0-9]{10}").matcher(phone).matches();
    }

    public static boolean isValidOTP(String otp) {
        return Pattern.compile("[0-9]{6}").matcher(otp).matches();
    }

    public static boolean validateLetters(String txt) {

        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    private static Gson gson;

    public static void fireIntent(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        return manufacturer + " " + model;
    }

    public static void fireIntent(Activity context) {
        context.finish();
    }

    public static void fireIntentForResult(Activity context, Class<?> cls, int requestCode) {

        Intent intent = new Intent(context, cls);
        context.startActivityForResult(intent, requestCode);
    }

    public static void fireIntentForResult(Activity context, Intent intent, int requestCode) {

        context.startActivityForResult(intent, requestCode);
    }

    public static void fireIntentWithClearFlag(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    public static void FunctionsfireIntentWithClearFlagWithWithPendingTransition(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
    }

    public static void fireIntentWithClearFlagWithWithPendingTransition(Activity context, Intent intent) {

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
    }

    public static void fireIntentWithClearFlagWithWithPendingTransition(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
    }

    public static void fireIntentWithData(Context context, Intent intent) {
        context.startActivity(intent);
        Activity activity = (Activity) context;
    }

    public static String toStingEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static boolean isEmptyEditText(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    public static Typeface getFontType(Context _context, int typeValue) {

        Typeface typeface;

        if (typeValue == 1) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");

        } else if (typeValue == 2) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/medium.ttf");

        } else if (typeValue == 3) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/semibold.ttf");

        } else if (typeValue == 4) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/bold.ttf");

        } else {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");
        }

        return typeface;
    }

    public static void openPlayStore(Context context) {
        String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static String jsonString(Object obj) {
        return "" + MyApplication.getGson().toJson(obj);
    }

    public static boolean checkString(String input) {
        if (input != null && input.trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }


    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*public static boolean checkNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }*/

    public static void loadImage(final Context context, String url, final ImageView imageView, final ProgressBar progressBar) {
        Glide.with(context).load(url)
                .fitCenter()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(imageView);
    }


    public static void loadPatientImage(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(imageView);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static void shareApp(Context context) {
        String appPackageName = context.getPackageName();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        //i.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_text) + "\n" + "http://play.google.com/store/apps/details?id=" + appPackageName);
        // i.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.refer_code_for_libon) + "\n" + Preferences.getInstance(context).getString(Preferences.KEY_REFER_CODE));
        // i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share));
        context.startActivity(Intent.createChooser(i, "Share"));

    }

    public static void showMessage(Context mContext, String message, DialogOptionsSelectedListener dialogOptionsSelectedListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    if (dialogOptionsSelectedListener != null)
                        dialogOptionsSelectedListener.onSelect(true);
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialogWithOk(Context mContext, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("");
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    if (okDialogDismissListener != null)
                        okDialogDismissListener.onDismiss();
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showMessage(Context mContext, String message, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(message)
                .setCancelable(isCancelable)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public interface DialogOptionsSelectedListener {
        void onSelect(boolean isYes);
    }

    public interface OkDialogDismissListener {
        void onDismiss();
    }

    public static void showAlertDialogWithYesNo(Context mContext, String message, DialogOptionsSelectedListener dialogOptionsSelectedListener) {
        if (mContext != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        if (dialogOptionsSelectedListener != null)
                            dialogOptionsSelectedListener.onSelect(true);
                        dialog.dismiss();
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        if (dialogOptionsSelectedListener != null)
                            dialogOptionsSelectedListener.onSelect(false);
                        dialog.dismiss();
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public static void hideKeyPad(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * this method is used to convert dp value to pixel value
     *
     * @param context the context
     * @param dp      the dp
     * @return the int
     */
    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static String formatBirthDate(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            //  Log.d("Old Format", originalFormat.format(date));
            //    Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

    public static String formatDate(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            Log.d("Old Format", originalFormat.format(date));
            Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

    public static String formatTenderDate(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            Log.d("Old Format", originalFormat.format(date));
            Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

    public static String formatFilterDate(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("d MMM yyyy");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            //   Log.d("Old Format", originalFormat.format(date));
            //   Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

    public static String formatTenderDateABC(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            //   Log.d("Old Format", originalFormat.format(date));
            //  Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }


    public static String formatTenderDateNcode(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            //  Log.d("Old Format", originalFormat.format(date));
            //    Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

    public static String formatOnDateDropDown(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            //  Log.d("Old Format", originalFormat.format(date));
            // Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

    public static String formatPushDate(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            //  Log.d("Old Format", originalFormat.format(date));
            // Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

    public static String formatFPSMonthYearDropDown(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("MMM yyyy");
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            // Log.d("Old Format", originalFormat.format(date));
            //Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

    public static String formatDateOfBirth(String OldDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("MM-dd-yyyy",Locale.getDefault());
        Date date;
        String newDate = null;
        try {
            date = originalFormat.parse(OldDate);
            //  Log.d("Old Format", originalFormat.format(date));
            //  Log.d("New Format", targetFormat.format(date));
            newDate = targetFormat.format(date);

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return newDate;
    }

   /* public static void setLanguage(Context context, int language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(context.getString(R.string.prefs_language_key), language).apply();
    }

    public static int getLanguage(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(context.getString(R.string.prefs_language_key), 0);
    }*/

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String getCurrentDateTimeSec() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
        String formattedDate = df.format(c.getTime());
        //  Log.d("date", formattedDate);

        return formattedDate;
    }

    public static String getCurrentDateForOnDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        //   Log.d("date", formattedDate);

        return formattedDate;
    }

    /**
     * common method to show progress dialog
     */
    public static void showProgress(Context context) {
        if (progressLoader != null) {
            try {
                if (progressLoader.isShowing()) {
                    progressLoader.dismiss();
                }
            } catch (Exception e) {
            }
        }
        progressLoader = new AppProgressLoader(context);
        progressLoader.show(context, null, false);
        progressLoader.getWindow().setGravity(Gravity.CENTER);
    }

    /**
     * common method to close progress dialog
     */
    public static void closeProgress() {
        try {
            if (progressLoader != null && progressLoader.isShowing()) {
                progressLoader.dismiss();
            }
        } catch (Exception e) {
        }
    }

    public static double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;

        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.d("Data", "Radius Value " + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

}
