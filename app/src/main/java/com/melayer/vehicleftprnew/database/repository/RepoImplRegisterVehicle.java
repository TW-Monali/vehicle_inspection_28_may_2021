package com.melayer.vehicleftprnew.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.melayer.vehicleftprnew.activity.MainActivity;
import com.melayer.vehicleftprnew.database.Database;
import com.melayer.vehicleftprnew.database.Helper;
import com.melayer.vehicleftprnew.domain.VehicleRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 25/8/16.
 */
public class RepoImplRegisterVehicle implements RepoRegisterVehicle {
    private Helper helper;

    public RepoImplRegisterVehicle(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void registerVehicle(VehicleRegistration registration) {
        SQLiteDatabase sqDb = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Database.TableVehicleRegistration.COL_VEHICLE_ID, registration.getVehicleId());
        values.put(Database.TableVehicleRegistration.COL_VEHICLE_NO, registration.getVehicleNo());
        values.put(Database.TableVehicleRegistration.COL_PSR_NAME, registration.getPsrName());
        values.put(Database.TableVehicleRegistration.COL_CELL_NUMBER, registration.getCellNumber());
        values.put(Database.TableVehicleRegistration.COL_START_PLACE, registration.getStartPlace());
        values.put(Database.TableVehicleRegistration.COL_END_PLACE, registration.getEndPlace());
        values.put(Database.TableVehicleRegistration.COL_LICENCE_NO, registration.getLicenceNo());
        values.put(Database.TableVehicleRegistration.COL_LICENCE_VALIDITY, registration.getLicenceValidity().toString());
        values.put(Database.TableVehicleRegistration.COL_TDM_ASM_NAME, registration.getTdmAsmName());
        values.put(Database.TableVehicleRegistration.COL_TDM_ASM, registration.getTdmAsm());
        values.put(Database.TableVehicleRegistration.COL_ENTRY_DATE, registration.getEntryDate());
        values.put(Database.TableVehicleRegistration.COL_DISTRIBUTOR, registration.getDistributor());

        values.put(Database.TableVehicleRegistration.COL_FLAG, 1);//data store locally
        sqDb.insert(Database.TableVehicleRegistration.TABLE_NAME, null, values);
        Log.i(MainActivity.TAG, "Vehicle Registration : " + values);
        Log.e("data inserted","inserted data succesfully"+values);
        sqDb.close();
    }

