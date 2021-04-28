package com.melayer.vehicleftprnew.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.melayer.vehicleftprnew.activity.MainActivity;

/**
 * Created by root on 26/8/16.
 */
public class Prefs {
    public static final String KEY_VEHICLE_NO = "vehicleNo";
    public static final String KEY_VEHICLE_LICENSE_VAL = "vehicleLicenseValidity";
    public static final String PREFS_STORE_VEHICLE = "STORE_VEHICLE";
    public static final String PREFS_STORE_VEHICLE_LICENSE = "STORE_VEHICLE_LICENSE_VALIDITY";
    public static final String PREFS_STORE_MODULE = "STORE_VEHICLE";
    public static final String PREFS_STORE_FLAG = "STORE_FLAG";
    public static final String KEY_FLAG = "flag";
    public static final String KEY_UNIT_NAME = "unit";

    public static void saveFlag(Context context, Boolean flag) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_STORE_FLAG, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY_FLAG, flag);
        editor.commit();
    }

    public static Boolean getKeyFlag(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_STORE_FLAG, Context.MODE_PRIVATE);
        Boolean flag = sharedPreferences.getBoolean(KEY_FLAG, false);
        sharedPreferences = null;
        return flag;
    }

    public static void saveVehicleNo(Context context, String vehicleNo) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_STORE_VEHICLE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_VEHICLE_NO, vehicleNo.trim());
        editor.commit();
    }

    public static String getKeyVehicleNo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_STORE_VEHICLE, Context.MODE_PRIVATE);
        String vehicleName = sharedPreferences.getString(KEY_VEHICLE_NO, "none");
        sharedPreferences = null;
        return vehicleName;
    }

    public static void saveLicenseValidity(Context context, String vehicleLicenseValidity) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_STORE_VEHICLE_LICENSE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_VEHICLE_LICENSE_VAL, vehicleLicenseValidity);
        editor.commit();
    }

    public static String getKeyLicenseValidity(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_STORE_VEHICLE_LICENSE, Context.MODE_PRIVATE);
        String vehicleName = sharedPreferences.getString(KEY_VEHICLE_LICENSE_VAL, "");
        sharedPreferences = null;
        return vehicleName;
    }

    public static void clearKeyLicenseValidity(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_STORE_VEHICLE_LICENSE, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static void saveUnitName(Context context, String unitName) {
        Log.i(MainActivity.TAG, "Unit Name : " + unitName);
        SharedPreferences prefs = context.getSharedPreferences(PREFS_STORE_MODULE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_UNIT_NAME, unitName);
        editor.commit();
    }

    public static String getKeyUnitName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_STORE_MODULE, context.MODE_PRIVATE);
        String unitName = prefs.getString(KEY_UNIT_NAME, "none");
        prefs = null;
        return unitName;
    }
}
