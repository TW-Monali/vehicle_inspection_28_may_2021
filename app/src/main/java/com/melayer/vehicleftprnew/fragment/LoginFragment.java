package com.melayer.vehicleftprnew.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.melayer.vehicleftprnew.Main2Activity;
import com.melayer.vehicleftprnew.R;
import com.melayer.vehicleftprnew.UserSessionManager;
import com.melayer.vehicleftprnew.activity.MainActivity;
import com.melayer.vehicleftprnew.database.repository.RepoImplLogin;
import com.melayer.vehicleftprnew.database.repository.RepoLogin;
import com.melayer.vehicleftprnew.web.Url;
import com.melayer.vehicleftprnew.web.Ws;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 22/8/16.
 */
public class   LoginFragment extends Fragment
{
    public static final String KEY_FRAGMENT_NAME = "key_fragment_name";
    public static final int PERMISSION_READ_PHONE_STATE = 5;
    Button button;
    public String onlineVersion,currentVersion;
    SharedPreferences sharedPreferences;
    UserSessionManager session;
    public static final String MyPREFERENCES = "login" ;
    TelephonyManager telephonyManager;
   // public static final String Name = "nameKey";
   // public static final String Password = "passwordKey";
    EditText ed1,ed2;
    String name,pass;
    private String Details;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sharedPreferences=this.getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        Log.e("inside shared pref",MyPREFERENCES);
        if(sharedPreferences.contains("name") && sharedPreferences.contains("password")) {
            getParent().runFragmentTransaction(R.id.frameMainContainer,GridFragment.newInstance());
            getFragmentManager().beginTransaction().remove(this);
        }
        new UpdateTask().execute();

        ///////////////
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            Log.e("Main2", "Inside if " );
//            Details = Settings.Secure.getString(
//                    context.getContentResolver(),
//                    Settings.Secure.ANDROID_ID);
            Details= Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);

        } else {
            Log.e("Main2", "Inside else " );
            telephonyManager= (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);

            if (telephonyManager.getDeviceId() != null) {
                Details = telephonyManager.getDeviceId();
            }
            else {

                Details= Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
//                Details = Settings.Secure.getString(
//                        context.getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
            }
        }
        ////////////////////////





    }

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FRAGMENT_NAME, "FragmentLogin");
        loginFragment.setArguments(args);
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_login,container,false);
        initLoginButton(rootView);
        button=(Button)rootView.findViewById(R.id.btreg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Main2Activity.class);
                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });
        initRegisterButton(rootView);
        new UpdateTask().execute();
        return rootView;
    }

    private void initRegisterButton(View rootView) {

        rootView.findViewById(R.id.btreg);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent;
                intent = new Intent(getActivity(), Main2Activity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });


    }

    private MainActivity getParent()

    {

        return (MainActivity) getActivity();
    }

    private void initLoginButton(final View rootView) {
        rootView.findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserNameValid(rootView) && isPasswordValid(rootView) ) {
                    if (getParent().isNetworkAvailable()) {
                        boolean a=isUserNameValid(rootView);
                        Log.e("a", String.valueOf(a));
                        boolean b=isPasswordValid(rootView);
                        Log.e("b", String.valueOf(b));
                        registerUser();

                    }
                    else {
                        getParent().snack(rootView, "Unable to connect to the server:(");
                    }
                }

            }
        });
    }


    private final Boolean isUserNameValid(final View rootView) {
        Boolean isValid = false;
        final Drawable drawableError = ContextCompat.getDrawable(getContext(), R.drawable.ic_error);
        drawableError.setBounds(0, 0, 50, 50);
        if (getUserName(rootView).getText().length() < 1) {
            getUserName(rootView).setError("Username required", drawableError);
            isValid = false;
        } else {
            getUserName(rootView).setError(null);
            getUserName(rootView).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done, 0);
            isValid = true;
        }
        return isValid;
    }

    private final Boolean isPasswordValid(final View rootView) {
        Boolean isValid = false;
        final Drawable drawableError = ContextCompat.getDrawable(getContext(), R.drawable.ic_error);
        drawableError.setBounds(0, 0, 50, 50);
        if (getPassword(rootView).getText().length() < 1) {
            getPassword(rootView).setError("Password required", drawableError);
            isValid = false;
        } else {
            getPassword(rootView).setError(null);
            getPassword(rootView).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done, 0);
            isValid = true;
        }
        return isValid;
    }
    private EditText getUserName(View rootView){
        // return (EditText) rootView.findViewById(R.id.edtUserName);
        ed1=(EditText)rootView.findViewById(R.id.edtUserName);
        String name=ed1.getText().toString();
        Log.e("name",name);
        return ed1;
    }
    private EditText getPassword(View rootView){
        // return (EditText) rootView.findViewById(R.id.edtPassword);
        ed2=(EditText)rootView.findViewById(R.id.edtPassword);
        String pass=ed2.getText().toString();
        Log.e("password",pass);
        return ed2;
    }
