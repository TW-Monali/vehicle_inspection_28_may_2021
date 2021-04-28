package com.melayer.vehicleftprnew.temp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.melayer.vehicleftprnew.R;
import com.melayer.vehicleftprnew.activity.MainActivity;
import com.melayer.vehicleftprnew.database.repository.RepoImplLogin;
import com.melayer.vehicleftprnew.database.repository.RepoImplModule;
import com.melayer.vehicleftprnew.database.repository.RepoImplRegisterVehicle;
import com.melayer.vehicleftprnew.database.repository.RepoLogin;
import com.melayer.vehicleftprnew.database.repository.RepoModule;
import com.melayer.vehicleftprnew.database.repository.RepoRegisterVehicle;
import com.melayer.vehicleftprnew.domain.Module;
import com.melayer.vehicleftprnew.domain.VehicleRegistration;
import com.melayer.vehicleftprnew.fragment.GridFragment;
import com.melayer.vehicleftprnew.fragment.RemarksDialogFragment;
import com.melayer.vehicleftprnew.prefs.Prefs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 30/6/16.
 */
public class InitVehicleObject {
    private Context context;
    private Map<String, Module> mapModule = new HashMap<>();

    public InitVehicleObject(Context context) {
        this.context = context;
    }

    private MainActivity getParent()
    {
        return (MainActivity) context;
    }

    public void saveVehicle(VehicleRegistration vehicleRegistration)
    {
        RepoRegisterVehicle repoRegisterVehicle =new RepoImplRegisterVehicle(getParent().getDbHelper());

        repoRegisterVehicle.registerVehicle(vehicleRegistration);
    }
    public void passVehicleObject(String tag, Integer checkPointId) {
        Module module = new Module();
        RepoModule repoModule = new RepoImplModule(context,getParent().getDbHelper());
        RepoRegisterVehicle repoRegisterVehicle = new RepoImplRegisterVehicle(getParent().getDbHelper());
        RepoLogin repoLogin = new RepoImplLogin(getParent().getDbHelper());
        try {
            module.setVehicleId(repoRegisterVehicle.getVehicleId(Prefs.getKeyVehicleNo(context)));
            Log.i(MainActivity.TAG,"vehicleId in passVehicleObject- "+Prefs.getKeyVehicleNo(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        module.setVehicleNo(Prefs.getKeyVehicleNo(context));
        module.setCheckPointId(checkPointId);
        module.setCheckPointName(tag);
        module.setCheckPointStatus("pass");
        Date date = new Date();
        module.setEntryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        module.setRemarks("null");
        module.setImage(null);
        try {
            module.setUserId(Integer.parseInt(repoLogin.getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapModule.put(tag, module);
        Log.i(MainActivity.TAG, tag + ":init pass: " + mapModule.toString());
    }

    public void failVehicleObject(final Spinner spinner, final ArrayAdapter adapter, final String spinnerName, final Integer checkPointId) {
        final FragmentManager fragmentManager = getParent().getSupportFragmentManager();
        RemarksDialogFragment remarksDialog = new RemarksDialogFragment();
        // reasonDialog.setCancelable(false);
        remarksDialog.setOnDismissListener(new RemarksDialogFragment.OnDismissListener() {
            @Override
            public void onDismiss(String inspectionTag, String path, String reason) {
                String tagHelmet = "Helmet Condition";
                if (inspectionTag.equals(tagHelmet)) {
                    if (path.length() > 0) {
                        //checkBox.setChecked(true);
                        int spinnerPosition = adapter.getPosition("No");
                        spinner.setSelection(spinnerPosition);
                        failVehicle(checkPointId,spinner,spinnerName,reason,path);

                    }
                    else

                        {
                        //checkBox.setChecked(false);
                        int spinnerPosition = adapter.getPosition("Select");
                        spinner.setSelection(spinnerPosition);
                        passVehicleObject(""+spinnerName,checkPointId);
                    }
                }

                else
                    {
                    if (reason.length() > 0 && path.length() > 0 && !inspectionTag.equals(tagHelmet)) {
                        //checkBox.setChecked(true);
                        int spinnerPosition = adapter.getPosition("No");
                        spinner.setSelection(spinnerPosition);
                        failVehicle(checkPointId,spinner,spinnerName,reason,path);
                    }

                    else
                        {
                        //checkBox.setChecked(false);
                        int spinnerPosition = adapter.getPosition("Select");
                        spinner.setSelection(spinnerPosition);
                        passVehicleObject(""+spinnerName,checkPointId);
                    }
                }

            }
        });


        remarksDialog.show(fragmentManager, "" + spinnerName);
    }

    private void failVehicle(Integer checkPointId,Spinner spinner,String spinnerName,String reason,String path){
        Module module = new Module();
        RepoRegisterVehicle repoRegisterVehicle = new RepoImplRegisterVehicle(getParent().getDbHelper());
        RepoModule repoModule = new RepoImplModule(context,getParent().getDbHelper());
        RepoLogin repoLogin = new RepoImplLogin(getParent().getDbHelper());
        try {
            Log.i(MainActivity.TAG,"vehicleId in failVehicle- "+Prefs.getKeyVehicleNo(context));
            module.setVehicleId(repoRegisterVehicle.getVehicleId(Prefs.getKeyVehicleNo(context)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        module.setVehicleNo(Prefs.getKeyVehicleNo(context));
        //    module.setUnit(Prefs.getKeyUnitName(context));
        module.setCheckPointId(checkPointId);
        module.setCheckPointName("" + spinner.getTag());
        module.setCheckPointStatus("fail");
        Date date = new Date();
        module.setEntryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        //  Log.i(MainActivity.TAG, "getTag() " + buttonView.getId() + ",Reason: " + reason + ", " + path);
        module.setRemarks(reason.length()<1?null:reason);
        module.setImage(new File(path));
        try

        {

            module.setUserId(Integer.parseInt(repoLogin.getUserId()));
        }

        catch (Exception e)

        {
            e.printStackTrace();
        }
        mapModule.put("" + spinnerName, module);
        Log.i(MainActivity.TAG, spinnerName + ": init Fail : " + mapModule.toString());
    }

    public Boolean saveModuleData(View rootView)

    {
        try

        {
            Log.i(MainActivity.TAG,"mapModule size- "+mapModule.size());
            if (mapModule.size() > 0)

            {
                RepoModule repoModule = new RepoImplModule(context,getParent().getDbHelper());
                // repoModule.deleteTableData(Prefs.getKeyVehicleNo(context));
                repoModule.saveLocalObj(mapModule.values());
                try

                {
                    repoModule.updateFinalStatus(Prefs.getKeyVehicleNo(context));
                }

                catch (Exception e)

                {
                    e.printStackTrace();
                }
                Log.i(MainActivity.TAG, "SQLite Data  save- " + mapModule.values());

            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public void buildAlertMessage(final View rootView, final VehicleRegistration vehicleRegistration) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to save it?")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Prefs.saveFlag(context, true);
                        saveVehicle(vehicleRegistration);
                        Log.i(MainActivity.TAG,"Vehicle Reg : "+vehicleRegistration);
                        saveModuleData(rootView);
                        Prefs.clearKeyLicenseValidity(context);
                        getParent().runFragmentTransaction(R.id.frameMainContainer, GridFragment.newInstance());
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}
