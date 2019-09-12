package com.example.myapplication.Validations;

import android.text.TextUtils;

public class Validations {

    public Validations(){

    }

    public String signUpValidations(String userName,String userEmail,String password,String confirmPassword){

        String msg;

        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
            msg = "Fill all the details!!!";
            return msg;
        }else if(password != confirmPassword){
            msg = "Password Mismatching!!!";
            return msg;
        }else{
            msg = "OK";
            return msg;
        }

    }
}
