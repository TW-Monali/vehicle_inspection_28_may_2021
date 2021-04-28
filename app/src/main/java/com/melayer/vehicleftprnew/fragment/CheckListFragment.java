package com.melayer.vehicleftprnew.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.melayer.vehicleftprnew.Main2Activity;
import com.melayer.vehicleftprnew.R;
import com.melayer.vehicleftprnew.TWsimpleMailSender;
import com.melayer.vehicleftprnew.activity.MainActivity;
import com.melayer.vehicleftprnew.database.repository.RepoImplRegisterVehicle;
import com.melayer.vehicleftprnew.database.repository.RepoRegisterVehicle;
import com.melayer.vehicleftprnew.domain.VehicleRegistration;
import com.melayer.vehicleftprnew.prefs.Prefs;
import com.melayer.vehicleftprnew.temp.InitVehicleObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static android.app.AlertDialog.*;

/**
 * Created by root on 22/8/16.
 */
public class CheckListFragment extends Fragment {
    public static final String KEY_FRAGMENT_NAME = "key_fragment_name";
    public static final String KEY_BACKWORD_FRAGMENT_DATA = "data";
    AlertDialog.Builder builder;
    String hostpop3, portpop3, userNamepop3, passwordpop3, subpop3;
    String bodyDetails,subjectline;
    android.content.Context context;
    android.content.Context Context;
    private String imagePath;
    private String  email;
    private String fname, lname,no,cname,cimei;

    String name,vehicleId,vehicleNo,userId,unitId;
    DateFormat dateFormat;

    String UnitID, Password, SmtpHost, SmtpPort, ToMailId, IncidentMailLength;
    String myInputsMailID;
    String[] myphoto;