//    private String getImei() {
//        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//        @SuppressLint("MissingPermission") String imei = manager.getDeviceId();
//        return (imei != null & imei.length() > 0) ? imei : "none";
//    }





    public void loginUser() {

        final ProgressDialog progressDialog = ProgressDialog.show(getParent(),"Login", "Checking credentials");
        //Log.i(MainActivity.TAG,"Imei in login : " +getImei());
        final JSONObject obj = new JSONObject();
        try {
            obj.put("userName",getUserName(getView()).getText().toString());
            obj.put("password", getPassword(getView()).getText().toString());
            obj.put("imei",Details);
            obj.put("currentDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            Log.i(MainActivity.TAG, "obj to send- " + obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String urlToLogin =Url.URL_LOGIN +"?userName="+getUserName(getView()).getText().toString()
                +"&password="+getPassword(getView()).getText().toString()+"&imei="+Details+"&currentDate="+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Log.i(MainActivity.TAG, "Login url - " + urlToLogin);


        try {

        }
        catch (Exception e){

        }
        Ws.getQueue(getContext()).add(new JsonObjectRequest(com.android.volley.Request.Method.POST, urlToLogin,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(MainActivity.TAG, "response b4 if - " + response);

                        if (progressDialog !=  null && progressDialog.isShowing())
                            progressDialog.dismiss();
                        if (response != null) {
                            Log.i(MainActivity.TAG, "response after if - " + response);
                            try {
                                if (response.getString("status").equals("success")) {
                                    Log.e(MainActivity.TAG, "response - " + response.getString("status"));
                                    storeUser(response);
                                    getParent().runFragmentTransaction(R.id.frameMainContainer, GridFragment.newInstance());
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name",ed1.getText().toString());
                                    editor.putString("password",ed2.getText().toString());
                                    editor.commit();
                                   Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                   // AestheticDialog.showToaster(this, "Your dialog Title", "Your message", AestheticDialog.SUCCESS);
                                }
                                if (response.getString("status").equals("fail")) {
                                    getParent().snack(getView(),"Invalid Credentials");
                                    Log.e("login", "onResponse: status "+response.getString("status") );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("login", "onResponse: catch" + e.getMessage() );
                            }

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* Log.i(MainActivity.TAG, "Error : "+error.toString());
                getParent().snack(getView(),"Server Not Responding...");*/
                NetworkResponse response = error.networkResponse;
                Log.e("login", "onErrorResponse: " + error.getMessage());
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        Log.e("res",res);
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        Log.e("e1",e1.getMessage());
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        Log.e("e2",e2.getMessage());
                        e2.printStackTrace();
                    }
                }
                progressDialog.dismiss();
                getParent().snack(getView(),"Invalid Credentials");

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
            {
                setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48;
                    }
                    @Override
                    public int getCurrentRetryCount() {
                        return 0;
                    }
                    @Override
                    public void retry(VolleyError error) throws VolleyError {
                        if (getView()!=null)
                            getParent().snack(getView(),"Internet not available!");
                    }
                });
            }
        });
    }

    private void storeUser(JSONObject response) {
        RepoLogin repoLogin = new RepoImplLogin(getParent().getDbHelper());
        try {
            JSONObject jsonObjectUser = response.getJSONObject("user");
            repoLogin.saveUserCredentials(jsonObjectUser.getString("userName"),jsonObjectUser.getInt("userId"));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_READ_PHONE_STATE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) registerUser();
            }
        }
    }

    private void registerUser() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_PHONE_STATE);
            Log.i(MainActivity.TAG, "In permission");
            return;
        }
        if (getParent().isNetworkAvailable()) {
            loginUser();
        }else getParent().snack(getView(),"Internet Not Available..");

    }

    class UpdateTask extends AsyncTask<Void,String,String>
    {


        @Override
        protected String doInBackground(Void... params)
        {

            String newVersion=null;

            try
            {

                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;



            }
            catch (Exception e)
            {

                return newVersion;
            }

        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion))
                {



                    Toast.makeText(getActivity().getApplicationContext(), " NEW VERSION IS AVAIABLE :"+onlineVersion, Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    int requestCode = 0;
                    PendingIntent pendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
                    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    NotificationCompat.Builder noBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getActivity().getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText("https://play.google.com/store/apps/details?id=com.melayer.vehicleftpnew"))
                            .setContentTitle("VFTPR Update")
                            .setAutoCancel(true).setContentIntent(pendingIntent);

                    NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, noBuilder.build()); //0 = ID of notification

                }
                else
                {


                    Toast.makeText(getActivity().getApplicationContext(), "NO....NO...", Toast.LENGTH_SHORT).show();


                }
            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);


        }
    }
}