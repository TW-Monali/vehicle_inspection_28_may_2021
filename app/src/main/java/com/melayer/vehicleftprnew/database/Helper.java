package com.melayer.vehicleftprnew.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 24/8/16.
 */
public class Helper extends SQLiteOpenHelper
{
    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Database.TableLogin.createTable());
        db.execSQL(Database.TableModule.createTable());
        db.execSQL(Database.TableVehicleRegistration.createTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

    {

    }
}