    public static CheckListFragment newInstance(Map<String,String> forwardData) {
        CheckListFragment checkListFragment = new CheckListFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FRAGMENT_NAME, "CheckListFragment");
        args.putSerializable(KEY_BACKWORD_FRAGMENT_DATA, (Serializable) forwardData);
        checkListFragment.setArguments(args);
        return checkListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_checklist, container, false);
        getParent().hideKeyboard(rootView);
        initSpinnerUnit(rootView);
        final InitVehicleObject vehicleObject = new InitVehicleObject(getParent());

        ArrayList<String> units = new ArrayList<>();
        units.add("Select");
        units.add("Yes");
        units.add("No");

        final Spinner spinnerHelmetCondition = (Spinner)
                rootView.findViewById(R.id.spinnerHelmetCondition);

        if(spinnerHelmetCondition.getSelectedItem()!=null && spinnerHelmetCondition.getSelectedItem().toString().equals("Yes")) {
            vehicleObject.passVehicleObject("" + spinnerHelmetCondition.getTag(), 1);
        }


        final ArrayAdapter<String> adapterHelmetCondition = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, units);
        spinnerHelmetCondition.setAdapter(adapterHelmetCondition);

        spinnerHelmetCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerHelmetCondition.getTag() - "+spinnerHelmetCondition.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerHelmetCondition.getTag(), 1);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerHelmetCondition,adapterHelmetCondition,""+spinnerHelmetCondition.getTag(),1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        final Spinner spinnerValidRc = (Spinner)
                rootView.findViewById(R.id.spinnerValidRc);
        vehicleObject.passVehicleObject("" + spinnerValidRc.getTag(), 2);

        ArrayList<String> unitsValidRc = new ArrayList<>();
        unitsValidRc.add("Select");
        unitsValidRc.add("Yes");
        unitsValidRc.add("No");

        final ArrayAdapter<String> adapterValidRc = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsValidRc);
        spinnerValidRc.setAdapter(adapterValidRc);

        spinnerValidRc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerValidRc.getTag(), 2);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerValidRc,adapterValidRc,""+spinnerValidRc.getTag(),2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        final Spinner spinnerValidInsurance = (Spinner)
                rootView.findViewById(R.id.spinnerValidInsurance);
        vehicleObject.passVehicleObject("" + spinnerValidInsurance.getTag(), 3);

        ArrayList<String> unitsValidInsurance = new ArrayList<>();
        unitsValidInsurance.add("Select");
        unitsValidInsurance.add("Yes");
        unitsValidInsurance.add("No");

        final ArrayAdapter<String> adapterValidInsurance = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsValidInsurance);
        spinnerValidInsurance.setAdapter(adapterValidInsurance);

        spinnerValidInsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerValidInsurance.getTag() - "+spinnerValidInsurance.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerValidInsurance.getTag(), 3);
                }
                if(parent.getSelectedItem().equals("No")){
                   vehicleObject.failVehicleObject(spinnerValidInsurance,adapterValidInsurance,""+spinnerValidInsurance.getTag(),3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        final Spinner spinnerPucCertificate = (Spinner)
                rootView.findViewById(R.id.spinnerPucCertificate);
        vehicleObject.passVehicleObject("" + spinnerPucCertificate.getTag(), 4);

        ArrayList<String> unitsPucCertificate = new ArrayList<>();
        unitsPucCertificate.add("Select");
        unitsPucCertificate.add("Yes");
        unitsPucCertificate.add("No");

        final ArrayAdapter<String> adapterPucCertificate = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsPucCertificate);
        spinnerPucCertificate.setAdapter(adapterPucCertificate);

        spinnerPucCertificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerPucCertificate.getTag() - "+spinnerPucCertificate.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerPucCertificate.getTag(), 4);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerPucCertificate,adapterPucCertificate,""+spinnerPucCertificate.getTag(),4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        final Spinner spinnerdrivinglicense = (Spinner)
                rootView.findViewById(R.id.spinnerdrivinglicense);
        vehicleObject.passVehicleObject("" + spinnerdrivinglicense.getTag(), 5);

        ArrayList<String> unitsdrivinglicense = new ArrayList<>();
        unitsdrivinglicense.add("Select");
        unitsdrivinglicense.add("Yes");
        unitsdrivinglicense.add("No");

        final ArrayAdapter<String> adapterdrivinglicense = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsdrivinglicense);
        spinnerdrivinglicense.setAdapter(adapterdrivinglicense);

        spinnerdrivinglicense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i(MainActivity.TAG,"spinnerWheelNuts.getTag() - "+spinnerwheelschockavailable.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerdrivinglicense.getTag(), 5);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerdrivinglicense,adapterdrivinglicense,""+spinnerdrivinglicense.getTag(),5);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        final Spinner spinnerFrontTyres = (Spinner)
                rootView.findViewById(R.id.spinnerFrontTyres);
        vehicleObject.passVehicleObject("" + spinnerFrontTyres.getTag(), 6);

        ArrayList<String> unitsFrontTyres = new ArrayList<>();
        unitsFrontTyres.add("Select");
        unitsFrontTyres.add("Yes");
        unitsFrontTyres.add("No");

        final ArrayAdapter<String> adapterFrontTyres = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsFrontTyres);
        spinnerFrontTyres.setAdapter(adapterFrontTyres);

        spinnerFrontTyres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerFrontTyres.getTag() - "+spinnerFrontTyres.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerFrontTyres.getTag(), 6);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerFrontTyres,adapterFrontTyres,""+spinnerFrontTyres.getTag(),6);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        final Spinner spinnerRearTyres = (Spinner)
                rootView.findViewById(R.id.spinnerRearTyres);
        vehicleObject.passVehicleObject("" + spinnerRearTyres.getTag(), 7);

        ArrayList<String> unitsRearTyres = new ArrayList<>();
        unitsRearTyres.add("Select");
        unitsRearTyres.add("Yes");
        unitsRearTyres.add("No");

        final ArrayAdapter<String> adapterRearTyres = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsRearTyres);
        spinnerRearTyres.setAdapter(adapterRearTyres);

        spinnerRearTyres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerRearTyres.getTag() - "+spinnerRearTyres.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerRearTyres.getTag(), 7);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerRearTyres,adapterRearTyres,""+spinnerRearTyres.getTag(),7);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        final Spinner spinnerSpareWheel = (Spinner)
                rootView.findViewById(R.id.spinnerSpareWheel);
        vehicleObject.passVehicleObject("" + spinnerSpareWheel.getTag(), 8);

        ArrayList<String> unitsSpareWheel = new ArrayList<>();
        unitsSpareWheel.add("Select");
        unitsSpareWheel.add("Yes");
        unitsSpareWheel.add("No");

        final ArrayAdapter<String> adapterSpareWheel = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsSpareWheel);
        spinnerSpareWheel.setAdapter(adapterSpareWheel);

        spinnerSpareWheel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerSpareWheel.getTag() - "+spinnerSpareWheel.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerSpareWheel.getTag(), 8);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerSpareWheel,adapterSpareWheel,""+spinnerSpareWheel.getTag(),8);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        final Spinner spinnerWheelNuts = (Spinner)
                rootView.findViewById(R.id.spinnerWheelNuts);
        vehicleObject.passVehicleObject("" + spinnerWheelNuts.getTag(), 9);

        ArrayList<String> unitsWheelNuts = new ArrayList<>();
        unitsWheelNuts.add("Select");
        unitsWheelNuts.add("Yes");
        unitsWheelNuts.add("No");

        final ArrayAdapter<String> adapterWheelNuts = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsWheelNuts);
        spinnerWheelNuts.setAdapter(adapterWheelNuts);

        spinnerWheelNuts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerWheelNuts.getTag() - "+spinnerWheelNuts.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerWheelNuts.getTag(), 9);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerWheelNuts,adapterWheelNuts,""+spinnerWheelNuts.getTag(),9);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        final Spinner spinnerBreakCondition = (Spinner)
                rootView.findViewById(R.id.spinnerBreakCondition);
        vehicleObject.passVehicleObject("" + spinnerBreakCondition.getTag(), 10);

        ArrayList<String> unitsBreakCondition = new ArrayList<>();
        unitsBreakCondition.add("Select");
        unitsBreakCondition.add("Yes");
        unitsBreakCondition.add("No");

        final ArrayAdapter<String> adapterBreakCondition = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsBreakCondition);
        spinnerBreakCondition.setAdapter(adapterBreakCondition);

        spinnerBreakCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerBreakCondition.getTag() - "+spinnerBreakCondition.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerBreakCondition.getTag(), 10);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerBreakCondition,adapterBreakCondition,""+spinnerBreakCondition.getTag(),10);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        final Spinner spinnerLightCondition = (Spinner)
                rootView.findViewById(R.id.spinnerLightCondition);
        vehicleObject.passVehicleObject("" + spinnerLightCondition.getTag(), 11);

        ArrayList<String> unitsLightCondition = new ArrayList<>();
        unitsLightCondition.add("Select");
        unitsLightCondition.add("Yes");
        unitsLightCondition.add("No");

        final ArrayAdapter<String> adapterLightCondition = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsLightCondition);
        spinnerLightCondition.setAdapter(adapterLightCondition);

        spinnerLightCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerLightCondition.getTag() - "+spinnerLightCondition.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerLightCondition.getTag(), 11);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerLightCondition,adapterLightCondition,""+spinnerLightCondition.getTag(),11);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        final Spinner spinnerRearViewMirrors = (Spinner)
                rootView.findViewById(R.id.spinnerRearViewMirrors);
        vehicleObject.passVehicleObject("" + spinnerRearViewMirrors.getTag(), 12);

        ArrayList<String> unitsRearViewMirrors = new ArrayList<>();
        unitsRearViewMirrors.add("Select");
        unitsRearViewMirrors.add("Yes");
        unitsRearViewMirrors.add("No");

        final ArrayAdapter<String> adapterRearViewMirrors = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsRearViewMirrors);
        spinnerRearViewMirrors.setAdapter(adapterRearViewMirrors);

        spinnerRearViewMirrors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerRearViewMirrors.getTag() - "+spinnerRearViewMirrors.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerRearViewMirrors.getTag(), 12);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerRearViewMirrors,adapterRearViewMirrors,""+spinnerRearViewMirrors.getTag(),12);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        final Spinner spinnerSideIndicators = (Spinner)
                rootView.findViewById(R.id.spinnerSideIndicators);
        vehicleObject.passVehicleObject("" + spinnerSideIndicators.getTag(), 13);

        ArrayList<String> unitsSideIndicators = new ArrayList<>();
        unitsSideIndicators.add("Select");
        unitsSideIndicators.add("Yes");
        unitsSideIndicators.add("No");

        final ArrayAdapter<String> adapterSideIndicators = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsSideIndicators);
        spinnerSideIndicators.setAdapter(adapterSideIndicators);

        spinnerSideIndicators.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerSideIndicators.getTag() - "+spinnerSideIndicators.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerSideIndicators.getTag(), 13);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerSideIndicators,adapterSideIndicators,""+spinnerSideIndicators.getTag(),13);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        final Spinner spinnerHornWorking = (Spinner)
                rootView.findViewById(R.id.spinnerHornWorking);
        vehicleObject.passVehicleObject("" + spinnerHornWorking.getTag(), 14);

        ArrayList<String> unitsHornWorking = new ArrayList<>();
        unitsHornWorking.add("Select");
        unitsHornWorking.add("Yes");
        unitsHornWorking.add("No");

        Log.e("HornWorking", "onCreateView: " + unitsHornWorking.toString());

        final ArrayAdapter<String> adapterHornWorking = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsHornWorking);
        spinnerHornWorking.setAdapter(adapterHornWorking);

        spinnerHornWorking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnerHornWorking.getTag() - "+spinnerHornWorking.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerHornWorking.getTag(), 14);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerHornWorking,adapterHornWorking,""+spinnerHornWorking.getTag(),14);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        final Spinner spinnersteeringwheelplay = (Spinner)
                rootView.findViewById(R.id.spinner_steeringwheelplay);
        vehicleObject.passVehicleObject("" + spinnersteeringwheelplay.getTag(), 15);

        ArrayList<String> unitssteeringwheelplay = new ArrayList<>();
        unitssteeringwheelplay.add("Select");
        unitssteeringwheelplay.add("Yes");
        unitssteeringwheelplay.add("No");

        final ArrayAdapter<String> adaptersteeringwheelplay = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitssteeringwheelplay);
        spinnersteeringwheelplay.setAdapter(adaptersteeringwheelplay);

        spinnersteeringwheelplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnersteeringwheelplay.getTag() - "+spinnersteeringwheelplay.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnersteeringwheelplay.getTag(), 15);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnersteeringwheelplay,adaptersteeringwheelplay,""+spinnersteeringwheelplay.getTag(),15);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