    @Override
    public List<VehicleRegistration> selectAll() throws Exception {
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        List<VehicleRegistration> registrationList = new ArrayList<>();
        RepoLogin repoLogin = new RepoImplLogin(helper);

        String sql = "select * from " + Database.TableVehicleRegistration.TABLE_NAME;// + " where " +
               // Database.TableVehicleRegistration.COL_FLAG + " = 1";
        Cursor cursor = sqDb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                VehicleRegistration vehicleRegistration = new VehicleRegistration();
                vehicleRegistration.setVehicleId(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_VEHICLE_ID)));
                vehicleRegistration.setVehicleNo(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_VEHICLE_NO)));
                vehicleRegistration.setPsrName(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_PSR_NAME)));
                vehicleRegistration.setCellNumber(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_CELL_NUMBER)));
                vehicleRegistration.setStartPlace(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_START_PLACE)));
                vehicleRegistration.setEndPlace(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_END_PLACE)));
                vehicleRegistration.setLicenceNo(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_LICENCE_NO)));
                vehicleRegistration.setLicenceValidity(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_LICENCE_VALIDITY)));
                vehicleRegistration.setTdmAsmName(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_TDM_ASM_NAME)));
                vehicleRegistration.setTdmAsm(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_TDM_ASM)));
                vehicleRegistration.setDistributor(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_DISTRIBUTOR)));
                vehicleRegistration.setEntryDate(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_ENTRY_DATE)));
                vehicleRegistration.setUserId(repoLogin.getUserId());
                registrationList.add(vehicleRegistration);

        }
        return registrationList;
    }

    @Override
    public void updateFlag(String vehicleNo) throws Exception {
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        String sql = "UPDATE " + Database.TableVehicleRegistration.TABLE_NAME +
                " SET " + Database.TableVehicleRegistration.COL_FLAG + " = 0  WHERE " +
                Database.TableVehicleRegistration.COL_VEHICLE_NO + " = " + "'" + vehicleNo.trim() + "'";
        Log.i("@Transworld", "Update flag " + sql);
        sqDb.execSQL(sql);
        sqDb.close();
    }

    @Override
    public void deleteTableData() throws Exception {
        SQLiteDatabase sqDb = helper.getWritableDatabase();
        sqDb.execSQL("delete from " + Database.TableVehicleRegistration.TABLE_NAME + " where " + Database.TableVehicleRegistration.COL_FLAG + " = 0");
        Log.i("@Transworld", "Deleted Record Successfully");
    }

    @Override
    public String getVehicleId(String vehicleNo) throws Exception {
        String vehicleId = null;
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        String sql = "select " + Database.TableVehicleRegistration.COL_VEHICLE_ID + " from " + Database.TableVehicleRegistration.TABLE_NAME
                + " where " + Database.TableVehicleRegistration.COL_VEHICLE_NO + " = " + "'" + vehicleNo.trim() + "'";

        Cursor cursor = sqDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            vehicleId = cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_VEHICLE_ID));

        }
        return vehicleId;
    }

    @Override
    public Integer checkDataInspections(String vehicleNo) throws Exception {
        return null;
    }


    public List<String> getLocalVehicleList() throws Exception {
        List<String> vehicleList = new ArrayList<>();
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        String sql = "select distinct " + Database.TableVehicleRegistration.COL_VEHICLE_NO + " from " +
                Database.TableModule.TABLE_NAME;// + " where " + Database.TableModule.COL_CHECKPOINT_ID + "=12";
        Log.i(MainActivity.TAG, "sql - " + sql);
        Cursor cursor = sqDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            vehicleList.add(cursor.getString(0));
            Log.i(MainActivity.TAG, "cursor.getString(0) - " + cursor.getString(0));
        }
        //  sqDb.close();
        Log.i(MainActivity.TAG, "Vehicle List " + vehicleList);
        return vehicleList;
    }


    @Override
    public List<VehicleRegistration> uploadData(String userId) throws Exception {
        List<VehicleRegistration> listOfVehicleData = new ArrayList<>();
        List<String> localVehicleList =getLocalVehicleList();
        Log.i(MainActivity.TAG,"localVehicleList : "+localVehicleList);
        if (localVehicleList.size() > 0) {
            for (String vehicle : localVehicleList) {
                listOfVehicleData = uploadVehicleDataToServer(userId);
            }
        }
        Log.i(MainActivity.TAG,"Upload Data " + listOfVehicleData);
        return listOfVehicleData;
    }


    @Override
    public List<VehicleRegistration> uploadVehicleDataToServer(String userId) throws Exception {
        List<VehicleRegistration> registrationList = new ArrayList<>();
     //   RepoLogin repoLogin = new RepoImplLogin(helper);
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        List<String> localVehicleList =getLocalVehicleList();

        Log.i(MainActivity.TAG,"localVehicleList : "+localVehicleList);
        if (localVehicleList.size() > 0) {
            for (String vehicleNo : localVehicleList) {
                String selectQuery = "select "
                        + Database.TableVehicleRegistration.COL_VEHICLE_ID + ","
                        + Database.TableVehicleRegistration.COL_VEHICLE_NO + "," +
                        "" + Database.TableVehicleRegistration.COL_PSR_NAME + "," +
                        "" + Database.TableVehicleRegistration.COL_CELL_NUMBER + "," +
                        "" + Database.TableVehicleRegistration.COL_START_PLACE + "," +
                        "" + Database.TableVehicleRegistration.COL_END_PLACE + "," +
                        "" + Database.TableVehicleRegistration.COL_LICENCE_NO + "," +
                        "" + Database.TableVehicleRegistration.COL_LICENCE_VALIDITY + "," +
                        "" + Database.TableVehicleRegistration.COL_TDM_ASM_NAME + "," +
                        "" + Database.TableVehicleRegistration.COL_TDM_ASM + "," +
                        "" + Database.TableVehicleRegistration.COL_DISTRIBUTOR + "," +
                        "" + Database.TableVehicleRegistration.COL_ENTRY_DATE +
                        " from " + Database.TableVehicleRegistration.TABLE_NAME +
                        " where " + Database.TableVehicleRegistration.COL_VEHICLE_NO + " = " + "'" + vehicleNo + "'";
                //  " where " + Database.TableVehicleRegistration.COL_VEHICLE_NO + " = " + "(select distinct " + Database.TableModule.COL_VEHICLE_NO + " " +
                // "from " + Database.TableModule.TABLE_NAME + " where " + Database.TableModule.COL_CHECKPOINT_ID + "=12)";
                Cursor cursor = sqDb.rawQuery(selectQuery, null);
                Log.i(MainActivity.TAG, "selectQuery - " + selectQuery);
                while (cursor.moveToNext()) {
                    VehicleRegistration vehicleRegistration = new VehicleRegistration();
                    vehicleRegistration.setVehicleId(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_VEHICLE_ID)));
                    vehicleRegistration.setVehicleNo(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_VEHICLE_NO)));
                    vehicleRegistration.setPsrName(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_PSR_NAME)));
                    vehicleRegistration.setCellNumber(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_CELL_NUMBER)));
                    vehicleRegistration.setStartPlace(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_START_PLACE)));
                    vehicleRegistration.setEndPlace(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_END_PLACE)));
                    vehicleRegistration.setLicenceNo(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_LICENCE_NO)));
                    vehicleRegistration.setLicenceValidity(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_LICENCE_VALIDITY)));
                    vehicleRegistration.setTdmAsmName(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_TDM_ASM_NAME)));
                    vehicleRegistration.setTdmAsm(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_TDM_ASM)));
                    vehicleRegistration.setDistributor(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_DISTRIBUTOR)));
                    vehicleRegistration.setEntryDate(cursor.getString(cursor.getColumnIndex(Database.TableVehicleRegistration.COL_ENTRY_DATE)));
                    vehicleRegistration.setUserId(userId);
                    registrationList.add(vehicleRegistration);
                }
            }
        }

            sqDb.close();
        Log.i(MainActivity.TAG, "uploadVehicleDataToServer vehicleRegistration-" + registrationList);
        return registrationList;
    }
}
