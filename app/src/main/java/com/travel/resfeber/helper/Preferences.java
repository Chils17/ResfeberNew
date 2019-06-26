package com.travel.resfeber.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by its7 on 11/1/18.
 */

public class Preferences {
    // SharedPreferences file name
    private static final String PREF_NAME = "com.gscscl.app";
    // public static final variables
    public static final String BOOKMARK = "BOOKMARK";
    // Shared pref mode
    private static final int PRIVATE_MODE = 1;

    // private static variables
    private static Preferences instance;

    /**
     * check notification click
     */
    public static String KEY_IS_NOTIFICATION_CLICK = "KEY_IS_NOTIFICATION_CLICK";

    /**
     * the Constant KEY_REQ_CODE
     */
    public static String KEY_REQ_CODE = "KEY_REQ_CODE";

    /**
     * logging user email
     */
    public static String KEY_USER_LOGIN_EMAIL = "KEY_USER_LOGIN_EMAIL";
    /**
     * logging user contact
     */
    public static String KEY_USER_CONTACT = "KEY_USER_CONTACT";
    /**
     * the Constant user model
     */
    public static String KEY_USER_MODEL = "KEY_USER_MODEL";
    /**
     * the Constant KEY_USER_LOAD_RIGHTS model
     */
    public static String KEY_USER_LOAD_RIGHTS = "KEY_USER_LOAD_RIGHTS";
    /**
     * the Constant KEY_USER_ACCESS_TYPE model
     */
    public static String KEY_USER_ACCESS_TYPE = "KEY_USER_ACCESS_TYPE";

    /**
     * the Constant KEY_USER_LOAD_DISTRICT model
     */
    public static String KEY_USER_LOAD_DISTRICT = "KEY_USER_LOAD_DISTRICT";

    /**
     * the Constant KEY_USER_LOAD_GODOWN model
     */
    public static String KEY_USER_LOAD_GODOWN = "KEY_USER_LOAD_GODOWN";

    /**
     * the Constant KEY_USER_LOAD_COMMODITY model
     */
    public static String KEY_USER_LOAD_COMMODITY = "KEY_USER_LOAD_COMMODITY";


    /**
     * the Constant user model
     */
    public static String KEY_USER_PERSONAL_INFORMATON = "KEY_USER_PERSONAL_INFORMATON";

    private Context context;
    // Shared Preferences
    private SharedPreferences sharedPref;

    /**
     * The constant KEY_USER_LOGIN_PASS_WORD.
     */
    public static final String KEY_USER_LOGIN_PASS_WORD = "KEY_USER_LOGIN_PASS_WORD";

