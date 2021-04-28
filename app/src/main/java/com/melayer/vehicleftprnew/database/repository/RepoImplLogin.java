package com.melayer.vehicleftprnew.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.melayer.vehicleftprnew.activity.MainActivity;
import com.melayer.vehicleftprnew.database.Database;
import com.melayer.vehicleftprnew.database.Helper;

/**
 * Created by root on 25/8/16.
 */
public class RepoImplLogin implements RepoLogin {
    private Helper helper;

    public RepoImplLogin(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void saveUserCredentials(String userName, Integer userId) throws Exception {
        SQLiteDatabase sqDb = helper.getWritableDatabase();
        sqDb.execSQL("delete from " + Database.TableLogin.TABLE_NAME);
        sqDb.execSQL("delete from " + Database.TableVehicleRegistration.TABLE_NAME);
        sqDb.execSQL("delete from " + Database.TableModule.TABLE_NAME);
        ContentValues values = new ContentValues();
        values.put(Database.TableLogin.COL_USER_NAME, userName);
        values.put(Database.TableLogin.COL_USER_ID, userId);
        values.put(Database.TableLogin.COL_FLAG, 1); //user details save after login user
        sqDb.insert(Database.TableLogin.TABLE_NAME, null, values);
        Log.i(MainActivity.TAG, "After Login Success" + values);
     //   sqDb.close();
    }

    @Override
    public String getUserName() throws Exception {
        String userName = "";
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        Cursor cursor = sqDb.query(Database.TableLogin.TABLE_NAME, new String[]{Database.TableLogin.COL_USER_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            userName = cursor.getString(cursor.getColumnIndex(Database.TableLogin.COL_USER_NAME));
        }
        sqDb.close();
        return userName;
    }

    @Override
    public String getUserId() throws Exception {
        String userId = "";
        SQLiteDatabase sqDb = helper.getReadableDatabase();
        Cursor cursor = sqDb.query(Database.TableLogin.TABLE_NAME, new String[]{Database.TableLogin.COL_USER_ID}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            userId = cursor.getString(cursor.getColumnIndex(Database.TableLogin.COL_USER_ID));
        }
        sqDb.close();
        return userId;
    }
}
