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



}
