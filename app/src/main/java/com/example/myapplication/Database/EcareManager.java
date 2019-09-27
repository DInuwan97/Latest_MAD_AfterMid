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
        public static final String COLUMN_NAME_USERNAME = "username";


    }

    protected static class Deliver implements BaseColumns{

        public static final String TABLE_NAME = "deliverytable";
        public static final String COLUMN_NAME_USER_NAME = "username";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_PHONE_NUMBER = "phonenumber";
        public static final String COLUMN_NAME_MEDICINE_ITEMS_NAMES = "itemsnames";
        public static final String COLUMN_NAME_MEDICINE_ITEMS_AMOUNT = "itemscount";
        public static final String COLUMN_NAME_PRICE_TOTAL = "total";
        public static final String COLUMN_NAME_DATETIME = "datetime";


    }

    protected  static class TimeSlots implements BaseColumns{

        public static final String TABLE_NAME = "timeslots";
        public static final String COL_DOCTOR_EMAIL = "doctoremail";
        public static final String COL_SLOT_DAY = "slotday";
        public static final String COL_SLOT_START_TIME = "slotstarttime";
        public static final String COL_SLOT_END_TIME = "slotendtime";

    }

}
