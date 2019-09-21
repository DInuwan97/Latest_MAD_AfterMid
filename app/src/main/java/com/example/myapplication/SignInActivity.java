package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.MedicineItemClass;

public class SignInActivity extends AppCompatActivity {

    public static final String LOGGED_USER_EMAIL = "com.example.myapplication";

    DBHandler myDb;
    EditText txtUserEmail,txtPassword;
    Button btnSignIn;
    String useremail,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        myDb = new DBHandler(this);

        txtUserEmail = (EditText) findViewById(R.id.editText_email);
        txtPassword = (EditText) findViewById(R.id.editText_password);





    }

    public void signIn(View view){

        useremail = txtUserEmail.getText().toString();
        password = txtPassword.getText().toString();

        boolean isValidUser = myDb.SignInUser(useremail,password);

        if(isValidUser == true){
            Toast.makeText(SignInActivity.this,"Welcome to eCare!!!",Toast.LENGTH_LONG).show();
            Intent intent;





            if(myDb.getLoggedUserType().toString().equals("Patient")) {
                intent = new Intent(SignInActivity.this, PatientBottomNavigationActivity.class);
            }else if(myDb.getLoggedUserType().toString().equals("Administrator")){
                intent = new Intent(SignInActivity.this, MainActivity.class);
            }else if(myDb.getLoggedUserType().toString().equals("PharmacyAdmin")){
                intent = new Intent(SignInActivity.this, PharmacyAdmin.class);
            }else{
                intent = new Intent(SignInActivity.this, MainActivity.class);
            }




            Log.i("TEST_Si",useremail.toString());
            intent.putExtra("A",useremail);
            startActivity(intent);
        }
        else{
            Toast.makeText(SignInActivity.this,"Invalid Credentials!!!",Toast.LENGTH_LONG).show();
        }

    }

    public void showMainActivity(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
