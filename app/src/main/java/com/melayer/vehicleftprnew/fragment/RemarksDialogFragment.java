package com.melayer.vehicleftprnew.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melayer.vehicleftprnew.EmailAPI;
import com.melayer.vehicleftprnew.Main2Activity;
import com.melayer.vehicleftprnew.MyLogger;
import com.melayer.vehicleftprnew.R;
import com.melayer.vehicleftprnew.TWsimpleMailSender;
import com.melayer.vehicleftprnew.activity.MainActivity;
import com.melayer.vehicleftprnew.database.repository.RepoImplLogin;
import com.melayer.vehicleftprnew.database.repository.RepoImplRegisterVehicle;
import com.melayer.vehicleftprnew.database.repository.RepoLogin;
import com.melayer.vehicleftprnew.database.repository.RepoRegisterVehicle;
import com.melayer.vehicleftprnew.domain.Module;
import com.melayer.vehicleftprnew.imageCompression.MeTaskImageCompression;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by root on 25/8/16.
 */
public class RemarksDialogFragment extends DialogFragment {
    public final static Integer CAMERA_REQUEST_CHECKPOINTS = 1001;
    private String imagePath;
    private OnDismissListener dismissListener;
    public static final int MY_PERMISSIONS_REQUESTS = 6;
    String bodyDetails,subjectline;
    String name,vehicleId,vehicleNo,userId,unitId,checkPointId,checkPointName;
    DateFormat dateFormat;
    android.content.Context Context;




    public static RemarksDialogFragment getInstance() {
        RemarksDialogFragment dialogFragment = new RemarksDialogFragment();
        Bundle bundle = new Bundle();
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }


    public void setOnDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_remarks, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initImageView(rootView);
        initTextView(rootView);

        getDialog().setCanceledOnTouchOutside(false);
        if (getTag().equals("Helmet Condition"))
            ((EditText) rootView.findViewById(R.id.edtRemarks)).setHint(getResources().getString(R.string.remark));
        else
            ((EditText) rootView.findViewById(R.id.edtRemarks)).setHint(getResources().getString(R.string.remarks));
        initButton(rootView);
        return rootView;
    }

    private MainActivity getParent() {
        return (MainActivity) getActivity();
    }

    private void initButton(final View rootView) {
        rootView.findViewById(R.id.btnCloseDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTag().equals("Helmet Condition") && isImageCapture()) {
                    dismiss();
                }
                if (isRemarksFilled() && isImageCapture()) {
                    dismiss();
                } else {
                    if (!isImageCapture())
                        getParent().snack(rootView, getResources().getString(R.string.imageError));
                    if (!isRemarksFilled() && !getTag().equals("Helmet Condition"))
                        getParent().snack(rootView, getResources().getString(R.string.remarksError));
                    if (!isImageCapture() && !isRemarksFilled() && !getTag().equals("Helmet Condition"))
                        getParent().snack(rootView, "Image and remarks required!!");
                }
                String SENDMAIL= " image:"+ imagePath;
                new message1(SENDMAIL).execute();
                //new MailAction().execute();
                Log.e("SUbmit ", "Called1");
                    //new MailAction().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,hostpop3, portpop3, userNamepop3, passwordpop3, subpop3);
                    // new MailAction().execute(hostpop3, portpop3, userNamepop3, passwordpop3, subpop3);
                    Log.e("b5555","validation");

            }
        });
    }

    private boolean isRemarksFilled() {
        return (!((EditText) getView().findViewById(R.id.edtRemarks)).getText().toString().isEmpty());
    }

    private boolean isImageCapture() {
        return imagePath != null;
    }

    private void initTextView(View rootView) {
        ((TextView) rootView.findViewById(R.id.textHeader)).setText(getTag());
    }

    private void initImageView(View rootView) {
        rootView.findViewById(R.id.imgAddPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent();
            }
        });
        rootView.findViewById(R.id.imageClosedTagAddPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePath = null;
                ((ImageView) getView().findViewById(R.id.imageViewAddPic)).setImageBitmap(null);
                getView().findViewById(R.id.imageViewAddPic).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.imageClosedTagAddPic).setVisibility(View.GONE);
                getView().findViewById(R.id.imgAddPhoto).setVisibility(View.VISIBLE);
            }
        });
    }

    private void openImageIntent() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQUEST_CHECKPOINTS);
            return;
        }
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CHECKPOINTS);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CHECKPOINTS) {
            if (grantResults.length > 0) {
                // checkCameraPresent("1");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CHECKPOINTS);
                }
            }
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(getTag(), (imagePath != null ? imagePath : ""),
                    ((TextView) getView().findViewById(R.id.edtRemarks)).getText().toString());
        } else {
            getParent().snack(getView(), getResources().getString(R.string.remarksError));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CHECKPOINTS) {
            //isCamera(data);
            if (resultCode == Activity.RESULT_OK) {
                Bitmap imagebitmap = (Bitmap) data.getExtras().get("data");
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getParent().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
                //Log.e("image for checkpoint1",imagePath);
                int column_index_data = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToLast();
                imagePath = cursor.getString(column_index_data);
                Log.e("image for checkpoint2",imagePath);
                //imageClosedTagAddPic.setImageBitmap(imagebitmap);
                Bitmap bitmapImage = BitmapFactory.decodeFile(imagePath);
                int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaled, 0, 0, scaled.getWidth(), scaled.getHeight(), matrix, true);
                getView().findViewById(R.id.imageViewAddPic).setVisibility(View.VISIBLE);
                ((ImageView) getView().findViewById(R.id.imageViewAddPic)).setImageBitmap(rotatedBitmap);
                Log.i(MainActivity.TAG, "Image : " + imagePath);
                MeTaskImageCompression taskImageCompression = new MeTaskImageCompression(getActivity(), imagePath);
                taskImageCompression.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imagePath);
                getView().findViewById(R.id.imgAddPhoto).setVisibility(View.GONE);
                getView().findViewById(R.id.imageViewAddPic).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.imageClosedTagAddPic).setVisibility(View.VISIBLE);
            }
        }
    }

    public interface OnDismissListener {
        void onDismiss(String inspectionTag, String path, String reason);
    }

    public class message1 extends AsyncTask
        {

            String MAIL;
            public message1(String MAIL)
            {

                this.MAIL = MAIL;
            }

            @Override
            protected Object doInBackground(Object[] params)

            {
                subjectline = "FTPR Checkpoints" ;
               // subjectline = "FTPR Checkpoints:" + " " + imagePath ;
//                bodyDetails = name.trim() + ","
//                        + vehicleId.trim() + ","
//                        + vehicleNo.trim() + ","
//                        + checkPointId.trim() + ","
//                        + checkPointName.trim() + "\n";
//                Log.e("bodydetails", bodyDetails);
//                Log.e("Subjectline", subjectline);

                TWsimpleMailSender mTWsimpleMailSender = new TWsimpleMailSender(Context, "75302", "transworld", "a.mobileeye.in", "2525");

                try
                {

                    boolean flag = mTWsimpleMailSender.sendMail(subjectline, MAIL, "8805", "ddmyinputsfwd@twtech.in", imagePath);
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

                //Toast.makeText(CheckListFragment.this, "Processing.....", Toast.LENGTH_SHORT).show();

                Log.i("DD","IN THE PRE EXCECUTE ");


                //pDialog = new ProgressDialog(Main2Activity.this);
                //pDialog.setMessage("Please wait.....");
                //pDialog.show();

                super.onPreExecute();
            }



    }
}