//        final Spinner spinnerreverseHornWorking = (Spinner)
//                rootView.findViewById(R.id.spinner_reverseHornWorking);
//        vehicleObject.passVehicleObject("" + spinnerreverseHornWorking.getTag(), 15);
//
//        ArrayList<String> unitsreverseHornWorking = new ArrayList<>();
//        unitsreverseHornWorking.add("Select");
//        unitsreverseHornWorking.add("Yes");
//        unitsreverseHornWorking.add("No");
//
//        final ArrayAdapter<String> adapterreverseHornWorking = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsreverseHornWorking);
//        spinnerreverseHornWorking.setAdapter(adapterreverseHornWorking);
//
//        spinnerreverseHornWorking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.i(MainActivity.TAG,"spinnerreverseHornWorking.getTag() - "+spinnerreverseHornWorking.getTag());
//                if(parent.getSelectedItem().equals("Yes")){
//                    vehicleObject.passVehicleObject("" + spinnerreverseHornWorking.getTag(), 15);
//                }
//                if(parent.getSelectedItem().equals("No")){
//                    vehicleObject.failVehicleObject(spinnerreverseHornWorking,adapterreverseHornWorking,""+spinnerreverseHornWorking.getTag(),15);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//
//        });




        final Spinner spinnercabindoorworking = (Spinner)
                rootView.findViewById(R.id.spinner_cabindoorworking);
        vehicleObject.passVehicleObject("" + spinnercabindoorworking.getTag(), 16);

        ArrayList<String> unitscabindoorworking = new ArrayList<>();
        unitscabindoorworking.add("Select");
        unitscabindoorworking.add("Yes");
        unitscabindoorworking.add("No");

        final ArrayAdapter<String> adaptercabindoorworking = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitscabindoorworking);
        spinnercabindoorworking.setAdapter(adaptercabindoorworking);

        spinnercabindoorworking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnercabindoorworking.getTag() - "+spinnercabindoorworking.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnercabindoorworking.getTag(), 16);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnercabindoorworking,adaptercabindoorworking,""+spinnercabindoorworking.getTag(),16);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        final Spinner spinnervehiclebodycondition = (Spinner)
                rootView.findViewById(R.id.spinnervehiclebodycondition);
        vehicleObject.passVehicleObject("" + spinnervehiclebodycondition.getTag(), 17);

        ArrayList<String> unitsvehiclebodycondition = new ArrayList<>();
        unitsvehiclebodycondition.add("Select");
        unitsvehiclebodycondition.add("Yes");
        unitsvehiclebodycondition.add("No");

        final ArrayAdapter<String> adaptervehiclebodycondition = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsvehiclebodycondition);
        spinnervehiclebodycondition.setAdapter(adaptervehiclebodycondition);

        spinnervehiclebodycondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(MainActivity.TAG,"spinnervehiclebody.getTag() - "+spinnervehiclebodycondition.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnervehiclebodycondition.getTag(), 17);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnervehiclebodycondition,adaptervehiclebodycondition,""+spinnervehiclebodycondition.getTag(),17);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



