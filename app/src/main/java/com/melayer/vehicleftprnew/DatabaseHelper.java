package com.melayer.vehicleftprnew;

/**
 * Created by twtech on 5/7/17.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by sushant on 11/4/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{

    public static String DB_NAME="LOGDB";
    public static String SMS_TABLE_NAME="SMSLOGTABLE1";
    public static String CALL_TABLE_NAME="CALLLOGTABLE1";

    public static int DB_VERSION=1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {

        super(context,"LOGDB.db", factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CALLTABLE="CREATE TABLE SMSLOGTABLE1(_SMSID INTEGER,SMSTIME varchar(50))";
        String CREATE_SMSTABLE="CREATE TABLE CALLLOGTABLE1(_CALLID INTEGER,CALLTIME varchar(50))";
        db.execSQL(CREATE_CALLTABLE);
        db.execSQL(CREATE_SMSTABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS SMSLOGTABLE1");
        db.execSQL("DROP TABLE IF EXISTS CALLLOGTABLE1");

    }

}

