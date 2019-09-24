package com.example.myapplication.Database;

import android.provider.BaseColumns;

public final class EcareManager {

    private EcareManager(){

    }

    protected static class Users implements BaseColumns{

        public static final String TABLE_NAME = "users";
        public static final String COL_NAME_USERNAME = "username";
        public static final String COL_NAME_USEREMAIL = "useremail";
        public static final String COL_NAME_DESIGNATION = "designation";
        public static final String COL_NAME_PASSWORD = "password";

        public static final String COL_NAME_GENDER = "gender";
        public static final String COL_NAME_MOBILE = "mobile";
        public static final String COL_NAME_ADDRESS = "address";
        public static final String COL_NAME_USERIMAGE = "userimage";


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


}
