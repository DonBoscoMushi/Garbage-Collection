package com.igm.garbage.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper db;

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = DbConfig.DATABASE_NAME;

    //create a sql string to create a table in db
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + DbConfig.TABLE_USERS + "("
            + DbConfig.COLUMN_ID + " INT PRIMARY KEY, "
            + DbConfig.COLUMN_FULLNAME + " TEXT, "
            + DbConfig.COLUMN_EMAIL + " TEXT, "
            + DbConfig.COLUMN_PHONE + " TEXT, "
            + DbConfig.COLUMN_ROLE + " TEXT "
            + ")";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbConfig.TABLE_USERS);

    }

    //insert the datas into the local database
    public boolean insert(String UId, String fullname, String email, String phone, String role){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbConfig.COLUMN_ID, UId);
        contentValues.put(DbConfig.COLUMN_FULLNAME, fullname);
        contentValues.put(DbConfig.COLUMN_EMAIL, email);
        contentValues.put(DbConfig.COLUMN_PHONE, phone);
        contentValues.put(DbConfig.COLUMN_ROLE, role);

        long ins = db.insert(DbConfig.TABLE_USERS, null, contentValues);
        return ins != -1;
    }

    //fetch data from the database

    public String checkRole(){
        String query = "SELECT " + DbConfig.COLUMN_ROLE + " FROM " + DbConfig.TABLE_USERS;
        String role = null;

        Log.d(TAG, "checkRole: inacheck role" );
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){

            role = c.getString(c.getColumnIndexOrThrow(DbConfig.COLUMN_ROLE));
            Log.d(TAG, "checkRole: " + role);

//            User user = new User();
//            user.setRole(role);
//            Log.d(TAG, "checkRole: " + user.getRole());
        }

        c.close();
        db.close();

        return role;
    }
}
