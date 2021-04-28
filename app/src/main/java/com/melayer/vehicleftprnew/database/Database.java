package com.melayer.vehicleftprnew.database;

import android.util.Log;

import com.melayer.vehicleftprnew.activity.MainActivity;

/**
 * Created by root on 24/8/16.
 */
public class Database
{
    public static final String DB_NAME = "vehicleftp";
    public static final Integer DB_VERSION = 1;

          public static class TableLogin
    {

        public static final String TABLE_NAME = "tab_login";
        public static final String COL_USER_NAME = "user_name";
        public static final String COL_PASSWORD = "password";
        public static final String COL_USER_ID = "userid";
        public static final String COL_FLAG ="flag";


            public static String createTable()

            {

            StringBuilder query = new StringBuilder();
            query.append("CREATE TABLE " + TABLE_NAME + "(");
            query.append(COL_USER_NAME + " " + "text" + ",");
            query.append(COL_PASSWORD + " " + "text" +",");
            query.append(COL_USER_ID + " " + "integer" + ",");
            query.append(COL_FLAG + " " + "integer DEFAULT 1");
            query.append(" )");
            Log.i("@vehicleftp", "Query Create Table - " + query.toString());
            return query.toString();
        }
    }

            public static class TableModule
     {
        public static final String TABLE_NAME = "tab_module";
        public static final String COL_SR_NO = "Srno";
        public static final String COL_VEHICLE_ID = "vehicleId";
        public static final String COL_VEHICLE_NO = "vehicleNo";
        public static final String COL_UNIT = "unit";
        public static final String COL_CHECKPOINT_NAME = "checkPointName";
        public static final String COL_CHECKPOINT_ID = "checkPointId";
        public static final String COL_CHECKPOINT_STATUS = "checkPointStatus";
        public static final String COL_FINAL_INSPECTION_STATUS = "finalInspectionStatus";
        public static final String COL_REMARKS = "remarks";
        public static final String COL_IMAGE_PATH = "imagePath";
        public static final String COL_ENTRY_DATE = "entryDate";
        public static final String COL_USER_ID = "userId";
        public static final String COL_FLAG ="flag";

              public static String createTable()

        {
            StringBuilder query = new StringBuilder();
            query.append("CREATE TABLE " + TABLE_NAME + "(");
            query.append(COL_SR_NO + " " + "integer primary key AUTOINCREMENT,");
            query.append(COL_VEHICLE_ID + " " + "text NOT NULL,");
            query.append(COL_VEHICLE_NO + " " + "text,");
            query.append(COL_UNIT + " " + "text,");
            query.append(COL_CHECKPOINT_NAME + " " + "text" + ",");
            query.append(COL_CHECKPOINT_ID + " " + "integer"+",");
            query.append(COL_CHECKPOINT_STATUS + " " + "text"+",");
            query.append(COL_REMARKS + " " + "text" + ",");
            query.append(COL_IMAGE_PATH + " " + "text" + ",");
            query.append(COL_ENTRY_DATE + " " + "text " + ",");
            query.append(COL_USER_ID + " " + "text" + ",");
            query.append(COL_FINAL_INSPECTION_STATUS + " " + "text DEFAULT fail" + ",");
            query.append(COL_FLAG + " " + "integer DEFAULT 1");
            query.append(")");
            Log.i("@vehicleftp", "Query Create Table CheckPoints - " + query.toString());
            return query.toString();
        }
    }


           public static class TableVehicleRegistration
    {
        public static final String TABLE_NAME = "tab_vehicleregistration";
        public static final String COL_SR_NO = "Srno";
        public static final String COL_VEHICLE_ID = "vehicleId";
        public static final String COL_VEHICLE_NO = "vehicleNo";
        public static final String COL_PSR_NAME = "psrName";
        public static final String COL_CELL_NUMBER = "cellNumber";
        public static final String COL_START_PLACE = "startPlace";
        public static final String COL_END_PLACE = "endPlace";
        public static final String COL_LICENCE_NO = "licenceNo";
        public static final String COL_LICENCE_VALIDITY = "licenceValidity";
        public static final String COL_TDM_ASM_NAME = "tdmAsmName";
        public static final String COL_TDM_ASM = "tdmAsm";
        public static final String COL_DISTRIBUTOR = "distributor";
        public static final String COL_ENTRY_DATE = "entryDate";
        public static final String COL_FLAG ="flag";

            public static String createTable()
        {
            StringBuilder query = new StringBuilder();
            query.append("CREATE TABLE " + TABLE_NAME + "(");
            query.append(COL_SR_NO + " " + "integer primary key AUTOINCREMENT,");
            query.append(COL_VEHICLE_ID + " " + "text NOT NULL,");
            query.append(COL_VEHICLE_NO + " " + "text,");
            query.append(COL_PSR_NAME + " " + "text" + ",");
            query.append(COL_CELL_NUMBER + " " + "text " + ",");
            query.append(COL_START_PLACE + " " + "text" + ",");
            query.append(COL_END_PLACE + " " + "text" + ",");
            query.append(COL_LICENCE_NO + " " + "text" + ",");
            query.append(COL_LICENCE_VALIDITY + " " + "text" + ",");
            query.append(COL_TDM_ASM_NAME + " " + "text" + ",");
            query.append(COL_TDM_ASM + " " + "text" + ",");
            query.append(COL_DISTRIBUTOR + " " + "text" + ",");
            query.append(COL_ENTRY_DATE + " " + "text DEFAULT NULL" + ",");
            query.append(COL_FLAG + " " + "integer DEFAULT 1");
            query.append(")");
            Log.i(MainActivity.TAG, "Query Create Table vehicleregistration - " + query.toString());
            return query.toString();
        }


    }

}