//        final Spinner spinnertrailerbreak = (Spinner)
//                rootView.findViewById(R.id.spinnertrailerbreak);
//        vehicleObject.passVehicleObject("" + spinnercabindoorworking.getTag(), 18);
//
//        ArrayList<String> unitstrailerbreak = new ArrayList<>();
//        unitstrailerbreak.add("Select");
//        unitstrailerbreak.add("Yes");
//        unitstrailerbreak.add("No");
//
//        final ArrayAdapter<String> adaptertrailerbreak = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitstrailerbreak);
//        spinnertrailerbreak.setAdapter(adaptertrailerbreak);
//
//        spinnertrailerbreak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//               // Log.i(MainActivity.TAG,"spinnercabindoorworking.getTag() - "+spinnercabindoorworking.getTag());
//                if(parent.getSelectedItem().equals("Yes")){
//                    vehicleObject.passVehicleObject("" + spinnertrailerbreak.getTag(), 18);
//                }
//                if(parent.getSelectedItem().equals("No")){
//                    vehicleObject.failVehicleObject(spinnertrailerbreak,adaptertrailerbreak,""+spinnertrailerbreak.getTag(),18);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//
//        });


        final Spinner spinnerwipersbladeworking = (Spinner)
                rootView.findViewById(R.id.spinnerwipersbladeworking);
        vehicleObject.passVehicleObject("" + spinnervehiclebodycondition.getTag(), 18);

        ArrayList<String> unitswipersbladeworking = new ArrayList<>();
        unitswipersbladeworking.add("Select");
        unitswipersbladeworking.add("Yes");
        unitswipersbladeworking.add("No");

        final ArrayAdapter<String> adapterwipersbladeworking = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitswipersbladeworking);
        spinnerwipersbladeworking.setAdapter(adapterwipersbladeworking);

        spinnerwipersbladeworking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Log.i(MainActivity.TAG,"spinnervehiclebody.getTag() - "+spinnervehiclebodycondition.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerwipersbladeworking.getTag(), 18);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerwipersbladeworking,adapterwipersbladeworking,""+spinnerwipersbladeworking.getTag(),18);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });




        final Spinner spinnerselfstarterBTNrunning = (Spinner)
                rootView.findViewById(R.id.spinnerselfstarterBTNrunning);
        vehicleObject.passVehicleObject("" + spinnervehiclebodycondition.getTag(), 19);

        ArrayList<String> unitsselfstarterBTNrunning = new ArrayList<>();
        unitsselfstarterBTNrunning.add("Select");
        unitsselfstarterBTNrunning.add("Yes");
        unitsselfstarterBTNrunning.add("No");

        final ArrayAdapter<String> adapterselfstarterBTNrunning = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsselfstarterBTNrunning);
        spinnerselfstarterBTNrunning.setAdapter(adapterselfstarterBTNrunning);

        spinnerselfstarterBTNrunning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i(MainActivity.TAG,"spinnervehiclebody.getTag() - "+spinnervehiclebodycondition.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerselfstarterBTNrunning.getTag(), 19);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerselfstarterBTNrunning,adapterselfstarterBTNrunning,""+spinnerselfstarterBTNrunning.getTag(),19);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



