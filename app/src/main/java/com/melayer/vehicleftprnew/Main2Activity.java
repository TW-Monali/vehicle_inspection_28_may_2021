package com.melayer.vehicleftprnew;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{


   Context context;
    String appname2="Vehicle FTPR";
    android.content.Context Context;

    private Button btnSubmit;
    public static final String KEY_FRAGMENT_NAME = "key_fragment_name";
    private TextView timei,textView,tv;
    private EditText etxFirstName, etxLastName, etxNumber, etxMail,etxCompanyName,etxAppname;
    String SENDMAIL;

    TextView appname;

    HttpURLConnection conn = null;
    static int interval = 40000;
    String tempURL;
    private String  imei, emailId;
    private ProgressDialog pDialog;
    private String fname, lname, email,no,cname,cimei;
    TelephonyManager telephonyManager;
    private String SIMSERIAL,IMEINO,Details;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.e("Main2Act", " On Create Method :-----");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.e("Main2Act", " Hereeeeeeeeeee :-----");


        setTitle("MobileEye");


//        if (Build.VERSION.SDK_INT > 9)
//        {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            telephonyManager= (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//
//           // SIMSERIAL=telephonyManager.getSimSerialNumber();
//
//
//            timei= (TextView) findViewById(R.id.textView2);
////            timei.setText(telephonyManager.getDeviceId().toString());
//
//            Details=telephonyManager.getDeviceId().toString();
//
//        }



//        telephonyManager = (TelephonyManager) this.getSystemService(getApplicationContext().TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling/media/twtech/6D33-6396/login
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            Log.e("Main2", "Inside if " );
//            Details = Settings.Secure.getString(
//                    context.getContentResolver(),
//                    Settings.Secure.ANDROID_ID);
           Details= Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        } else {
            Log.e("Main2", "Inside else " );
            telephonyManager= (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

            if (telephonyManager.getDeviceId() != null) {
                Details = telephonyManager.getDeviceId();
              }
            else {

                Details= Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
//                Details = Settings.Secure.getString(
//                        context.getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
            }
        }




        //appname= (TextView) findViewById(R.id.appname);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etxCompanyName = (EditText) findViewById(R.id.etxCompanyName);

        etxFirstName = (EditText) findViewById(R.id.etxFirstName);
        etxLastName = (EditText) findViewById(R.id.etxLastName);
        etxNumber = (EditText) findViewById(R.id.etxNumber);
        etxMail = (EditText) findViewById(R.id.etxMail);
        timei=(TextView) findViewById(R.id.textView2);
        // textView=(TextView)findViewById(R.id.textView1);
        //tv=(TextView)findViewById(R.id.textView3) ;


        //appname.setText(" "+appname2);


        btnSubmit.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v)
            {

                Log.i("DD","IN THE ON CLLICK");

                fname = etxFirstName.getText().toString();
                lname = etxLastName.getText().toString();
                email = etxMail.getText().toString();
                no = etxNumber.getText().toString();
                cname = etxCompanyName.getText().toString();
                cimei=timei.getText().toString();
                // capp=etxAppname.getText().toString();


                if (TextUtils.isEmpty(fname))
                {
                    etxFirstName.setError("Please fill the details");
                    return;
                }
                if(TextUtils.isEmpty(lname))
                {
                    etxLastName.setError("Please fill the details");
                    return;

                }
                if(TextUtils.isEmpty(email))
                {
                    etxMail.setError("Please fill the details");
                    return;
                }

                if(TextUtils.isEmpty(no))
                {
                    etxNumber.setError("Please fill the details");
                    return;
                }

                if (TextUtils.isEmpty(cname))
                {
                    etxCompanyName.setError("Please fil the details");
                    return;
                }
                /*if (TextUtils.isEmpty(capp))
                {
                    etxAppname.setError("Please fil the details");
                    return;
                }*/

                String SENDMAIL= "NAME:"+ fname+"\n LAST NAME:"+ lname+"\n Email:"+ email+"\n Phone NO:"+ no+"\n IMEI No:"+Details+"\n Company Name:"+cname+" \n App Name:"+appname2;
                new  message(SENDMAIL).execute();



                /*runFragmentTransaction(R.id.main2activity,LoginFragment.newInstance());
*/
               /* LoginFragment fragment = new LoginFragment();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main2activity, fragment);
                transaction.commit();*/

                //startService(new Intent(getApplicationContext(),TWattachmentMailSender.class));


                // run();


            }
        });


    }

    public class message extends AsyncTask
    {

        String MAIL;
        public message(String MAIL)
        {

            this.MAIL = MAIL;
        }

        @Override
        protected Object doInBackground(Object[] params)

        {
            //TWattachmentMailSender mailSender = new TWattachmentMailSender("twtech1020", "9175886657", "smtp.gmail.com.", "587");
           // TWsimpleMailSender mailSender = new TWsimpleMailSender("twtech1020", "9175886657", "smtp.gmail.com.", "587");

            TWsimpleMailSender mTWsimpleMailSender = new TWsimpleMailSender(Context, "75302", "transworld", "a.mobileeye.in", "2525");

            try
            {
               // mailSender.sendMail1( "Registration Request for "+ email +" for  FTPR" , "CONTENT:\n\n "+MAIL.toString(), "LOG", "9100", "AppRegister@twtech.in");
                boolean flag = mTWsimpleMailSender.sendMail(email, MAIL, "8805", "ftpr3vehicleinspection@gmail.com");
                Log.e("Main2", "doInBackground: "+flag);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Main2", "Insidecatch: "+e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute()
        {

            Toast.makeText(Main2Activity.this, "Processing.....", Toast.LENGTH_SHORT).show();

            Log.i("DD","IN THE PRE EXCECUTE ");


            pDialog = new ProgressDialog(Main2Activity.this);
            pDialog.setMessage("Please wait.....");
            pDialog.show();

            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Object o)
        {
            pDialog.dismiss();

            Toast.makeText(Main2Activity.this, "MAIL SENT", Toast.LENGTH_SHORT).show();

            super.onPostExecute(o);
            Intent i=new Intent(Main2Activity.this,com.melayer.vehicleftprnew.activity.MainActivity.class);
            startActivity(i);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)

    {

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private void entryFileDetails()
    {


        try {

            Log.i("DD","IN THE ENTRY DETAILS ");


            //String newurl = "http://103.241.181.36:8080/DeviceIMEITracker/test?deviceId=983469844&Mailid=%27test&gamil.com%27&Mobno=%27978759067";

            //String strUrl1 = "http://twtech.in:8080/SmartGridWebService1/rest/transporter?rqst=imagedata&deviceid=" + URLEncoder.encode(unitid, "UTF-8") + "&unitid=" + URLEncoder.encode(unitid, "UTF-8") + "&camerapos=" + URLEncoder.encode(camerapos, "UTF-8") + "&snapdate=" + URLEncoder.encode(filedatenew[0], "UTF-8") + "&snaptime=" + URLEncoder.encode(finalfiletimenew, "UTF-8") + "&rawtime=" + URLEncoder.encode(files, "UTF-8") + "&jpgfilename=" + URLEncoder.encode(newFile, "UTF-8") + "&Imgcategory=-&snapdatetime=" + URLEncoder.encode(snapdatetime, "UTF-8");
            String newurl = "http://103.241.181.36:8080/DeviceIMEITracker1/test?firstname="+fname+"&lastname="+lname+"&Mailid="+email+"&mobno="+no;
            URL url = new URL(newurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            if (conn.getResponseCode() == 200)
            {
                new MyLogger().storeMassage("Details inserted", "Sent Successfully...");
                Log.i("DD","RESPONSE CODE 200 ");


               /* BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String apiOutput = br.readLine();
                System.out.println(apiOutput);

                Log.i("DD","API OP  "+apiOutput);
                Log.i("DD","API OP  "+br);*/


                /*String inserted = "";
                try
                {

                    Log.i("DD","INSERTION");

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    InputSource is = new InputSource(new StringReader(apiOutput));
                    Document doc = builder.parse(is);
                    NodeList nodes = doc.getElementsByTagName("Inserted");
                    Element line = (Element) nodes.item(0);
                    inserted = getCharacterDataFromElement(line);



                    Log.i("DD","INSERTED ");

                    Log.i("NOEXCPTN", "No exception comes");

                }
                catch (Exception e)
                {
                    new MyLogger().storeMassage("Exception", "DocumentBuilderFactory- " + e.getMessage());

                    Log.i("EXCPTN", " exception comes :"+e.getMessage());



                }*/

                // new MyLogger().storeMassage("inserted", inserted);

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            new MyLogger().storeMassage("Conn IOException", e.getMessage());
            entryFileDetails();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Main2Activity.this,com.melayer.vehicleftprnew.activity.MainActivity.class);
        startActivity(i);
        finish();
    }
}
