package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.MedicineItemClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {

    public static final String LOGGED_USER_EMAIL = "com.example.myapplication";

    DBHandler myDb;
    EditText txtUserEmail,txtPassword;
    Button btnSignIn;
    String useremail,password;
    ArrayList<String> firebaseList=new ArrayList<>();
    ArrayList<String> sqlList;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        myDb = new DBHandler(this);

        txtUserEmail = (EditText) findViewById(R.id.editText_email);
        txtPassword = (EditText) findViewById(R.id.editText_password);


        db= new DBHandler(getApplicationContext());
        DatabaseReference DBref = FirebaseDatabase.getInstance().getReference().child("Medicine");

        Query query = DBref.orderByChild("nameMedicine");

        sqlList = db.selectAll();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    firebaseList.add(postSnapShot.child("nameMedicine").getValue().toString());
                }
                sqlList.removeAll(firebaseList);
                for(String name : sqlList){
                    db.deleteMedicine(name);
                }
                Log.i("testing fire ","done");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query2 = DBref.orderByChild("nameMedicine");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    DatabaseReference DBRef2 = FirebaseDatabase.getInstance().getReference().child("Medicine")
                            .child(postSnapshot.getKey());
                    MedicineItemClass item = new MedicineItemClass();
                    item.setAmount(Float.parseFloat(postSnapshot.child("amount").getValue().toString()));
                    item.setDescription(postSnapshot.child("description").getValue().toString());
                    item.setImageBase64(postSnapshot.child("imageBase64").getValue().toString());
                    item.setIngredients(postSnapshot.child("ingredients").getValue().toString());
                    item.setNameMedicine(postSnapshot.child("nameMedicine").getValue().toString());
                    item.setPrice(Float.parseFloat(postSnapshot.child("price").getValue().toString()));
                    item.setPriceItemType(postSnapshot.child("priceItemType").getValue().toString());
                    item.setSideEffects(postSnapshot.child("sideEffects").getValue().toString());
                    item.setUsage(postSnapshot.child("usage").getValue().toString());

                    byte[] byteImage = Base64.decode(item.getImageBase64(), Base64.DEFAULT);
                    item.setImage(byteImage);

                    db.addMedicine(item);
                }
                Toast.makeText(getApplicationContext(),"Synced with the Online Database", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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
            }else if(myDb.getLoggedUserType().toString().equals("Doctor")){
                intent = new Intent(SignInActivity.this, DoctorPortalActivity.class);
            }else{
                intent = new Intent(SignInActivity.this, DoctorPortalActivity.class);
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