//        final Spinner spinnerheadlightcondition = (Spinner)
//                rootView.findViewById(R.id.spinnnerheadlightcondition);
//        vehicleObject.passVehicleObject("" + spinnerheadlightcondition.getTag(), 21);
//
//        ArrayList<String> unitsheadlightcondition = new ArrayList<>();
//        unitsheadlightcondition.add("Select");
//        unitsheadlightcondition.add("Yes");
//        unitsheadlightcondition.add("No");
//
//        final ArrayAdapter<String> adapterheadlightcondition = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsheadlightcondition);
//        spinnerheadlightcondition.setAdapter(adapterheadlightcondition);
//
//        spinnerheadlightcondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Log.i(MainActivity.TAG,"spinnervehiclebody.getTag() - "+spinnervehiclebodycondition.getTag());
//                if(parent.getSelectedItem().equals("Yes")){
//                    vehicleObject.passVehicleObject("" + spinnerheadlightcondition.getTag(), 21);
//                }
//                if(parent.getSelectedItem().equals("No")){
//                    vehicleObject.failVehicleObject(spinnerheadlightcondition,adapterheadlightcondition,""+spinnerheadlightcondition.getTag(),21);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//
//        });



        final Spinner spinnersideindicators = (Spinner)
                rootView.findViewById(R.id.spinnersideindicators);
        vehicleObject.passVehicleObject("" + spinnersideindicators.getTag(), 20);

        ArrayList<String> unitssideindicators = new ArrayList<>();
        unitssideindicators.add("Select");
        unitssideindicators.add("Yes");
        unitssideindicators.add("No");

        final ArrayAdapter<String> adaptersideindicators = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitssideindicators);
        spinnersideindicators.setAdapter(adaptersideindicators );

        spinnersideindicators.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i(MainActivity.TAG,"spinnervehiclebody.getTag() - "+spinnervehiclebodycondition.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnersideindicators.getTag(), 20);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnersideindicators,adaptersideindicators ,""+spinnersideindicators.getTag(),20);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


//        final Spinner spinnerparkinglight = (Spinner)
//                rootView.findViewById(R.id.spinnerparkinglight);
//        vehicleObject.passVehicleObject("" + spinnerparkinglight.getTag(), 23);
//
//        ArrayList<String> unitsparkinglight = new ArrayList<>();
//        unitsparkinglight.add("Select");
//        unitsparkinglight.add("Yes");
//        unitsparkinglight.add("No");
//
//        final ArrayAdapter<String> adapterparkinglight = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsparkinglight);
//        spinnerparkinglight.setAdapter(adapterparkinglight);
//
//        spinnerparkinglight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//             //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
//                if(parent.getSelectedItem().equals("Yes")){
//                    vehicleObject.passVehicleObject("" + spinnerparkinglight.getTag(), 23);
//                }
//                if(parent.getSelectedItem().equals("No")){
//                    vehicleObject.failVehicleObject(spinnerparkinglight,adapterparkinglight,""+spinnerparkinglight.getTag(),23);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//
//        });




        final Spinner spinnernumberplateview = (Spinner)
                rootView.findViewById(R.id.spinnernumberplateview);
        vehicleObject.passVehicleObject("" + spinnernumberplateview.getTag(), 21);

        ArrayList<String> unitsnumberplateview = new ArrayList<>();
        unitsnumberplateview.add("Select");
        unitsnumberplateview.add("Yes");
        unitsnumberplateview.add("No");

        final ArrayAdapter<String> adapternumberplateview = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsnumberplateview);
        spinnernumberplateview.setAdapter(adapternumberplateview);

        spinnernumberplateview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnernumberplateview.getTag(), 21);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnernumberplateview,adapternumberplateview,""+spinnernumberplateview.getTag(),21);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        final Spinner spinnerfootrestcondition = (Spinner)
                rootView.findViewById(R.id.spinnerfootrestcondition);
        vehicleObject.passVehicleObject("" + spinnerfootrestcondition.getTag(), 22);

        ArrayList<String> unitsfootrestcondition = new ArrayList<>();
        unitsfootrestcondition.add("Select");
        unitsfootrestcondition.add("Yes");
        unitsfootrestcondition.add("No");

        final ArrayAdapter<String> adapterfootrestcondition = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsfootrestcondition);
        spinnerfootrestcondition.setAdapter(adapterfootrestcondition);

        spinnerfootrestcondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerfootrestcondition.getTag(), 22);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerfootrestcondition,adapterfootrestcondition,""+spinnerfootrestcondition.getTag(),22);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        final Spinner spinnerRupg = (Spinner)
                rootView.findViewById(R.id.spinnerrupg);
        vehicleObject.passVehicleObject("" + spinnerRupg.getTag(), 23);

        ArrayList<String> unitsRupg = new ArrayList<>();
        unitsRupg.add("Select");
        unitsRupg.add("Yes");
        unitsRupg.add("No");

        final ArrayAdapter<String> adapterRupg = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsRupg);
        spinnerRupg.setAdapter(adapterRupg);

        spinnerRupg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerRupg.getTag(), 23);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerRupg,adapterRupg,""+spinnerRupg.getTag(),23);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        final Spinner spinnerSupg = (Spinner)
                rootView.findViewById(R.id.spinnersupg);
        vehicleObject.passVehicleObject("" + spinnerSupg.getTag(), 24);

        ArrayList<String> unitsSupg = new ArrayList<>();
        unitsSupg.add("Select");
        unitsSupg.add("Yes");
        unitsSupg.add("No");

        final ArrayAdapter<String> adapterSupg = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsSupg);
        spinnerSupg.setAdapter(adapterSupg);

        spinnerSupg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerSupg.getTag(), 24);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerSupg,adapterSupg,""+spinnerSupg.getTag(),24);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        final Spinner spinnercleanerpresent = (Spinner)
                rootView.findViewById(R.id.spinnercleanerpresent);
        vehicleObject.passVehicleObject("" + spinnercleanerpresent.getTag(), 25);

        ArrayList<String> unitscleanerpresent = new ArrayList<>();
        unitscleanerpresent.add("Select");
        unitscleanerpresent.add("Yes");
        unitscleanerpresent.add("No");

        final ArrayAdapter<String> adaptercleanerpresent = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitscleanerpresent);
        spinnercleanerpresent.setAdapter(adaptercleanerpresent);

        spinnercleanerpresent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnercleanerpresent.getTag(), 25);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnercleanerpresent,adaptercleanerpresent,""+spinnercleanerpresent.getTag(),25);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        final Spinner spinnerfailsafebrakecondition = (Spinner)
                rootView.findViewById(R.id.spinnerfailsafebrakecondition);
        vehicleObject.passVehicleObject("" + spinnerfailsafebrakecondition.getTag(), 26);

        ArrayList<String> unitsfailsafebrakecondition = new ArrayList<>();
        unitsfailsafebrakecondition.add("Select");
        unitsfailsafebrakecondition.add("Yes");
        unitsfailsafebrakecondition.add("No");

        final ArrayAdapter<String> adapterfailsafebrakecondition = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsfailsafebrakecondition);
        spinnerfailsafebrakecondition.setAdapter(adapterfailsafebrakecondition);

        spinnerfailsafebrakecondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerfailsafebrakecondition.getTag(), 26);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerfailsafebrakecondition,adapterfailsafebrakecondition,""+spinnerfailsafebrakecondition.getTag(),26);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