    /**
     * The constant KEY_DASHBOARD_MODEL.
     */
    public static final String KEY_DASHBOARD_DATA = "KEY_DASHBOARD_DATA";
    /**
     * The constant KEY_FUN_NAME.
     */
    public static final String KEY_FUN_NAME = "KEY_FUN_NAME";
    /**
     * The constant KEY_USER_ID.
     */
    public static final String KEY_USER_ID = "KEY_USER_ID";
    /**
     * The constant KEY_LOGIN_TOKEN.
     */
    public static final String KEY_LOGIN_TOKEN = "KEY_LOGIN_TOKEN";
    /**
     * The constant KEY_DISTRICT_NAME.
     */
    public static final String KEY_DISTRICT_NAME = "KEY_DISTRICT_NAME";
    /**
     * The constant KEY_DISTRICT_NAME_ENG.
     */
    public static final String KEY_DISTRICT_NAME_ENG = "KEY_DISTRICT_NAME_ENG";
    /**
     * The constant KEY_START_DATE.
     */
    public static final String KEY_START_DATE = "KEY_START_DATE";
    /**
     * The constant KEY_END_DATE.
     */
    public static final String KEY_END_DATE = "KEY_END_DATE";
    /**
     * The constant KEY_DISTRICT_ID.
     */
    public static final String KEY_DISTRICT_ID = "KEY_DISTRICT_ID";
    /**
     * The constant KEY_GO_DOWN_ID.
     */
    public static final String KEY_GO_DOWN_ID = "KEY_GO_DOWN_ID";
    /**
     * The constant KEY_COMMODITY_ID.
     */
    public static final String KEY_COMMODITY_ID = "KEY_COMMODITY_ID";
    /**
     * The constant KEY_SCHEME_ID.
     */
    public static final String KEY_SCHEME_ID = "KEY_SCHEME_ID";
    /**
     * The constant KEY_SUPPLIER_DEPOT_ID.
     */
    public static final String KEY_SUPPLIER_DEPOT_ID = "KEY_SUPPLIER_DEPOT_ID";
    /**
     * The constant KEY_FCI_AREA_OFFICE_ID.
     */
    public static final String KEY_FCI_AREA_OFFICE_ID = "KEY_FCI_AREA_OFFICE_ID";
    /**
     * The constant KEY_TRANSPORTER_ID.
     */
    public static final String KEY_TRANSPORTER_ID = "KEY_TRANSPORTER_ID";
    /**
     * The constant KEY_GO_DOWN_NAME.
     */
    public static final String KEY_GO_DOWN_NAME = "KEY_GO_DOWN_NAME";
    /**
     * The constant KEY_GO_DOWN_NAME_ENG.
     */
    public static final String KEY_GO_DOWN_NAME_ENG = "KEY_GO_DOWN_NAME_ENG";
    /**
     * The constant KEY_FCI_AREA_OFFICE_NAME.
     */
    public static final String KEY_FCI_AREA_OFFICE_NAME = "KEY_FCI_AREA_OFFICE_NAME";
    /**
     * The constant KEY_FCI_AREA_OFFICE_NAME_ENG.
     */
    public static final String KEY_FCI_AREA_OFFICE_NAME_ENG = "KEY_FCI_AREA_OFFICE_NAME_ENG";
    /**
     * The constant KEY_SUPPLIER_NAME.
     */
    public static final String KEY_SUPPLIER_NAME = "KEY_SUPPLIER_NAME";
    /**
     * The constant KEY_SUPPLIER_NAME_ENG.
     */
    public static final String KEY_SUPPLIER_NAME_ENG = "KEY_SUPPLIER_NAME_ENG";
    /**
     * The constant KEY_TRANSPORTER_NAME.
     */
    public static final String KEY_TRANSPORTER_NAME = "KEY_TRANSPORTER_NAME";
    /**
     * The constant KEY_TRANSPORTER_NAME_ENG.
     */
    public static final String KEY_TRANSPORTER_NAME_ENG = "KEY_TRANSPORTER_NAME_ENG";
    /**
     * The constant KEY_SCHEME_NAME.
     */
    public static final String KEY_SCHEME_NAME = "KEY_SCHEME_NAME";
    /**
     * The constant KEY_SCHEME_NAME_ENG.
     */
    public static final String KEY_SCHEME_NAME_ENG = "KEY_SCHEME_NAME_ENG";
    /**
     * The constant KEY_COMMODITY_NAME.
     */
    public static final String KEY_COMMODITY_NAME = "KEY_COMMODITY_NAME";
    /**
     * The constant KEY_COMMODITY_NAME_ENG.
     */
    public static final String KEY_COMMODITY_NAME_ENG = "KEY_COMMODITY_NAME_ENG";
    /**
     * The constant KEY_SUPPLIER_DEPOT_NAME.
     */
    public static final String KEY_SUPPLIER_DEPOT_NAME = "KEY_SUPPLIER_DEPOT_NAME";
    /**
     * The constant KEY_SUPPLIER_DEPOT_NAME_ENG.
     */
    public static final String KEY_SUPPLIER_DEPOT_NAME_ENG = "KEY_SUPPLIER_DEPOT_NAME_ENG";
    /**
     * The constant KEY_SOURCE_TYPE.
     */
    public static final String KEY_SOURCE_TYPE = "KEY_SOURCE_TYPE";
    /**
     * The constant KEY_SOURCE_TYPE_ID.
     */
    public static final String KEY_SOURCE_TYPE_ID = "KEY_SOURCE_TYPE_ID";
    /**
     * The constant KEY_SOURCE_TYPE_NAME.
     */
    public static final String KEY_SOURCE_TYPE_NAME = "KEY_SOURCE_TYPE_NAME";
    /**
     * The constant KEY_SOURCE_ID.
     */
    public static final String KEY_SOURCE_ID = "KEY_SOURCE_ID";
    /**
     * The constant KEY_SOURCE_NAME.
     */
    public static final String KEY_SOURCE_NAME = "KEY_SOURCE_NAME";
    /**
     * The constant KEY_STATUS_ID.
     */
    public static final String KEY_STATUS_ID = "KEY_STATUS_ID";
    /**
     * The constant KEY_STATUS_NAME.
     */
    public static final String KEY_STATUS_NAME = "KEY_STATUS_NAME";
    /**
     * The constant KEY_FPS_MONTH_YEAR.
     */
    public static final String KEY_FPS_MONTH_YEAR = "KEY_FPS_MONTH_YEAR";
    /**
     * The constant KEY_FPS_MONTH.
     */
    public static final String KEY_FPS_MONTH = "KEY_FPS_MONTH";
    /**
     * The constant KEY_FPS_YEAR.
     */
    public static final String KEY_FPS_YEAR = "KEY_FPS_YEAR";
    /**
     * The constant KEY_ON_DATE.
     */
    public static final String KEY_ON_DATE = "KEY_ON_DATE";
    /**
     * The constant KEY_ON_DATE_ID.
     */
    public static final String KEY_ON_DATE_ID = "KEY_ON_DATE_ID";
    /**
     * The constant KEY_ON_DATE_NAME.
     */
    public static final String KEY_ON_DATE_NAME = "KEY_ON_DATE_NAME";
    /**
     * The constant KEY_FCI_GODOWN_NAME.
     */
    public static final String KEY_FCI_GODOWN_NAME = "KEY_FCI_GODOWN_NAME";
    /**
     * The constant KEY_FCI_GODOWN_ID.
     */
    public static final String KEY_FCI_GODOWN_ID = "KEY_FCI_GODOWN_ID";
    /**
     * The constant KEY_RO_ISSUED.
     */
    public static final String KEY_RO_ISSUED = "KEY_RO_ISSUED";

