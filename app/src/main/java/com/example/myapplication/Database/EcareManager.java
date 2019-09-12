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



}