//        final Spinner spinnerthreepartsofmirror = (Spinner)
//                rootView.findViewById(R.id.spinnerthreepartsofmirror);
//        vehicleObject.passVehicleObject("" + spinnerthreepartsofmirror.getTag(), 30);
//
//        ArrayList<String> unitsfailthreepartsofmirror = new ArrayList<>();
//        unitsfailthreepartsofmirror.add("Select");
//        unitsfailthreepartsofmirror.add("Yes");
//        unitsfailthreepartsofmirror.add("No");
//
//        final ArrayAdapter<String> adapterthreepartsofmirror = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsfailthreepartsofmirror);
//        spinnerthreepartsofmirror.setAdapter(adapterthreepartsofmirror);
//
//        spinnerthreepartsofmirror.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
//                if(parent.getSelectedItem().equals("Yes")){
//                    vehicleObject.passVehicleObject("" + spinnerthreepartsofmirror.getTag(), 30);
//                }
//                if(parent.getSelectedItem().equals("No")){
//                    vehicleObject.failVehicleObject(spinnerthreepartsofmirror,adapterthreepartsofmirror,""+spinnerthreepartsofmirror.getTag(),30);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//
//        });
        final Spinner spinnerfrontglasscondition = (Spinner)
                rootView.findViewById(R.id.spinnerfrontglasscondition);
        vehicleObject.passVehicleObject("" + spinnerfrontglasscondition.getTag(), 27);

        ArrayList<String> unitsfrontglasscondition = new ArrayList<>();
        unitsfrontglasscondition.add("Select");
        unitsfrontglasscondition.add("Yes");
        unitsfrontglasscondition.add("No");

        final ArrayAdapter<String> adapterfrontglasscondition = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsfrontglasscondition);
        spinnerfrontglasscondition.setAdapter(adapterfrontglasscondition);

        spinnerfrontglasscondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i(MainActivity.TAG,"spinnerWheelNuts.getTag() - "+spinnerwheelschockavailable.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerfrontglasscondition.getTag(), 27);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerfrontglasscondition,adapterfrontglasscondition,""+spinnerfrontglasscondition.getTag(),27);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

