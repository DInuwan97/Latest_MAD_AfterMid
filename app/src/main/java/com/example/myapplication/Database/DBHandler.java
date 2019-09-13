package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EcareInfo.db";

    private static String LoggedUserName;
    private static String LoggedUserType;




    public DBHandler(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES_USERS;
        String SQL_CREATE_ENTRIES_DOCTORS;

        //create users table
        SQL_CREATE_ENTRIES_USERS = "CREATE TABLE " + EcareManager.Users.TABLE_NAME+ " ("
                + EcareManager.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EcareManager.Users.COL_NAME_USERNAME + " TEXT,"
                + EcareManager.Users.COL_NAME_USEREMAIL + " TEXT,"
                + EcareManager.Users.COL_NAME_DESIGNATION + " TEXT,"
                + EcareManager.Users.COL_NAME_PASSWORD + " TEXT)";

        //create doctors table

        SQL_CREATE_ENTRIES_DOCTORS = "CREATE TABLE " + EcareManager.Doctors.TABLE_NAME+ " ("
                                    + EcareManager.Doctors._ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                                    + EcareManager.Doctors.COL_NAME_DOCTORNAME + "TEXT,"
                                    + EcareManager.Doctors.COL_NAME_DOCTOREMAIL + "TEXT,"
                                    + EcareManager.Doctors.COL_NAME_HOSPITAL + "TEXT,"
                                    + EcareManager.Doctors.COL_NAME_DOCTORMOBILE + "TEXT,"
                                    + EcareManager.Doctors.COL_NAME_SPECIALIZATION + "TEXT,"
                                    + EcareManager.Doctors.COL_NAME_NIC + "TEXT)";


        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_DOCTORS);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.Users.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.Doctors.TABLE_NAME);
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
                                EcareManager.Users.COL_NAME_USERNAME,
                                EcareManager.Users.COL_NAME_DESIGNATION};

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
         this.LoggedUserType = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_DESIGNATION));
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

    public static String getLoggedUserType(){
        return LoggedUserType;
    }

    public boolean addDoctor(String doctorName,String doctorEmail,String hospitalName,String doctorMobile,
                             String doctorSpecilization,String doctorNic){


        //firstly add users table
        SQLiteDatabase dbUsers = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        String designation = "Doctor";

        userValues.put(EcareManager.Users.COL_NAME_USERNAME,doctorName);
        userValues.put(EcareManager.Users.COL_NAME_USEREMAIL,doctorEmail);
        userValues.put(EcareManager.Users.COL_NAME_DESIGNATION,designation);
        userValues.put(EcareManager.Users.COL_NAME_PASSWORD,doctorNic);



        //sencondly add doctors table
        SQLiteDatabase dbDoctors = this.getWritableDatabase();

        ContentValues doctorValues = new ContentValues();

        doctorValues.put(EcareManager.Doctors.COL_NAME_DOCTORNAME,doctorName);
        doctorValues.put(EcareManager.Doctors.COL_NAME_DOCTOREMAIL,doctorEmail);
        doctorValues.put(EcareManager.Doctors.COL_NAME_HOSPITAL,hospitalName);
        doctorValues.put(EcareManager.Doctors.COL_NAME_DOCTORMOBILE,doctorMobile);
        doctorValues.put(EcareManager.Doctors.COL_NAME_SPECIALIZATION,doctorSpecilization);
        doctorValues.put(EcareManager.Doctors.COL_NAME_NIC,doctorNic);


        long resultUser = dbUsers.insert(EcareManager.Users.TABLE_NAME,null,userValues);
        long resultDoctor = dbDoctors.insert(EcareManager.Doctors.TABLE_NAME,null,doctorValues);

        if(resultUser == -1 && resultDoctor == -1)
            return false;
        else
            return true;


    }
}
