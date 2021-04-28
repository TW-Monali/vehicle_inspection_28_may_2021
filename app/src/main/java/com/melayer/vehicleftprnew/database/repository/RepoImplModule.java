package com.melayer.vehicleftprnew.database.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.melayer.vehicleftprnew.activity.MainActivity;
import com.melayer.vehicleftprnew.database.Database;
import com.melayer.vehicleftprnew.database.Helper;
import com.melayer.vehicleftprnew.domain.Module;
import com.melayer.vehicleftprnew.prefs.Prefs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 25/8/16.
 */
public class RepoImplModule implements RepoModule {
    private Helper helper;
    private Context context;

    public RepoImplModule(Context context, Helper helper) {
        this.context = context;
        this.helper = helper;
    }

    @Override
    public void saveModule(Module module) throws Exception {
        SQLiteDatabase sqDb = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        RepoRegisterVehicle registerVehicle = new RepoImplRegisterVehicle(helper);

        values.put(Database.TableModule.COL_VEHICLE_ID, registerVehicle.getVehicleId(Prefs.getKeyVehicleNo(context)));
        values.put(Database.TableModule.COL_VEHICLE_NO, module.getVehicleNo());
        values.put(Database.TableModule.COL_UNIT, Prefs.getKeyUnitName(context));
        values.put(Database.TableModule.COL_CHECKPOINT_NAME, module.getCheckPointName());
        values.put(Database.TableModule.COL_REMARKS, module.getRemarks());
        values.put(Database.TableModule.COL_CHECKPOINT_ID, module.getCheckPointId());
        values.put(Database.TableModule.COL_CHECKPOINT_STATUS, module.getCheckPointStatus());
        values.put(Database.TableModule.COL_IMAGE_PATH, "" + module.getImage());
        values.put(Database.TableModule.COL_ENTRY_DATE, module.getEntryDate());
        values.put(Database.TableModule.COL_USER_ID, module.getUserId());
        values.put(Database.TableModule.COL_FLAG, 1);
        sqDb.insert(Database.TableModule.TABLE_NAME, null, values);
        Log.i(MainActivity.TAG, "Module Inserted : " + values);
        sqDb.close();
    }

