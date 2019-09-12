package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainPromptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prompt);
    }

    public void showSignUpActivity(View view){
        Intent intent1 = new Intent(this,SignUpActivity.class);
        startActivity(intent1);
    }

    public void showSignInActivity(View view){
        Intent intent1 = new Intent(this,SignInActivity.class);
        startActivity(intent1);
    }
}
