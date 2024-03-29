package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import android.icu.util.Freezable;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Models.Appoinments;
import com.example.myapplication.Models.TimeSlots;
import com.example.myapplication.PharmacyAdminAddMedicine;
import com.example.myapplication.PharmacyMedicineCart;
import com.example.myapplication.PharmacyMedicineList;
import com.example.myapplication.pharmacyAdminMedicineList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EcareInfo.db";

    private static String LoggedUserName;
    private static String LoggedUserType;
    private static String LoggedUserID;
    private static String PatientID = "1";


    private static String LoggedUserGender;
    private static String LoggedUserAddress;
    private static String LoggedUserMobile;

    private static String LoggedUserEmail;


    public static String getLoggedUserGender() {
        return LoggedUserGender;
    }

    public static String getLoggedUserAddress() {
        return LoggedUserAddress;
    }

    public static String getLoggedUserMobile() {
        return LoggedUserMobile;
    }

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES_USERS;
        String SQL_CREATE_ENTRIES_DOCTORS;

        //create users table
        SQL_CREATE_ENTRIES_USERS = "CREATE TABLE " + EcareManager.Users.TABLE_NAME + " ("
                + EcareManager.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EcareManager.Users.COL_NAME_USERNAME + " TEXT,"
                + EcareManager.Users.COL_NAME_USEREMAIL + " TEXT,"
                + EcareManager.Users.COL_NAME_DESIGNATION + " TEXT,"
                + EcareManager.Users.COL_NAME_PASSWORD + " TEXT,"
                + EcareManager.Users.COL_NAME_GENDER + " TEXT,"
                + EcareManager.Users.COL_NAME_MOBILE + " TEXT,"
                + EcareManager.Users.COL_NAME_ADDRESS + " TEXT)";

        //create doctors table


        SQL_CREATE_ENTRIES_DOCTORS = "CREATE TABLE " + EcareManager.Doctors.TABLE_NAME + " ("
                + EcareManager.Doctors._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EcareManager.Doctors.COL_NAME_DOCTORNAME + " TEXT,"
                + EcareManager.Doctors.COL_NAME_DOCTOREMAIL + " TEXT,"
                + EcareManager.Doctors.COL_NAME_HOSPITAL + " TEXT,"
                + EcareManager.Doctors.COL_NAME_DOCTORMOBILE + " TEXT,"
                + EcareManager.Doctors.COL_NAME_SPECIALIZATION + " TEXT,"
                + EcareManager.Doctors.COL_NAME_NIC + " TEXT)";

        String SQL_CREATE_ENTRIES_MEDICINE = "CREATE TABLE  " + EcareManager.Medicine.TABLE_NAME + " ( " +
                EcareManager.Medicine._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + " TEXT ," +
                EcareManager.Medicine.COLUMN_NAME_PRICE + " REAL," +
                EcareManager.Medicine.COLUMN_NAME_PRICE_ITEM_TYPE + " TEXT," +
                EcareManager.Medicine.COLUMN_NAME_DESCRIPTION + " TEXT," +
                EcareManager.Medicine.COLUMN_NAME_USAGE + " TEXT," +
                EcareManager.Medicine.COLUMN_NAME_INGREDIENTS + " TEXT," +
                EcareManager.Medicine.COLUMN_NAME_SIDE_EFFECTS + " TEXT," +

                EcareManager.Medicine.COLUMN_NAME_IMAGE + " BLOB)";

        String SQL_CREATE_ENTRIES_CART_PHARMACY = "CREATE TABLE " + EcareManager.PharmacyCart.TABLE_NAME + "(" +
                EcareManager.PharmacyCart._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME + " TEXT," +
                EcareManager.PharmacyCart.COLUMN_NAME_USERNAME + " TEXT," +
                EcareManager.PharmacyCart.COLUMN_NAME_AMOUNT + " REAL," +
                EcareManager.PharmacyCart.COLUMN_NAME_PRICE_FOR_ONE_ITEM + " REAL," +
                EcareManager.PharmacyCart.COLUMN_NAME_PRICE_TYPE + " TEXT)";

        String SQL_CREATE_ENTRIES_DELIVERY = "CREATE TABLE " + EcareManager.Deliver.TABLE_NAME + " ( " +
                EcareManager.Deliver._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EcareManager.Deliver.COLUMN_NAME_USER_NAME + " TEXT, " +
                EcareManager.Deliver.COLUMN_NAME_EMAIL + " TEXT, " +
                EcareManager.Deliver.COLUMN_NAME_ADDRESS + " TEXT, " +
                EcareManager.Deliver.COLUMN_NAME_PHONE_NUMBER + " INTEGER , " +
                EcareManager.Deliver.COLUMN_NAME_STATUS + " INTEGER , " +
                EcareManager.Deliver.COLUMN_NAME_MEDICINE_ITEMS_NAMES + " TEXT, " +
                EcareManager.Deliver.COLUMN_NAME_MEDICINE_ITEMS_AMOUNT + " TEXT, " +
                EcareManager.Deliver.COLUMN_NAME_DATETIME + " TEXT , " +
                EcareManager.Deliver.COLUMN_NAME_PRICE_TOTAL + " REAL)";



        String SQL_CREATE_ENTRIES_TIMESLOTS = "CREATE TABLE " + EcareManager.TimeSlots.TABLE_NAME+ " ("+
                EcareManager.TimeSlots._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                EcareManager.TimeSlots.COL_DOCTOR_EMAIL+ " TEXT, "+
                EcareManager.TimeSlots.COL_SLOT_DAY+ " TEXT, "+
                EcareManager.TimeSlots.COL_SLOT_START_TIME+ " TEXT, "+
                EcareManager.TimeSlots.COL_SLOT_END_TIME + " TEXT)";



        String SQL_CREATE_ENTRIES_TESTS = "CREATE TABLE "+ EcareManager.Tests.TABLE_NAME +" ("+
                EcareManager.Tests._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                EcareManager.Tests.COLUMN_NAME_PATIENT_ID + " INTEGER,"+
                EcareManager.Tests.COLUMN_NAME_TESTER_ID + " INTEGER,"+
                EcareManager.Tests.COLUMN_NAME_TEST_NAME + " TEXT,"+
                EcareManager.Tests.COLUMN_NAME_TEST_DESCRIPTION + " TEXT,"+
                EcareManager.Tests.COLUMN_NAME_TEST_PRICE + " REAL,"+
                EcareManager.Tests.COLUMN_NAME_TEST_DATE + " TEXT)";



        String SQL_CREATE_ENTRIES_PRESCRIPTIONS = "CREATE TABLE "+ EcareManager.Prescription.TABLE_NAME +" ("+
                EcareManager.Prescription._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                EcareManager.Prescription.COLUMN_NAME_DOCTOR_EMAIL + " TEXT,"+
                EcareManager.Prescription.COLUMN_NAME_PATIENT_EMAIL + " TEXT,"+
                EcareManager.Prescription.COLUMN_NAME_MEDICINES + " TEXT,"+
                EcareManager.Prescription.COLUMN_NAME_TESTS + " TEXT)";


        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_MEDICINE);
        db.execSQL(SQL_CREATE_ENTRIES_DOCTORS);
        db.execSQL(SQL_CREATE_ENTRIES_CART_PHARMACY);

        db.execSQL(SQL_CREATE_ENTRIES_TESTS);
        db.execSQL(SQL_CREATE_ENTRIES_DELIVERY);
        db.execSQL(SQL_CREATE_ENTRIES_TIMESLOTS);
        db.execSQL(SQL_CREATE_ENTRIES_PRESCRIPTIONS);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.Users.TABLE_NAME);
        
        db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.Tests.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.Doctors.TABLE_NAME);


        db.execSQL("DROP TABLE IF EXISTS " + EcareManager.Medicine.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EcareManager.PharmacyCart.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EcareManager.Deliver.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.Doctors.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.TimeSlots.TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS "+ EcareManager.Prescription.TABLE_NAME);
        onCreate(db);
    }

    public boolean addUsers(String userName, String userEmail, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //Administrator
        //Patient
        //PharmacyAdmin

        //Tester

        String designation = "Patient";
        String gender = "NULL";
        String address = "NULL";
        String mobile = "NULL";

        values.put(EcareManager.Users.COL_NAME_USERNAME, userName);
        values.put(EcareManager.Users.COL_NAME_USEREMAIL, userEmail);
        values.put(EcareManager.Users.COL_NAME_DESIGNATION, designation);
        values.put(EcareManager.Users.COL_NAME_PASSWORD, password);
        values.put(EcareManager.Users.COL_NAME_GENDER, gender);
        values.put(EcareManager.Users.COL_NAME_MOBILE, mobile);
        values.put(EcareManager.Users.COL_NAME_ADDRESS, address);

        long result = db.insert(EcareManager.Users.TABLE_NAME, null, values);

        if (result == -1)
            return false;
        else
            return true;

    }


    public boolean SignInUser(String userEmail, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {EcareManager.Users._ID,
                EcareManager.Users.COL_NAME_USERNAME,
                EcareManager.Users.COL_NAME_DESIGNATION,

                EcareManager.Users.COL_NAME_GENDER,
                EcareManager.Users.COL_NAME_ADDRESS,
                EcareManager.Users.COL_NAME_MOBILE,

                EcareManager.Users.COL_NAME_USEREMAIL};


        String[] selectionArgs = {userEmail, password};

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

         this.LoggedUserID = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users._ID));
            this.LoggedUserName = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_USERNAME));
            this.LoggedUserType = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_DESIGNATION));

            this.LoggedUserGender = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_GENDER));
            this.LoggedUserAddress = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_ADDRESS));
            this.LoggedUserMobile = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_MOBILE));

            this.LoggedUserEmail = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_USEREMAIL));

            //cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Users.COL_NAME_USERNAME));
        }



        int count = cursor.getCount();
        cursor.close();
        db.close();


        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }





    public static String getLoggedUserName() {
        return LoggedUserName;
    }

    public static String getLoggedUserType() {
        return LoggedUserType;
    }


    public static String getPatientID() {
        return PatientID;
    }

    public static void setPatientID(String patientID) {
        PatientID = patientID;
    }

    public static String getLoggedUserID() { return LoggedUserID;    }

    public static void setLoggedUserID(String loggedUserID) { LoggedUserID = loggedUserID;  }





    
    public static String getLoggedUserEmail() {
        return LoggedUserEmail;
    }


    public boolean addDoctor(String doctorName, String doctorEmail, String hospitalName, String doctorMobile,
                             String doctorSpecilization, String doctorNic) {



        //firstly add users table
        SQLiteDatabase dbUsers = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        String designation = "Doctor";

        userValues.put(EcareManager.Users.COL_NAME_USERNAME, doctorName);
        userValues.put(EcareManager.Users.COL_NAME_USEREMAIL, doctorEmail);
        userValues.put(EcareManager.Users.COL_NAME_DESIGNATION, designation);
        userValues.put(EcareManager.Users.COL_NAME_PASSWORD, doctorNic);


        //sencondly add doctors table
        SQLiteDatabase dbDoctors = this.getWritableDatabase();

        ContentValues doctorValues = new ContentValues();

        doctorValues.put(EcareManager.Doctors.COL_NAME_DOCTORNAME, doctorName);
        doctorValues.put(EcareManager.Doctors.COL_NAME_DOCTOREMAIL, doctorEmail);
        doctorValues.put(EcareManager.Doctors.COL_NAME_HOSPITAL, hospitalName);
        doctorValues.put(EcareManager.Doctors.COL_NAME_DOCTORMOBILE, doctorMobile);
        doctorValues.put(EcareManager.Doctors.COL_NAME_SPECIALIZATION, doctorSpecilization);
        doctorValues.put(EcareManager.Doctors.COL_NAME_NIC, doctorNic);


        long resultUser = dbUsers.insert(EcareManager.Users.TABLE_NAME, null, userValues);
        long resultDoctor = dbDoctors.insert(EcareManager.Doctors.TABLE_NAME, null, doctorValues);

        if (resultUser == -1 && resultDoctor == -1)
            return false;
        else
            return true;


    }


    public Cursor getPatientListContents() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = EcareManager.Users.COL_NAME_DESIGNATION + " =? ";
        String[] projection = {EcareManager.Users._ID,
                EcareManager.Users.COL_NAME_USERNAME,
                EcareManager.Users.COL_NAME_USEREMAIL,
                EcareManager.Users.COL_NAME_DESIGNATION};

        Cursor data = db.query(EcareManager.Users.TABLE_NAME,
                projection,
                selection,
                new String[]{"Patient"},
                null,
                null,
                null);
        return data;
    }


    public Cursor getDoctorListContents() {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor data = db.rawQuery("SELECT * FROM " + EcareManager.Doctors.TABLE_NAME, null);
        return data;
    }


    public boolean getUserCount(String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        //return true if item already exists in the cart
        Cursor cursor = db.query(EcareManager.Users.TABLE_NAME,
                new String[]{EcareManager.Users.COL_NAME_USEREMAIL},
                EcareManager.Users.COL_NAME_USEREMAIL + " = ?",
                new String[]{userEmail},
                null,
                null,
                EcareManager.Users.COL_NAME_USEREMAIL + " ASC");
        int count = cursor.getCount();
        db.close();

        cursor.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }


    }

    public ArrayList selectAll() {

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME};

        Cursor cursor = db.query(EcareManager.Medicine.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + " ASC");

        ArrayList<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String MedicineName = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME));
            list.add(MedicineName);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int addMedicine(MedicineItemClass item) {

        //1 = inserted
        //2 = updated
        //3 = not inserted nor updated

        if (!checkMedicineExist(item.getNameMedicine())) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME, item.getNameMedicine());
            values.put(EcareManager.Medicine.COLUMN_NAME_PRICE, item.getPrice());
            values.put(EcareManager.Medicine.COLUMN_NAME_PRICE_ITEM_TYPE, item.getPriceItemType());
            values.put(EcareManager.Medicine.COLUMN_NAME_DESCRIPTION, item.getDescription());
            values.put(EcareManager.Medicine.COLUMN_NAME_USAGE, item.getUsage());
            values.put(EcareManager.Medicine.COLUMN_NAME_INGREDIENTS, item.getIngredients());
            values.put(EcareManager.Medicine.COLUMN_NAME_SIDE_EFFECTS, item.getSideEffects());
            values.put(EcareManager.Medicine.COLUMN_NAME_IMAGE, item.getImage());
            long id = db.insert(EcareManager.Medicine.TABLE_NAME,
                    null,
                    values);

            db.close();
            if (id > 0) {
                return 1;
            } else {
                return 3;
            }
        } else {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(EcareManager.Medicine.COLUMN_NAME_PRICE, item.getPrice());
            values.put(EcareManager.Medicine.COLUMN_NAME_PRICE_ITEM_TYPE, item.getPriceItemType());
            values.put(EcareManager.Medicine.COLUMN_NAME_DESCRIPTION, item.getDescription());
            values.put(EcareManager.Medicine.COLUMN_NAME_USAGE, item.getUsage());
            values.put(EcareManager.Medicine.COLUMN_NAME_INGREDIENTS, item.getIngredients());
            values.put(EcareManager.Medicine.COLUMN_NAME_SIDE_EFFECTS, item.getSideEffects());
            values.put(EcareManager.Medicine.COLUMN_NAME_IMAGE, item.getImage());
            int count = db.update(EcareManager.Medicine.TABLE_NAME, values,
                    EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + "=?",
                    new String[]{item.getNameMedicine()});

            if (count > 0) {
                return 2;
            } else {
                return 3;
            }
        }

    }

    public boolean checkMedicineExist(String name) {
        //return true if there is already a medicine with the same name
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME};
        String Selection = EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + " =? ";
        String[] selectionArgs = {name};

        Cursor cursor = db.query(EcareManager.Medicine.TABLE_NAME,
                projection,
                Selection,
                selectionArgs,
                null,
                null,
                EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + " ASC");


        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    public MedicineItemClass selectMedicineItem(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME,
                EcareManager.Medicine.COLUMN_NAME_PRICE,
                EcareManager.Medicine.COLUMN_NAME_PRICE_ITEM_TYPE,
                EcareManager.Medicine.COLUMN_NAME_DESCRIPTION,
                EcareManager.Medicine.COLUMN_NAME_USAGE,
                EcareManager.Medicine.COLUMN_NAME_INGREDIENTS,
                EcareManager.Medicine.COLUMN_NAME_SIDE_EFFECTS,
                EcareManager.Medicine.COLUMN_NAME_IMAGE
        };
        String Selection = EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + " =? ";
        String[] selectionArgs = {name};

        Cursor cursor = db.query(EcareManager.Medicine.TABLE_NAME,
                projection,
                Selection,
                selectionArgs,
                null,
                null,
                EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + " ASC");

        MedicineItemClass item = new MedicineItemClass();
        while (cursor.moveToNext()) {
            String MedicineName = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME));
            float price = cursor.getFloat(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_PRICE));
            String priceType = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_PRICE_ITEM_TYPE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_DESCRIPTION));
            String usage = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_USAGE));
            String ingredients = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_INGREDIENTS));
            String sideEffects = cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_SIDE_EFFECTS));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(EcareManager.Medicine.COLUMN_NAME_IMAGE));


            item.setNameMedicine(MedicineName);
            item.setPrice(price);
            item.setPriceItemType(priceType);
            item.setDescription(description);
            item.setUsage(usage);
            item.setIngredients(ingredients);
            item.setSideEffects(sideEffects);
            item.setImage(image);
        }
        cursor.close();
        db.close();
        return item;
    }


    public boolean deleteMedicine(String name) {

        SQLiteDatabase db = getWritableDatabase();

        db.delete(EcareManager.Medicine.TABLE_NAME,
                EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + " =? ",
                new String[]{name});

        db.close();

        if (checkMedicineExist(name)) {
            return false;
        } else {
            return true;
        }

    }

    public boolean updateMedicine(MedicineItemClass item) {

        if (checkMedicineExist(item.getNameMedicine())) {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME, item.getNameMedicine());
            values.put(EcareManager.Medicine.COLUMN_NAME_PRICE, item.getPrice());
            values.put(EcareManager.Medicine.COLUMN_NAME_PRICE_ITEM_TYPE, item.getPriceItemType());
            values.put(EcareManager.Medicine.COLUMN_NAME_DESCRIPTION, item.getDescription());
            values.put(EcareManager.Medicine.COLUMN_NAME_USAGE, item.getUsage());
            values.put(EcareManager.Medicine.COLUMN_NAME_INGREDIENTS, item.getIngredients());
            values.put(EcareManager.Medicine.COLUMN_NAME_SIDE_EFFECTS, item.getSideEffects());
            values.put(EcareManager.Medicine.COLUMN_NAME_IMAGE, item.getImage());


            int count = db.update(EcareManager.Medicine.TABLE_NAME,
                    values,
                    EcareManager.Medicine.COLUMN_NAME_MEDICINE_NAME + "= ?",
                    new String[]{item.getNameMedicine()});

            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ArrayList<MedicineItemClass> selectAllCart() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME,
                EcareManager.PharmacyCart.COLUMN_NAME_AMOUNT,
                EcareManager.PharmacyCart.COLUMN_NAME_PRICE_FOR_ONE_ITEM,
                EcareManager.PharmacyCart.COLUMN_NAME_PRICE_TYPE};

        Cursor cursor = db.query(EcareManager.PharmacyCart.TABLE_NAME,
                projection,
                EcareManager.PharmacyCart.COLUMN_NAME_USERNAME + " =?",
                new String[]{getLoggedUserName()},
                null,
                null,
                EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME + " ASC");
        ArrayList<MedicineItemClass> CartListItem = new ArrayList<>();
        while (cursor.moveToNext()) {
            MedicineItemClass item = new MedicineItemClass();
            item.setNameMedicine(cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME)));
            item.setPriceItemType(cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.PharmacyCart.COLUMN_NAME_PRICE_TYPE)));
            item.setPrice(cursor.getFloat(cursor.getColumnIndexOrThrow(EcareManager.PharmacyCart.COLUMN_NAME_PRICE_FOR_ONE_ITEM)));
            item.setAmount(cursor.getFloat(cursor.getColumnIndexOrThrow(EcareManager.PharmacyCart.COLUMN_NAME_AMOUNT)));

            CartListItem.add(item);
        }

        return CartListItem;
    }


    public boolean checkItemCart(String name) {
        //return true if item already exists in the cart
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(EcareManager.PharmacyCart.TABLE_NAME,
                new String[]{EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME},
                EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME + " = ? AND " + EcareManager.PharmacyCart.COLUMN_NAME_USERNAME + " =?",
                new String[]{name, getLoggedUserName()},
                null,
                null,
                EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME + " ASC");
        int count = cursor.getCount();

        db.close();
        cursor.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkCartIsEmpty() {
        //return true if cart is empty
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(EcareManager.PharmacyCart.TABLE_NAME,
                new String[]{EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME},
                EcareManager.PharmacyCart.COLUMN_NAME_USERNAME + " = ?",
                new String[]{getLoggedUserName()},
                null,
                null,
                EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME + " ASC");
        int count = cursor.getCount();

        db.close();
        cursor.close();
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean clearCart() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(EcareManager.PharmacyCart.TABLE_NAME,
                EcareManager.PharmacyCart.COLUMN_NAME_USERNAME + " =?",
                new String[]{getLoggedUserName()});
        if (checkCartIsEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean cartAddItem(MedicineItemClass item, float Amount) {


        if (!checkItemCart(item.getNameMedicine()) && Amount > 0) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME, item.getNameMedicine());
            values.put(EcareManager.PharmacyCart.COLUMN_NAME_AMOUNT, Amount);
            values.put(EcareManager.PharmacyCart.COLUMN_NAME_PRICE_FOR_ONE_ITEM, item.getPrice());
            values.put(EcareManager.PharmacyCart.COLUMN_NAME_PRICE_TYPE, item.getPriceItemType());
            values.put(EcareManager.PharmacyCart.COLUMN_NAME_USERNAME, getLoggedUserName());


            long id = db.insert(EcareManager.PharmacyCart.TABLE_NAME,
                    null,
                    values);
            db.close();
            if (id > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            //values.put(EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME,item.getNameMedicine());
            values.put(EcareManager.PharmacyCart.COLUMN_NAME_AMOUNT, Amount);
            /*
            values.put(EcareManager.PharmacyCart.COLUMN_NAME_PRICE_FOR_ONE_ITEM,item.getPriceItemType());
            values.put(EcareManager.PharmacyCart.COLUMN_NAME_PRICE_TYPE,item.getPriceItemType());*/
            db.update(EcareManager.PharmacyCart.TABLE_NAME,
                    values,
                    EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME + " = ? AND " + EcareManager.PharmacyCart.COLUMN_NAME_USERNAME + " =?",
                    new String[]{item.getNameMedicine(), getLoggedUserName()});
            db.close();
            return true;
        }
    }


    public boolean cartDeleteItem(String name) {
        if (checkItemCart(name)) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(EcareManager.PharmacyCart.TABLE_NAME,
                    EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME + " =? AND " + EcareManager.PharmacyCart.COLUMN_NAME_USERNAME + "=?",
                    new String[]{name, getLoggedUserName()});
            if (checkItemCart(name)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    public float calculateCartTotal() {


        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {EcareManager.PharmacyCart.COLUMN_NAME_PRICE_FOR_ONE_ITEM,
                EcareManager.PharmacyCart.COLUMN_NAME_AMOUNT};
        Cursor cursor = db.query(EcareManager.PharmacyCart.TABLE_NAME,
                projection,
                EcareManager.PharmacyCart.COLUMN_NAME_USERNAME + " =?",
                new String[]{getLoggedUserName()},
                null,
                null,
                EcareManager.PharmacyCart.COLUMN_NAME_MEDICINE_NAME + " ASC");
        float totalAmount = 0;
        while (cursor.moveToNext()) {
            float priceForOne = cursor.getFloat(cursor.getColumnIndexOrThrow(EcareManager.PharmacyCart.COLUMN_NAME_PRICE_FOR_ONE_ITEM));
            float amount = cursor.getFloat(cursor.getColumnIndexOrThrow(EcareManager.PharmacyCart.COLUMN_NAME_AMOUNT));

            totalAmount = totalAmount + priceForOne * amount;
        }
        cursor.close();
        db.close();
        return totalAmount;

    }



    //functions for tests


    // add test query
    public boolean addTest(int pid,int tid,String tname,String des,float price,String date)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EcareManager.Tests.COLUMN_NAME_PATIENT_ID,pid);
        values.put(EcareManager.Tests.COLUMN_NAME_TESTER_ID,tid);
        values.put(EcareManager.Tests.COLUMN_NAME_TEST_NAME,tname);
        values.put(EcareManager.Tests.COLUMN_NAME_TEST_DESCRIPTION,des);
        values.put(EcareManager.Tests.COLUMN_NAME_TEST_PRICE,price);
        values.put(EcareManager.Tests.COLUMN_NAME_TEST_DATE,date);

        long chkAddTest = db.insert(EcareManager.Tests.TABLE_NAME,null,values);

        return chkAddTest > 0;

    }

    //get name of the user to show in description
    public void getUserName(String pid)
    {

        SQLiteDatabase db = this.getReadableDatabase();

        String GET_USER_NAME_QUERY = "SELECT * FROM "+EcareManager.Users.TABLE_NAME+" WHERE "+EcareManager.Users._ID+" = ?";
        Cursor cursor = db.rawQuery(GET_USER_NAME_QUERY,new String[]{pid});

        while(cursor.moveToFirst())
        {

            LoggedUserName = cursor.getString(1);

        }


    }

    //get test table contents fro patient
    //for update fragment

    public Cursor getPreviousTests(String pid)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String QUERY_GET_PRE_TEST = "SELECT * FROM "+EcareManager.Tests.TABLE_NAME+" WHERE "+EcareManager.Tests.COLUMN_NAME_PATIENT_ID+" = ?";

        Cursor cursor = db.rawQuery(QUERY_GET_PRE_TEST,new String[]{pid});

        Log.d(TAG, "getPreviousTests: query executed");

        return cursor;

    }

    //get test table contents of all time
    //for history fragment

    public Cursor getTestsHistory()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String QUERY_GET_PRE_TEST = "SELECT * FROM "+EcareManager.Tests.TABLE_NAME;

        Cursor cursor = db.rawQuery(QUERY_GET_PRE_TEST,null);

        return cursor;

    }

    public Cursor getTest(String testID)
    {

        Log.d(TAG, "getTest: testID :"+testID);
        SQLiteDatabase db = this.getReadableDatabase();

        String QUERY_GET_TEST = "SELECT * FROM "+EcareManager.Tests.TABLE_NAME+" WHERE "+EcareManager.Tests._ID+" = ? ";

        Cursor cursor = db.rawQuery(QUERY_GET_TEST,new String[]{testID});

        return cursor;

    }

    public void updateTest(String tID,String tName,String tDes,String tCost)
    {
        Log.d(TAG, "updateTEst: testID :"+tID);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EcareManager.Tests.COLUMN_NAME_TEST_NAME,tName);
        values.put(EcareManager.Tests.COLUMN_NAME_TEST_DESCRIPTION,tDes);
        values.put( EcareManager.Tests.COLUMN_NAME_TEST_PRICE,tCost);

        db.update(EcareManager.Tests.TABLE_NAME,values,"_id = ?",new String[]{tID});

        Log.d(TAG, "updateTEst: query executed");


        /*String QUERY_UPDATE_TEST = " UPDATE "+EcareManager.Tests.TABLE_NAME+" SET "+
                EcareManager.Tests.COLUMN_NAME_TEST_NAME + " = '" + tName +"' , "+
                EcareManager.Tests.COLUMN_NAME_TEST_DESCRIPTION + " = '" + tDes +"' , "+
                EcareManager.Tests.COLUMN_NAME_TEST_PRICE+ " = '"+ tCost + "' WHERE "+
                EcareManager.Tests._ID+ " = ? ";*/



        //db.execSQL(QUERY_UPDATE_TEST,new String[]{tID});

    }

    public void deleteTest(String testID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int chkDelete = db.delete(EcareManager.Tests.TABLE_NAME,"_id = ?",new String[]{testID});

        Log.d(TAG, "deleteTest: chkdelete value : "+chkDelete);


    }


    public Cursor getPatientName(String pid)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String patName ;

        String QUERY_GET_PATIENT_DATA = " SELECT * FROM "+ EcareManager.Users.TABLE_NAME +" WHERE "+EcareManager.Users._ID + " = ? ";

        Cursor cursor = db.rawQuery(QUERY_GET_PATIENT_DATA,new String[]{pid});

        Log.d(TAG, "getPatientName: BeforepatName :");

        return cursor;


    }

    public String gettestID(String testName) {

        SQLiteDatabase db = this.getReadableDatabase();

        String TestID;

        String QUERY_GET_TEST_ID = " SELECT * FROM " + EcareManager.Tests.TABLE_NAME + " WHERE " + EcareManager.Tests.COLUMN_NAME_TEST_NAME + " = ?";

        Cursor cursor = db.rawQuery(QUERY_GET_TEST_ID, new String[]{testName});

        if (cursor.moveToFirst()) {

            TestID = cursor.getString(cursor.getColumnIndex("_id"));


        } else {

            TestID = " ";

        }

        return TestID;
    }

        public boolean deleteUsers (String userEmail){

            if (getUserCount(userEmail)) {

                SQLiteDatabase db = getWritableDatabase();

                db.delete(EcareManager.Users.TABLE_NAME,
                        EcareManager.Users.COL_NAME_USEREMAIL + " =? ",
                        new String[]{userEmail});

                db.close();
                if (getUserCount(userEmail)) {
                    return false;
                } else {
                    return true;
                }

            } else {
                return true;
            }

        }


        public long addDeliver (DeliverClass item){


            //status 0 =  not deliverd yet
            //status 1 = delivered
            //status 2 = stop the delivery
            //status 3 = accepted
            //status 4 = rejected

            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(EcareManager.Deliver.COLUMN_NAME_USER_NAME, item.getUserName());
            values.put(EcareManager.Deliver.COLUMN_NAME_EMAIL, item.getEmail());
            values.put(EcareManager.Deliver.COLUMN_NAME_ADDRESS, item.getAddress());
            values.put(EcareManager.Deliver.COLUMN_NAME_MEDICINE_ITEMS_NAMES, item.getItemNames());
            values.put(EcareManager.Deliver.COLUMN_NAME_MEDICINE_ITEMS_AMOUNT, item.getItemsAmount());
            values.put(EcareManager.Deliver.COLUMN_NAME_PHONE_NUMBER, item.getPhonenumber());
            values.put(EcareManager.Deliver.COLUMN_NAME_STATUS, item.getStatus());
            values.put(EcareManager.Deliver.COLUMN_NAME_PRICE_TOTAL, item.getTotalprice());
            values.put(EcareManager.Deliver.COLUMN_NAME_DATETIME, item.getDateTime());


            long id = db.insert(EcareManager.Deliver.TABLE_NAME, null, values);

            return id;
        }


        public void updateUser (String address, String mobile, String email){
            ContentValues values = new ContentValues();
            values.put(EcareManager.Users.COL_NAME_ADDRESS, address);
            values.put(EcareManager.Users.COL_NAME_MOBILE, mobile);

            SQLiteDatabase db = this.getWritableDatabase();
            db.update(EcareManager.Users.TABLE_NAME, values, EcareManager.Users.COL_NAME_USEREMAIL + "	= ?", new String[]{String.valueOf(email)});
        }


        public boolean addTimeSlot (TimeSlots timeSlots){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(EcareManager.TimeSlots.COL_DOCTOR_EMAIL, timeSlots.getDoctorEmail());
            values.put(EcareManager.TimeSlots.COL_SLOT_DAY, timeSlots.getSlotDay());
            values.put(EcareManager.TimeSlots.COL_SLOT_START_TIME, timeSlots.getSlotStartTime());
            values.put(EcareManager.TimeSlots.COL_SLOT_END_TIME, timeSlots.getSlotEndTime());


            long result = db.insert(EcareManager.TimeSlots.TABLE_NAME, null, values);

            if (result == -1) {
                return false;
            }
            else {
                return true;
            }


        }



        public Cursor getDoctorSlotListContents () {
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor data = db.rawQuery("SELECT * FROM " + EcareManager.TimeSlots.TABLE_NAME, null);
            return data;
        }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList selectAllDocs(){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {EcareManager.Doctors.COL_NAME_DOCTORNAME,
                EcareManager.Doctors.COL_NAME_SPECIALIZATION };

        Cursor cursor = db.query(EcareManager.Doctors.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                EcareManager.Doctors.COL_NAME_DOCTORNAME + " ASC");

        HashMap<String, String> DocNameSpec = new HashMap<>();

        List<HashMap<String,String>> listItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            DocNameSpec.put(cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Doctors.COL_NAME_DOCTORNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Doctors.COL_NAME_SPECIALIZATION)));
        }

        Iterator it = DocNameSpec.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        cursor.close();
        db.close();
        return (ArrayList) listItems;
    }
    public ArrayList selectSomeDocs(String name){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {EcareManager.Doctors.COL_NAME_DOCTORNAME,EcareManager.Doctors.COL_NAME_SPECIALIZATION};

        Cursor cursor = db.query(EcareManager.Doctors.TABLE_NAME,
                projection,
                EcareManager.Doctors.COL_NAME_DOCTORNAME + " LIKE ?",
                new String[]{name+"%"},
                null,
                null,
                EcareManager.Doctors.COL_NAME_DOCTORNAME + " ASC");

        HashMap<String, String> DocNameSpec = new HashMap<>();

        List<HashMap<String,String>> listItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            DocNameSpec.put(cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Doctors.COL_NAME_DOCTORNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(EcareManager.Doctors.COL_NAME_SPECIALIZATION)));
        }

        Iterator it = DocNameSpec.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);

        }
        cursor.close();
        db.close();
        return (ArrayList) listItems;
    }


    public boolean addAppoinments(Appoinments appoinments){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EcareManager.Appoinments.COL_DOCTOR_EMAIL,appoinments.getDoctorEmail());
        values.put(EcareManager.Appoinments.COL_DOCTOR_EMAIL,appoinments.getPatientEmail());
        values.put(EcareManager.Appoinments.COL_SLOT_DAY,appoinments.getSlotDay());
        values.put(EcareManager.Appoinments.COL_SLOT_START_TIME,appoinments.getSlotStartTime());
        values.put(EcareManager.Appoinments.COL_SLOT_END_TIME,appoinments.getSlotEndTime());


        long result = db.insert(EcareManager.TimeSlots.TABLE_NAME,null,values);

        if(result == -1)
            return false;
        else
            return true;

    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    }

