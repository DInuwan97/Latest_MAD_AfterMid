package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Database.DBHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PharmacyAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    TextView txtViewUserEmail,txtViewUserName;
    String LoggedUserEmail,LoggedUserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_medicine);

        DBHandler myDB = new DBHandler(this);

        intent = getIntent();
        LoggedUserEmail = intent.getStringExtra("A");

        NavigationView navigationView1 = findViewById(R.id.nav_view1);
        View header1 = navigationView1.getHeaderView(0);
        txtViewUserEmail = header1.findViewById(R.id.textViewLoggedEmail);
        txtViewUserEmail.setText(LoggedUserEmail.toString());

        txtViewUserName = header1.findViewById(R.id.txtLoggedUserName);
        txtViewUserName.setText(myDB.getLoggedUserName().toString());


        LoggedUserType = myDB.getLoggedUserType().toString();



        if(savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, new pharmacyAdminMedicineList());
            transaction.commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pharmacy_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.about,null);
            AlertDialog.Builder builder = new AlertDialog.Builder(PharmacyAdmin.this);
            builder.setView(v);
            builder.show();




            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        NavigationView navigationView = findViewById(R.id.nav_view1);
        if (id == R.id.nav_all_medicine) {

            bottomNav.setSelectedItemId(R.id.nav_all_medicine);
        } else if (id == R.id.nav_add_medicine) {
            bottomNav.setSelectedItemId(R.id.nav_add_medicine);
        } else if (id == R.id.nav_pending_delivery_tasks) {
            bottomNav.setSelectedItemId(R.id.nav_pending_delivery_tasks);

        } else if (id == R.id.nav_accepted_delivery_tasks) {
            bottomNav.setSelectedItemId(R.id.nav_accepted_delivery_tasks);
        } else if (id == R.id.nav_completed_delivery_tasks) {
            bottomNav.setSelectedItemId(R.id.nav_completed_delivery_tasks);
        } else if (id == R.id.nav_complete) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(PharmacyAdmin.this);
            builder1.setTitle("QR Scanner").setMessage("You want to Open the QR Scanner ?");
            builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }

            });
            builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(PharmacyAdmin.this);
                    intentIntegrator.setPrompt("Please focus the camera on the QR Code");
                    intentIntegrator.setBeepEnabled(false);
                    intentIntegrator.setBarcodeImageEnabled(false);
                    intentIntegrator.setRequestCode(9890);
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.initiateScan();



                }
            });
            AlertDialog dialog1 = builder1.create();
            dialog1.show();

        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==9890){
            if(data!= null) {
                IntentResult result = IntentIntegrator.parseActivityResult( resultCode, data);
                if (result != null) {

                    final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Delivery").child(result.getContents());
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (TextUtils.equals(dataSnapshot.child("acceptedby").getValue().toString(), DBHandler.getLoggedUserName())) {
                                dbref.child("status").setValue(1);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                String dateTime = simpleDateFormat.format(new Date());

                                dbref.child("DeliveredDateTime").setValue(dateTime);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        }


    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_all_medicine:
                            selectedFragment = new pharmacyAdminMedicineList();
                            break;

                        case R.id.nav_add_medicine:
                            selectedFragment = new PharmacyAdminAddMedicine();
                            break;

                        case R.id.nav_accepted_delivery_tasks:
                            selectedFragment = new PharmacyAdminAcceptedDeliveries();
                            break;

                        case R.id.nav_pending_delivery_tasks:
                            selectedFragment = new PharmacyAdminPendingDelivery();
                            break;
                        case R.id.nav_completed_delivery_tasks:
                            selectedFragment = new PharmacyAdminCompletedDeliveries();
                            break;
                    }
                    NavigationView navigationView = findViewById(R.id.nav_view1);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();


                    return true;
                }
            };
}

