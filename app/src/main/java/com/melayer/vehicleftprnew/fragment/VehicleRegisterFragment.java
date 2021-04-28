package com.melayer.vehicleftprnew.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.melayer.vehicleftprnew.R;
import com.melayer.vehicleftprnew.activity.MainActivity;
import com.melayer.vehicleftprnew.database.repository.RepoImplLogin;
import com.melayer.vehicleftprnew.database.repository.RepoImplRegisterVehicle;
import com.melayer.vehicleftprnew.database.repository.RepoLogin;
import com.melayer.vehicleftprnew.database.repository.RepoRegisterVehicle;
import com.melayer.vehicleftprnew.domain.VehicleRegistration;
import com.melayer.vehicleftprnew.prefs.Prefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 22/8/16.
 */
public class VehicleRegisterFragment extends Fragment {
    public static final String KEY_FRAGMENT_NAME = "key_fragment_name";
    private String dateToWeb;

    public static VehicleRegisterFragment newInstance() {
        VehicleRegisterFragment registerFragment = new VehicleRegisterFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FRAGMENT_NAME, "VehicleRegisterFragment");
        registerFragment.setArguments(args);
        return registerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vehicle_register, container, false);
        Prefs.saveFlag(getContext(), false);
        //Prefs.saveLicenseValidity(getContext(),"");
        ((TextView)rootView.findViewById(R.id.textLicenseValidity)).setText(Prefs.getKeyLicenseValidity(getContext()));
        initButton(rootView);
        initLicenseValidity(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(MainActivity.TAG, "getKeyFlag() : " + Prefs.getKeyFlag(getContext()));
        if (Prefs.getKeyFlag(getContext())) {
            clearData();
        }
        if (!Prefs.getKeyLicenseValidity(getContext()).equals("")) {
            getLicenseValidity().setTextColor(ContextCompat.getColor(getContext(), R.color.date));
            getLicenseValidity().setText(Prefs.getKeyLicenseValidity(getContext()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(MainActivity.TAG, "getKeyFlag() onPause: " + Prefs.getKeyFlag(getContext()));
        if (Prefs.getKeyFlag(getContext())) {
            clearData();
        }
    }

    private void clearData() {
        getVehicleNo().setText("");
        getPsrName().setText("");
        getCellNumber().setText("");
        getStartPlace().setText("");
        getEndPlace().setText("");
        getLicenseNo().setText("");
        getTdmAsmName().setText("");
        getTdmAsm().setText("");
        getDistributor().setText("");
    }

    private void initLicenseValidity(final View rootView) {
        final Calendar calendar = Calendar.getInstance();
        getImageViewCalendar(rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DateListener(),
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try
                {
                    date = sdf.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
                //  datePicker.getDatePicker().setMinDate(date.getTime());
                datePicker.show();
            }
        });
    }


    private MainActivity getParent() {
        return (MainActivity) getActivity();
    }

    private void initButton(View rootView) {

        rootView.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidations()) {
                    Prefs.saveVehicleNo(getContext(), getVehicleNo().getText().toString().trim());
                    //saveVehicleLocally();
                    //by sonali
                    getParent().runFragmentTransaction(R.id.frameMainContainer, CheckListFragment.newInstance(saveDataTemp()));
                } else {
                    showErrors();
                }
            }
        });
    }

    private Boolean checkValidations() {
        Log.i(MainActivity.TAG, "IsTextViewEmpty : " + ((TextView) getView().findViewById(R.id.textLicenseValidity)).getText().toString());
        if (((EditText) getView().findViewById(R.id.edtVehicleNo)).getText().length() > 0
                && ((EditText) getView().findViewById(R.id.edtPsrName)).getText().length() > 0
                && ((EditText) getView().findViewById(R.id.edtCellNumber)).getText().length() == 10
                && ((EditText) getView().findViewById(R.id.edtLicenseNo)).getText().length() > 0
                && ((TextView) getView().findViewById(R.id.textLicenseValidity)).getText().toString().length() > 0
                && ((EditText) getView().findViewById(R.id.edtTdmAsmName)).getText().length() > 0
                && ((EditText) getView().findViewById(R.id.edtTdmAsm)).getText().length() > 0
                && ((EditText) getView().findViewById(R.id.edtDistributor)).getText().length() > 0) {
            return true;
        }
        return false;
    }

    private void showErrors() {
        if (((EditText) getView().findViewById(R.id.edtDistributor)).getText().toString().isEmpty()) {
            getParent().snack(getView(), "Please Enter Distributor Name!!");
        }
        if (((EditText) getView().findViewById(R.id.edtTdmAsm)).getText().toString().isEmpty()) {
            getParent().snack(getView(), "Please Enter TdmAsm!!");
        }
        if (((EditText) getView().findViewById(R.id.edtTdmAsmName)).getText().toString().isEmpty()) {
            getParent().snack(getView(), "Please Enter TdmAsm Name!!");
        }
        if (((TextView) getView().findViewById(R.id.textLicenseValidity)).getText().toString().length() < 1) {
            getParent().snack(getView(), "Please Select License Expiry Date!!");
        }
        if (((EditText) getView().findViewById(R.id.edtLicenseNo)).getText().toString().isEmpty()) {
            getParent().snack(getView(), "Please Enter the License No.!!");
        }
        if (((EditText) getView().findViewById(R.id.edtCellNumber)).getText().toString().length() != 10) {
            if (((EditText) getView().findViewById(R.id.edtCellNumber)).getText().toString().isEmpty()) {
                getParent().snack(getView(), "Please Enter Mobile No.");
            } else {
                getParent().snack(getView(), "Please Enter Valid Mobile No.");
            }
        }
        if (((EditText) getView().findViewById(R.id.edtPsrName)).getText().toString().isEmpty()) {
            getParent().snack(getView(), "Please Enter PSR Name!!");
        }
        if (((EditText) getView().findViewById(R.id.edtVehicleNo)).getText().toString().isEmpty()) {
            getParent().snack(getView(), "Please Enter Vehicle No.!!");
        }
    }

    private Map<String, String> saveDataTemp() {
        Map<String, String> forwordData = new HashMap<>();
        RepoLogin repoLogin = new RepoImplLogin(getParent().getDbHelper());
        forwordData.put("vehicleId", getVehicleNo().getText().toString().trim().replace(" ", "") + "_" + System.currentTimeMillis());
        forwordData.put("vehicleNo", getVehicleNo().getText().toString().trim());
        forwordData.put("PsrName", getPsrName().getText().toString());
        forwordData.put("CellNumber", getCellNumber().getText().toString());
        forwordData.put("StartPlace", getStartPlace().getText().toString());
        forwordData.put("EndPlace", getEndPlace().getText().toString());
        forwordData.put("LicenseNo", getLicenseNo().getText().toString());
        forwordData.put("LicenseValidity", dateToWeb != null ? dateToWeb : "");
        forwordData.put("TdmAsmName", getTdmAsmName().getText().toString());
        forwordData.put("TdmAsm", getTdmAsm().getText().toString());
        forwordData.put("Distributor", getDistributor().getText().toString());
        try {
            forwordData.put("UserId", repoLogin.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new Date();
        forwordData.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        Log.i(MainActivity.TAG, "forwordData saveDataTemp - " + forwordData.toString());
        return forwordData;
    }

    private void saveVehicleLocally() {
        Log.i(MainActivity.TAG, "dateToWeb : " + dateToWeb);
        RepoRegisterVehicle repoRegisterVehicle = new RepoImplRegisterVehicle(getParent().getDbHelper());
        RepoLogin repoLogin = new RepoImplLogin(getParent().getDbHelper());
        VehicleRegistration vehicleRegistration = new VehicleRegistration();
        vehicleRegistration.setVehicleId(getVehicleNo().getText().toString().trim().replace(" ", "") + "_" + System.currentTimeMillis());
        vehicleRegistration.setVehicleNo(getVehicleNo().getText().toString().trim());
        vehicleRegistration.setPsrName(getPsrName().getText().toString());
        vehicleRegistration.setCellNumber(getCellNumber().getText().toString());
        vehicleRegistration.setStartPlace(getStartPlace().getText().toString());
        vehicleRegistration.setEndPlace(getEndPlace().getText().toString());
        vehicleRegistration.setLicenceNo(getLicenseNo().getText().toString());
        vehicleRegistration.setLicenceValidity(dateToWeb != null ? dateToWeb : "");
        vehicleRegistration.setTdmAsmName(getTdmAsmName().getText().toString());
        vehicleRegistration.setTdmAsm(getTdmAsm().getText().toString());
        vehicleRegistration.setDistributor(getDistributor().getText().toString());
        try {
            vehicleRegistration.setUserId(repoLogin.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new Date();
        vehicleRegistration.setEntryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        Log.i(MainActivity.TAG, "Vehicle Object :" + vehicleRegistration.toString());
        repoRegisterVehicle.registerVehicle(vehicleRegistration);
        // getParent().snack(getView(),getResources().getString(R.string.saveVehicle));
        //getParent().runFragmentTransaction(R.id.frameMainContainer, CheckListFragment.newInstance());
    }

    private final class DateListener implements DatePickerDialog.OnDateSetListener {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month = months[monthOfYear];
            String date = "" + (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth) + "-" + month + "-" + year;
            dateToWeb = year + "-" + (monthOfYear + 1) + "-" + (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth);
            getLicenseValidity().setTextColor(ContextCompat.getColor(getContext(), R.color.date));
            getLicenseValidity().setText(date);
            Prefs.saveLicenseValidity(getContext(), date);
        }

        public String getDateToWeb() {
            return dateToWeb;
        }
    }

    private EditText getVehicleNo() {
        return (EditText) getView().findViewById(R.id.edtVehicleNo);
    }

    private EditText getPsrName() {
        return (EditText) getView().findViewById(R.id.edtPsrName);
    }

    private EditText getCellNumber() {
        return (EditText) getView().findViewById(R.id.edtCellNumber);
    }

    private EditText getStartPlace() {
        return (EditText) getView().findViewById(R.id.edtStartPlace);
    }

    private EditText getEndPlace() {
        return (EditText) getView().findViewById(R.id.edtEndPlace);
    }

    private EditText getLicenseNo() {
        return (EditText) getView().findViewById(R.id.edtLicenseNo);
    }

    private EditText getTdmAsmName() {
        return (EditText) getView().findViewById(R.id.edtTdmAsmName);
    }

    private EditText getTdmAsm() {
        return (EditText) getView().findViewById(R.id.edtTdmAsm);
    }

    private EditText getDistributor() {
        return (EditText) getView().findViewById(R.id.edtDistributor);
    }

    private TextView getLicenseValidity() {
        return (TextView) getView().findViewById(R.id.textLicenseValidity);
    }

    private ImageView getImageViewCalendar(View rootView) {
        return (ImageView) rootView.findViewById(R.id.imageViewLicenseValidity);
    }
}
