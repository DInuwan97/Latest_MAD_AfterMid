package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EcareInfo.db";

    private static String LoggedUserName;




    public DBHandler(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES_USERS;

        SQL_CREATE_ENTRIES_USERS = "CREATE TABLE " + EcareManager.Users.TABLE_NAME+ " ("
                + EcareManager.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EcareManager.Users.COL_NAME_USERNAME + " TEXT,"
                + EcareManager.Users.COL_NAME_USEREMAIL + " TEXT,"
                + EcareManager.Users.COL_NAME_DESIGNATION + " TEXT,"
                + EcareManager.Users.COL_NAME_PASSWORD + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES_USERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.Users.TABLE_NAME);
        onCreate(db);
    }

    public boolean addUsers(String userName,String userEmail,String password){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        String designation = "Patient";

        values.put(EcareManager.Users.COL_NAME_USERNAME,userName);
        values.put(EcareManager.Users.COL_NAME_USEREMAIL,userEmail);
        values.put(EcareManager.Users.COL_NAME_DESIGNATION,designation);
        values.put(EcareManager.Users.COL_NAME_PASSWORD,password);

        long result = db.insert(EcareManager.Users.TABLE_NAME,null,values);

        if(result == -1)
            return false;
        else
            return true;

    }


    public boolean SignInUser(String userEmail,String password){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = { EcareManager.Users._ID,
                                EcareManager.Users.COL_NAME_USERNAME};

        String [] selectionArgs = {userEmail,password};

        String sortOrder = EcareManager.Users.COL_NAME_USEREMAIL + " DESC";

        Cursor cursor = db.query(
                EcareManager.Users.TABLE_NAME,
                projection,
                EcareManager.Users.COL_NAME_USEREMAIL + " = ?" + " and " + EcareManager.Users.COL_NAME_PASSWORD + " = ?",
                selectionArgs,
                null,
                null,
                null

        );

        while (cursor.moveToNext()) {
         this.LoggedUserName = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_USERNAME));
        //cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_USERNAME));
         }


        int count = cursor.getCount();
        cursor.close();
        db.close();


        if(count > 0){
            return true;
        }else{
            return false;
        }

    }

    public static String getLoggedUserName() {
        return LoggedUserName;
    }
}
