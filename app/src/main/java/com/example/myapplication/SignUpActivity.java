package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Validations.Validations;

public class SignUpActivity extends AppCompatActivity {

    DBHandler myDb;
    Validations signUpVal;
    EditText txtUserName,txtUserEmail,txtDesignation,txtPassword,txtConfirmPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        myDb = new DBHandler(this);


        txtUserName = (EditText)findViewById(R.id.editText_username);
        txtUserEmail = (EditText)findViewById(R.id.editText_email);
        txtPassword = (EditText)findViewById(R.id.editText_pwrd);
        txtConfirmPassword = (EditText)findViewById(R.id.editText_cpwrd);

        btnSignUp = (Button) findViewById(R.id.button_signup);

        addNewUser();

    }


    public void addNewUser(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signUpVal = new Validations();

                String msg = signUpVal.signUpValidations(
                        txtUserName.getText().toString(),
                        txtUserEmail.getText().toString(),
                        txtPassword.getText().toString(),
                        txtConfirmPassword.getText().toString());


                //if(msg == "OK") {
                    boolean isInserted = myDb.addUsers(txtUserName.getText().toString(),
                            txtUserEmail.getText().toString(),
                            txtPassword.getText().toString());



                    if (isInserted == true) {
                        Toast.makeText(SignUpActivity.this, "Sucessfully Registered!!!", Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(this,SignInActivity.class);
                        //startActivity(intent);
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }
                    //}

                /*}else if(msg == "Fill all the details!!!"){

                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();

                }else if(msg == "Password Mismatching!!!"){

                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();

                }*/

               /* }
                else if(msg.equals("Fill all the details!!!")){
                    Toast.makeText(SignUpActivity.this,msg, Toast.LENGTH_LONG).show();
                }
                else if(msg.equals("Password Mismatching!!!")){
                    Toast.makeText(SignUpActivity.this,msg, Toast.LENGTH_LONG).show();
                }*/

            }
         }

        );
    }

    public void showSignInActivity(View view){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }
}