    @Override
    public Set<Module> selectAll() throws Exception {
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        Set<Module> listOfVehicleData = new HashSet<>();
        String sql = "select * from " + Database.TableModule.TABLE_NAME+ " where " + Database.TableModule.COL_FLAG + " = 1" ;
        Cursor cursor = sqDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Module module = new Module();
            module.setVehicleId(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_VEHICLE_ID)));
            module.setVehicleNo(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_VEHICLE_NO)));
            module.setUnit(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_UNIT)));
            module.setCheckPointId(cursor.getInt(cursor.getColumnIndex(Database.TableModule.COL_CHECKPOINT_ID)));
            module.setCheckPointName(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_CHECKPOINT_NAME)));
            module.setCheckPointStatus(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_CHECKPOINT_STATUS)));
            module.setFinalInspectionStatus(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_FINAL_INSPECTION_STATUS)));
            module.setRemarks(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_REMARKS)));
            module.setImage(new File(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_IMAGE_PATH))));
            module.setEntryDate(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_ENTRY_DATE)));
            listOfVehicleData.add(module);
        }
        return listOfVehicleData;
    }

    @Override
    public void saveLocalObj(Collection<Module> modules) throws Exception {
        for (Module module : modules) {
            saveModule(module);
        }
    }

    @Override
    public void updateFinalStatus(String vehicleNo) throws Exception {
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        Integer count = null;
        String countCheckPoints = "select * from " + Database.TableModule.TABLE_NAME + "" +
                " where " + Database.TableModule.COL_CHECKPOINT_STATUS + " = 'pass' and " + Database.TableModule.COL_VEHICLE_NO + " = " + "'" + vehicleNo + "'";
        Log.i(MainActivity.TAG, "CountCheckPoints Query: " + countCheckPoints);
        Cursor cursor = sqDb.rawQuery(countCheckPoints, null);
        while (cursor.moveToNext()) {
            count = cursor.getCount();
            Log.i(MainActivity.TAG, "cursor.getCount() : " + cursor.getCount());
            Log.i(MainActivity.TAG, "CheckpointName : " + cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_CHECKPOINT_NAME)));
        }
        if (count == 12) {
            String updateFinalStatus = "update " + Database.TableModule.TABLE_NAME + " Set " + Database.TableModule.COL_FINAL_INSPECTION_STATUS + "='pass'" +
                    " where " + Database.TableModule.COL_VEHICLE_NO + " = " + "'" + vehicleNo + "'";
            Log.i(MainActivity.TAG, "updateFinalStatus : " + updateFinalStatus);
            sqDb.execSQL(updateFinalStatus);
        }
        sqDb.close();
    }

    @Override
    public void updateFlag(String vehicleNo, String checkPointName) throws Exception {
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        String sql = "UPDATE " + Database.TableModule.TABLE_NAME +
                " SET " + Database.TableModule.COL_FLAG + " = 0  WHERE " +
                Database.TableModule.COL_CHECKPOINT_NAME + " = '" +checkPointName + "' and "+
                Database.TableModule.COL_VEHICLE_NO + " = " + "'" + vehicleNo.trim() + "'" ;
        Log.i(MainActivity.TAG, "Update flag " + sql);
        sqDb.execSQL(sql);
        sqDb.close();
    }

    @Override
    public void deleteTableData() throws Exception {
        SQLiteDatabase sqDb = helper.getWritableDatabase();
        sqDb.execSQL("delete from " + Database.TableModule.TABLE_NAME + " where " + Database.TableModule.COL_FLAG + " = 0");
        Log.i("@Transworld", "Deleted Record Successfully");
    }

    @Override
    public void deleteTableData(String vehicleNo) throws Exception {
        SQLiteDatabase sqDb = helper.getWritableDatabase();
        sqDb.execSQL("delete from " + Database.TableModule.TABLE_NAME + " where " + Database.TableModule.COL_FLAG + " = 0");
        Log.i("@Transworld", "Deleted Record Successfully");
    }

    @Override
    public Integer checkDataInspections() throws Exception {
        SQLiteDatabase sqDb = helper.getWritableDatabase();
        String sql = "select * from " + Database.TableModule.TABLE_NAME;
        // String sql = "select * from "+ Database.TableModule.TABLE_NAME;
        //"  where "
        //+Database.TableModule.COL_VEHICLE_NO + " = " + "'" + vehicleNo + "'" ;
        Log.i(MainActivity.TAG, "SQL " + sql);
        Cursor cursor = sqDb.rawQuery(sql, null);
        Integer count = cursor.getCount();
        return count;
    }

    @Override
    public List<String> getLocalVehicleList() throws Exception {
        List<String> vehicleList = new ArrayList<>();
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        String sql = "select distinct " + Database.TableModule.COL_VEHICLE_NO + " from " +
                Database.TableModule.TABLE_NAME; //+// " where " + Database.TableModule.COL_CHECKPOINT_ID + "=12";
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
    public List<Module> uploadData(String userId) throws Exception {
        List<Module> listOfVehicleData = new ArrayList<>();
        List<String> localVehicleList = getLocalVehicleList();
        if (localVehicleList.size() > 0) {
            for (String vehicle : localVehicleList) {
                //listOfVehicleData = uploadModuleData(userId);
            }
        }
        return listOfVehicleData;
    }

    @Override
    public Set<Module> uploadModuleData(String userId) throws Exception {
        Set<Module> listOfVehicleData = new HashSet<>();
        SQLiteDatabase sqDb = helper.getWritableDatabase();
        List<String> localVehicleList = getLocalVehicleList();
        if (localVehicleList.size() > 0) {
            for (String vehicleNo : localVehicleList) {
                String selectQuery = "select "
                        + Database.TableModule.COL_VEHICLE_ID + ","
                        + Database.TableModule.COL_VEHICLE_NO + "," +
                        "" + Database.TableModule.COL_UNIT + "," +
                        "" + Database.TableModule.COL_CHECKPOINT_ID + "," +
                        "" + Database.TableModule.COL_CHECKPOINT_NAME + "," +
                        "" + Database.TableModule.COL_CHECKPOINT_STATUS + "," +
                        "" + Database.TableModule.COL_REMARKS + "," +
                        "" + Database.TableModule.COL_FINAL_INSPECTION_STATUS + "," +
                        "" + Database.TableModule.COL_IMAGE_PATH + "," +
                        "" + Database.TableModule.COL_ENTRY_DATE + "," +
                        "" + Database.TableModule.COL_USER_ID +
                        " from " + Database.TableModule.TABLE_NAME +
                        " where " +  Database.TableModule.COL_VEHICLE_NO + " = " + "'" + vehicleNo + "'";
                Cursor cursor = sqDb.rawQuery(selectQuery, null);
                while (cursor.moveToNext()) {
                    Module module = new Module();
                    module.setVehicleId(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_VEHICLE_ID)));
                    module.setVehicleNo(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_VEHICLE_NO)));
                    module.setUnit(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_UNIT)));
                    module.setCheckPointId(cursor.getInt(cursor.getColumnIndex(Database.TableModule.COL_CHECKPOINT_ID)));
                    module.setCheckPointName(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_CHECKPOINT_NAME)));
                    module.setCheckPointStatus(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_CHECKPOINT_STATUS)));
                    module.setFinalInspectionStatus(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_FINAL_INSPECTION_STATUS)));
                    module.setRemarks(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_REMARKS)));
                    module.setImage(new File(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_IMAGE_PATH))));
                    module.setEntryDate(cursor.getString(cursor.getColumnIndex(Database.TableModule.COL_ENTRY_DATE)));
                    module.setUserId(Integer.parseInt(userId));
                    listOfVehicleData.add(module);
                }
            }
        }
        sqDb.close();
        Log.i(MainActivity.TAG, "Local List Ready to Upload -" + listOfVehicleData);
        return listOfVehicleData;
    }
}
