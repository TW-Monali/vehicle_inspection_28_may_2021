package com.melayer.vehicleftprnew.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.melayer.vehicleftprnew.R;
import com.melayer.vehicleftprnew.database.Database;
import com.melayer.vehicleftprnew.database.Helper;
import com.melayer.vehicleftprnew.database.repository.RepoImplLogin;
import com.melayer.vehicleftprnew.database.repository.RepoLogin;
import com.melayer.vehicleftprnew.fragment.GridFragment;
import com.melayer.vehicleftprnew.fragment.LoginFragment;
import com.melayer.vehicleftprnew.prefs.Prefs;

public class MainActivity extends AppCompatActivity

{
    public static final String KEY_FRAGMENT_NAME = "key_fragment_name";
    public static final String TAG = "@vehicleftp";
    public static final int MY_PERMISSIONS_REQUESTS = 5;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private Object UIApplicationStateActive;
    private String currentVersion;
    private String onlineVersion;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }

           if (!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
            else {

            }
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            Prefs.clearKeyLicenseValidity(getApplicationContext());
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            RepoLogin repoLogin = new RepoImplLogin(getDbHelper());
            try {
                Log.i(MainActivity.TAG, "User Id" + repoLogin.getUserId());
               /* if (repoLogin.getUserName().equals("")) {
                    runFragmentTransaction(R.id.frameMainContainer, LoginFragment.newInstance());
                }
                else
                    runFragmentTransaction(R.id.frameMainContainer, GridFragment.newInstance());*/
                SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
                if(sp.contains("name") && sp.contains("password")) {
                    Log.e("inside shared pref1","name");
                    runFragmentTransaction(R.id.frameMainContainer,GridFragment.newInstance());
            } else
                    runFragmentTransaction(R.id.frameMainContainer, LoginFragment.newInstance());
            }catch (Exception e) {
                e.printStackTrace();
            }

            try {
                currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            // Update task called

        }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection, Please connect");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                //dialog.dismiss();
            }
        })

                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder;
    }

    public void snack(View rootView, String message) {
        Snackbar snackBar = Snackbar.make(rootView, (Html.fromHtml("<font color='#FFFFFF'>" + message + "</font>")), Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.snack));
        snackBar.show();
    }
    
    @Override
    public void onBackPressed() {
            removeFragmentFromBackStack();

    }

    public final void removeFragmentFromBackStack()
    {
      Log.i(MainActivity.TAG,"Fragment Name "+getSupportFragmentManager().getBackStackEntryAt
              (getSupportFragmentManager().getBackStackEntryCount() -1).getName());
        if (getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName() != null) {
            if (getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).
                    getName().equals("GridFragment") || getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).
                    getName().equals("FragmentLogin"))
            {
               dialogExitApp();

            }
            else
            {
               super.onBackPressed();
            }
        } else
            {
            dialogExitApp();

        }
        Log.i(MainActivity.TAG, "getSupportFragmentManager().getBackStackEntryCount()" + getSupportFragmentManager().getBackStackEntryCount());
    }

    private void dialogExitApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Sure to Exit?")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public final void popBackStack(Integer count) {
        for (Integer i = 0; i < count; i++) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    public final Fragment runFragmentTransaction(Integer containerId, Fragment fragment)


    {
        final String backStateName = fragment.getArguments().getString(KEY_FRAGMENT_NAME);
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (!fragmentPopped)


        {
            FragmentTransaction txn = manager.beginTransaction();
            txn.replace(containerId, fragment, backStateName);
            txn.addToBackStack(backStateName);
            txn.commitAllowingStateLoss();
        }

        else

            {
            //not Popped
            FragmentTransaction txn = manager.beginTransaction();
            txn.replace(containerId, fragment, backStateName);
            txn.addToBackStack(null);
            txn.commit();
        }

        return fragment;
    }

    public final boolean isNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }

        else
        {
            return false;
        }
    }

    public void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public final Helper getDbHelper() {
        return new Helper(this, Database.DB_NAME, null, Database.DB_VERSION);
    }

    public final String getDeviceUniqueId() {
        TelephonyManager telephonyManager;
        WifiManager wifiManager;
        WifiInfo wifiInfo = null;
        String imei = null;
        String macId = null;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.CAMERA
                    },MY_PERMISSIONS_REQUESTS);
        } else {
            telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//            wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
            wifiInfo = wifiManager.getConnectionInfo();
            macId = wifiInfo.getMacAddress();
        }
        Log.i(TAG, "Imei - " + imei);
        Log.i(TAG, "MacId - " + macId);
        return (((imei != null) && !imei.equals("")) ? imei : macId);

}


}