//        final Spinner spinnercheckbreatheanalyser = (Spinner)
//                rootView.findViewById(R.id.spinnercheckbreatheanalyser);
//        vehicleObject.passVehicleObject("" + spinnercheckbreatheanalyser.getTag(), 28);
//
//        ArrayList<String> unitscheckbreatheanalyser = new ArrayList<>();
//        unitscheckbreatheanalyser.add("Select");
//        unitscheckbreatheanalyser.add("Yes");
//        unitscheckbreatheanalyser.add("No");
//
//        final ArrayAdapter<String> adaptercheckbreatheanalyser = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitscheckbreatheanalyser);
//        spinnercheckbreatheanalyser.setAdapter(adaptercheckbreatheanalyser);
//
//        spinnercheckbreatheanalyser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Log.i(MainActivity.TAG,"spinnerWheelNuts.getTag() - "+spinnerwheelschockavailable.getTag());
//                if(parent.getSelectedItem().equals("Yes")){
//                    vehicleObject.passVehicleObject("" + spinnercheckbreatheanalyser.getTag(), 28);
//                }
//                if(parent.getSelectedItem().equals("No")){
//                    vehicleObject.failVehicleObject(spinnercheckbreatheanalyser,adaptercheckbreatheanalyser,""+spinnercheckbreatheanalyser.getTag(),28);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//
//        });


        final Spinner spinnercheckbreatheanalyser = (Spinner)
                rootView.findViewById(R.id.spinnercheckbreatheanalyser);
        vehicleObject.passVehicleObject("" + spinnercheckbreatheanalyser.getTag(), 28);

        ArrayList<String> unitscheckbreatheanalyser = new ArrayList<>();
        unitscheckbreatheanalyser.add("Select");
        unitscheckbreatheanalyser.add("Yes");
        unitscheckbreatheanalyser.add("No");

        final ArrayAdapter<String> adaptercheckbreatheanalyser = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitscheckbreatheanalyser);
        spinnercheckbreatheanalyser.setAdapter(adaptercheckbreatheanalyser);

        spinnercheckbreatheanalyser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnercheckbreatheanalyser.getTag(), 28);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnercheckbreatheanalyser,adaptercheckbreatheanalyser,""+spinnercheckbreatheanalyser.getTag(),28);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        final Spinner spinnersafetyshoesPPE = (Spinner)
                rootView.findViewById(R.id.spinnersafetyshoesPPE);
        vehicleObject.passVehicleObject("" + spinnersafetyshoesPPE.getTag(), 29);

        ArrayList<String> unitssafetyshoesPPE = new ArrayList<>();
        unitssafetyshoesPPE.add("Select");
        unitssafetyshoesPPE.add("Yes");
        unitssafetyshoesPPE.add("No");

        final ArrayAdapter<String> adaptersafetyshoesPPE = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitssafetyshoesPPE);
        spinnersafetyshoesPPE.setAdapter(adaptersafetyshoesPPE);

        spinnersafetyshoesPPE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.i(MainActivity.TAG,"spinnerValidRc.getTag() - "+spinnerValidRc.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnersafetyshoesPPE.getTag(), 29);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnersafetyshoesPPE,adaptersafetyshoesPPE,""+spinnersafetyshoesPPE.getTag(),29);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        final Spinner spinnerwheelschockavailable = (Spinner)
                rootView.findViewById(R.id.spinnerwheelschockavailable);
        vehicleObject.passVehicleObject("" + spinnerwheelschockavailable.getTag(), 30);

        ArrayList<String> unitswheelschockavailable = new ArrayList<>();
        unitswheelschockavailable.add("Select");
        unitswheelschockavailable.add("Yes");
        unitswheelschockavailable.add("No");

        final ArrayAdapter<String> adapterwheelschockavailable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitswheelschockavailable);
        spinnerwheelschockavailable.setAdapter(adapterwheelschockavailable);

        spinnerwheelschockavailable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Log.i(MainActivity.TAG,"spinnerWheelNuts.getTag() - "+spinnerwheelschockavailable.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerwheelschockavailable.getTag(), 30);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerwheelschockavailable,adapterwheelschockavailable,""+spinnerwheelschockavailable.getTag(),30);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });








        final Spinner spinnertoolKit = (Spinner)
                rootView.findViewById(R.id.spinnertoolKit);
        vehicleObject.passVehicleObject("" + spinnertoolKit.getTag(), 31);

        ArrayList<String> unitstoolKit = new ArrayList<>();
        unitstoolKit.add("Select");
        unitstoolKit.add("Yes");
        unitstoolKit.add("No");

        final ArrayAdapter<String> adaptertoolKit = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitstoolKit);
        spinnertoolKit.setAdapter(adaptertoolKit);

        spinnertoolKit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i(MainActivity.TAG,"spinnerWheelNuts.getTag() - "+spinnerwheelschockavailable.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnertoolKit.getTag(), 31);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnertoolKit,adaptertoolKit,""+spinnertoolKit.getTag(),31);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        final Spinner spinnerfirstaidbox = (Spinner)
                rootView.findViewById(R.id.spinneravailabilityoffirstaidbox);
        vehicleObject.passVehicleObject("" + spinnerfirstaidbox.getTag(), 32);

        ArrayList<String> unitsfirstaidbox = new ArrayList<>();
        unitsfirstaidbox.add("Select");
        unitsfirstaidbox.add("Yes");
        unitsfirstaidbox.add("No");

        final ArrayAdapter<String> adapterfirstaidbox = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, unitsfirstaidbox);
        spinnerfirstaidbox.setAdapter(adapterfirstaidbox);

        spinnerfirstaidbox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i(MainActivity.TAG,"spinnerWheelNuts.getTag() - "+spinnerwheelschockavailable.getTag());
                if(parent.getSelectedItem().equals("Yes")){
                    vehicleObject.passVehicleObject("" + spinnerfirstaidbox.getTag(), 32);
                }
                if(parent.getSelectedItem().equals("No")){
                    vehicleObject.failVehicleObject(spinnerfirstaidbox,adapterfirstaidbox,""+spinnerfirstaidbox.getTag(),32);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });





        ////////////////////////////////////////////////sideindicators

        Log.i(MainActivity.TAG,"vehicleObject total- "+vehicleObject.toString());
        rootView.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParent().popBackStack(1);
            }
        });
        rootView.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerHelmetCondition.getSelectedItem().equals("Select") || spinnerValidRc.getSelectedItem().equals("Select")||
                spinnerValidInsurance.getSelectedItem().equals("Select") || spinnerPucCertificate.getSelectedItem().equals("Select")||
                spinnerFrontTyres.getSelectedItem().equals("Select")|| spinnerRearTyres.getSelectedItem().equals("Select")||
                spinnerSpareWheel.getSelectedItem().equals("Select")|| spinnerWheelNuts.getSelectedItem().equals("Select")||
                spinnerBreakCondition.getSelectedItem().equals("Select")|| spinnerLightCondition.getSelectedItem().equals("Select")||
                spinnerRearViewMirrors.getSelectedItem().equals("Select") || spinnerSideIndicators.getSelectedItem().equals("Select")||
                spinnerHornWorking.getSelectedItem().equals("Select") || spinnersteeringwheelplay.getSelectedItem().equals("Select")||
                spinnercabindoorworking.getSelectedItem().equals("Select") || spinnervehiclebodycondition.getSelectedItem().equals("Select")||
                        spinnerwipersbladeworking.getSelectedItem().equals("Select") || spinnerselfstarterBTNrunning.getSelectedItem().equals("Select")||
                        spinnersideindicators.getSelectedItem().equals("Select") || spinnernumberplateview.getSelectedItem().equals("Select")||
                        spinnerfootrestcondition.getSelectedItem().equals("Select") || spinnerRupg.getSelectedItem().equals("Select")||
                        spinnerSupg.getSelectedItem().equals("Select") || spinnercleanerpresent.getSelectedItem().equals("Select")||
                        spinnerfailsafebrakecondition.getSelectedItem().equals("Select") || spinnerfrontglasscondition.getSelectedItem().equals("Select")||
                        spinnercheckbreatheanalyser.getSelectedItem().equals("Select") || spinnersafetyshoesPPE.getSelectedItem().equals("Select")||
                        spinnerwheelschockavailable.getSelectedItem().equals("Select") || spinnertoolKit.getSelectedItem().equals("Select")||
                        spinnerfirstaidbox.getSelectedItem().equals("Select") || spinnerdrivinglicense.getSelectedItem().equals("Select"))


                {
                    getParent().snack(rootView,"Please Complete All the Checkpoints!!");
                }else {

                    vehicleObject.buildAlertMessage(rootView,saveVehicleLocally());

                }

               // new MailAction().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, hostpop3, portpop3, userNamepop3, passwordpop3, subpop3);
                        //new MailAction().execute(hostpop3, portpop3, userNamepop3, passwordpop3, subpop3);
                       // Log.e("SUbmit ", "Called1");

                String SENDMAIL= " image:"+ imagePath;
                //new message1(SENDMAIL).execute();

                Log.e("SUbmit ", "Called12");