    /**
     * The constant KEY_ADVANCED_PAYMENT_TO_FCI.
     */
    public static final String KEY_ADVANCED_PAYMENT_TO_FCI = "KEY_ADVANCED_PAYMENT_TO_FCI";

    /**
     * The constant KEY_REFER_CODE.
     */
    public static final String KEY_REFER_CODE = "KEY_REFER_CODE";
    /**
     * The constant KEY_BOOK_ID.
     */
    public static final String KEY_WISH_LIST = "KEY_WISH_LIST";

    /**
     * The constant KEY_SUBSCRIPTION_STATUS.
     */
    public static final String KEY_SUBSCRIPTION_STATUS = "KEY_SUBSCRIPTION_STATUS";
    /**
     * The constant KEY_SUBSCRIPTION_ID.
     */
    public static final String KEY_SUBSCRIPTION_ID = "KEY_SUBSCRIPTION_ID";
    /**
     * The constant KEY_IS_AUTO_LOGIN.
     */
    public static final String KEY_IS_AUTO_LOGIN = "KEY_IS_AUTO_LOGIN";

    public static final String KEY_DATE = "KEY_DATE";

    public static final String KEY_IS_DASHBOARD_DATA_LOADED = "KEY_IS_DASHBOARD_DATA_LOADED";

    /**
     * Instantiates a new Preferences.
     *
     * @param context the context
     */
    public Preferences(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    /**
     * Gets instance.
     *
     * @param c the c
     * @return the instance
     */
    public static Preferences getInstance(Context c) {
        if (instance == null) {
            instance = new Preferences(c);
        }
        return instance;
    }

    /**
     * Gets shared pref.
     *
     * @return the shared pref
     */
    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key) {
        return sharedPref.getString(key, AppConstant.DEFAULT_STRING);
    }

    /**
     * Sets string.
     *
     * @param key   the key
     * @param value the value
     */
    public void setString(String key, String value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        String preferenceValue = value;
        sharedPrefEditor.putString(key, preferenceValue);
        sharedPrefEditor.commit();
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public int getInt(String key) {
        return sharedPref.getInt(key, AppConstant.DEFAULT_INTEGER);
    }

    /**
     * Sets string.
     *
     * @param key   the key
     * @param value the value
     */
    public void setInt(String key, int value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.putInt(key, value);
        sharedPrefEditor.apply();
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public Set<String> getStringSet(String key) {
        return sharedPref.getStringSet(key, new HashSet<String>());
    }

    /**
     * Sets string.
     *
     * @param key   the key
     * @param value the value
     */
    public void setStringSet(String key, ArrayList<String> value) {
        Set<String> strSet = new HashSet<>();
        for (int i = 0; i < value.size(); i++) {
            strSet.add(value.get(i));
        }
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.putStringSet(key, strSet);
        sharedPrefEditor.apply();
    }

    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean getBoolean(String key) {
        return sharedPref.getBoolean(key, false);
    }

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.putBoolean(key, value);
        sharedPrefEditor.apply();
    }

    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public long getLong(String key) {
        return sharedPref.getLong(key, 0);
    }

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void setLong(String key, long value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.putLong(key, value);
        sharedPrefEditor.apply();
    }


    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public String[] getAllIds(String key) {
        String ids = sharedPref.getString(key, "");
        return !ids.isEmpty() ? ids.substring(0, ids.length() - 1).split(",") : new String[0];
    }

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    public void addIds(String key, long value) {
        String ids = sharedPref.getString(key, "");
        if (!ids.contains(Long.toString(value))) {
            SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
            sharedPrefEditor.putString(key, ids + (value + ","));
            sharedPrefEditor.apply();
        }
    }

    /**
     * Rome one.
     */
    public void remove(String key) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.remove(key).apply();
    }

    /**
     * Clear all.
     */
    public void clearAll() {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.clear().apply();
    }


}
