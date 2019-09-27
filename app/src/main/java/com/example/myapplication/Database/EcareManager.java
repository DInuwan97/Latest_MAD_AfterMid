package com.example.myapplication.Database;

import android.provider.BaseColumns;

import java.nio.file.attribute.BasicFileAttributes;

public final class EcareManager {

    private EcareManager(){

    }

    protected static class Users implements BaseColumns{

        public static final String TABLE_NAME = "users";
        public static final String COL_NAME_USERNAME = "username";
        public static final String COL_NAME_USEREMAIL = "useremail";
        public static final String COL_NAME_DESIGNATION = "designation";
        public static final String COL_NAME_PASSWORD = "password";

    }

    protected static class Doctors implements  BaseColumns{

        public static final String TABLE_NAME = "doctors";
        public static final String COL_NAME_DOCTORNAME = "doctorname";
        public static final String COL_NAME_DOCTOREMAIL = "doctoremail";
        public static final String COL_NAME_HOSPITAL = "hospital";
        public static final String COL_NAME_DOCTORMOBILE = "doctormobile";
        public static final String COL_NAME_SPECIALIZATION = "specialization";
        public static final String COL_NAME_NIC = "nic";


    }

    protected static class Medicine implements BaseColumns{
        public static final String TABLE_NAME = "medicine";
        public static final String COLUMN_NAME_MEDICINE_NAME = "medicinename";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_PRICE_ITEM_TYPE = "itemtype";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_USAGE = "usage";
        public static final String COLUMN_NAME_INGREDIENTS = "ingredients";
        public static final String COLUMN_NAME_SIDE_EFFECTS = "sideeffects";
        public static final String COLUMN_NAME_IMAGE= "image";

    }


    protected static class PharmacyCart implements BaseColumns{
        public static final String TABLE_NAME = "pharmacycart";
        public static final String COLUMN_NAME_MEDICINE_NAME = "medicinename";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_PRICE_FOR_ONE_ITEM = "priceforone";
        public static final String COLUMN_NAME_PRICE_TYPE = "pricetype";


    }

    protected static class Deliver implements BaseColumns{

        public static final String TABLE_NAME = "deliverytable";
        public static final String COLUMN_NAME_USER_NAME = "username";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_PHONE_NUMBER = "phonenumber";


    }

    //tester columns

    protected static class Tests implements BaseColumns
    {
        //base columns id primary key
        public static final String TABLE_NAME = "tests";
        public static final String COLUMN_NAME_PATIENT_ID = "patient_id";
        public static final String COLUMN_NAME_TESTER_ID = "tester_id";
        public static final String COLUMN_NAME_TEST_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TEST_NAME = "test_name";
        public static final String COLUMN_NAME_TEST_PRICE = "test_price";
        public static final String COLUMN_NAME_TEST_DATE = "test_date";


    }

}