//                String SENDMAIL= "NAME:"+ fname+"\n LAST NAME:"+ lname+"\n Email:"+ email+"\n Phone NO:"+ no+"\n IMEI No:"+Details+"\n Company Name:"+cname+" \n App Name:"+appname2;
//                new Main2Activity.message(SENDMAIL).execute();


                builder = new Builder(getContext());
                Log.i("DD","alert1 ");
                builder.setIcon(R.drawable.ic_baseline_check_box_24)

                        //Setting message manually and performing action on button click
                        //builder.setMessage("Do you want to close this application ?")

                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                //getActivity().finish();
                                Toast.makeText(getContext(),"Submit Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
//Creating dialog box
                AlertDialog alert = builder.create();
//Setting the title manually
                alert.setTitle("Submit Successfully");
                alert.show();


            }
        });


        return rootView;
    }
    private VehicleRegistration saveVehicleLocally() {
        Log.i(MainActivity.TAG,"KEY_BACKWORD_FRAGMENT_DATA - "+getArguments().get(KEY_BACKWORD_FRAGMENT_DATA));
        Map<String,String> collectData = (Map<String, String>) getArguments().getSerializable(KEY_BACKWORD_FRAGMENT_DATA);
        Log.i(MainActivity.TAG,"collectData - "+collectData.toString());

        RepoRegisterVehicle repoRegisterVehicle = new RepoImplRegisterVehicle(getParent().getDbHelper());
        VehicleRegistration vehicleRegistration = new VehicleRegistration();
        try {
            vehicleRegistration.setVehicleId(collectData.get("vehicleId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        vehicleRegistration.setVehicleNo(collectData.get("vehicleNo"));
        vehicleRegistration.setPsrName(collectData.get("PsrName"));
        vehicleRegistration.setCellNumber(collectData.get("CellNumber"));
        vehicleRegistration.setStartPlace(collectData.get("StartPlace"));
        vehicleRegistration.setEndPlace(collectData.get("EndPlace"));
        vehicleRegistration.setLicenceNo(collectData.get("LicenseNo"));
        vehicleRegistration.setLicenceValidity(collectData.get("LicenseValidity"));
        vehicleRegistration.setTdmAsmName(collectData.get("TdmAsmName"));
        vehicleRegistration.setTdmAsm(collectData.get("TdmAsm"));
        vehicleRegistration.setDistributor(collectData.get("Distributor"));
        try {
            vehicleRegistration.setUserId(collectData.get("UserId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        vehicleRegistration.setEntryDate(collectData.get("date"));
        Log.i(MainActivity.TAG,"Vehicle Object :" +vehicleRegistration.toString());
        //repoRegisterVehicle.registerVehicle(vehicleRegistration);
    return  vehicleRegistration;
    }


    private MainActivity getParent() {
        return (MainActivity) getActivity();
    }

    private void initSpinnerUnit(final View rootView) {
        Spinner spinnerUnit = (Spinner) rootView.findViewById(R.id.spinnerUnit);
        ArrayList<String> units = new ArrayList<>();
       // units.add("North");
       // units.add("East");
       // units.add("GMRC (Gujarat,MP,Rajasthan,Chhattisgarh)");
       // units.add("M&G (Maharashtra & Goa)");
        //units.add("APTK (AP,Telangana,Karnataka)");
      //  units.add("TNK (Tamilnadu, Kerala)");
       // units.add("Agro North");
       // units.add("Agro West");
       // units.add("Agro East");
        //units.add("Nutrition");
        units.add("Taloja");
        units.add("Shikrapur plant");
        Collections.sort(units);
        ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, units);
        spinnerUnit.setAdapter(adapterUnit);
        Prefs.saveUnitName(getContext(), ((Spinner) rootView.findViewById(R.id.spinnerUnit)).getSelectedItem().toString());
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i(MainActivity.TAG,"Selected Unit : "+parent.getSelectedItem().toString());
                Prefs.saveUnitName(getContext(), parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    }
