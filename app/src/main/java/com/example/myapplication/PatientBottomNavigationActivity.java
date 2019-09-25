package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Database.DBHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class PatientBottomNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    TextView txtViewUserEmail,txtViewUserName;
    String LoggedUserEmail,LoggedUserType;

    EditText txtDoctorName,txtDoctorEmail,txtHospital,txtDoctorMobile,txtSpecilization,txtDoctorNic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_bottom_navigation);

        DBHandler myDB = new DBHandler(this);


        if(/*myDB.getLoggedUserGender().toString().equals("NULL")  || */ myDB.getLoggedUserAddress().toString().equals("NULL")  || myDB.getLoggedUserMobile().toString().equals("NULL")){
            loadDialog();
        }




        intent = getIntent();
        LoggedUserEmail = intent.getStringExtra("A");
        Log.i("TEST_main",LoggedUserEmail.toString());



        NavigationView navigationView1 = findViewById(R.id.nav_view);
        View header1 = navigationView1.getHeaderView(0);
        txtViewUserEmail = header1.findViewById(R.id.textViewLoggedEmail);
        txtViewUserEmail.setText(LoggedUserEmail.toString());

        txtViewUserName = header1.findViewById(R.id.txtLoggedUserName);
        txtViewUserName.setText(myDB.getLoggedUserName().toString());


        LoggedUserType = myDB.getLoggedUserType().toString();



        if(savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, new MyAppoinmentsFragment());
            transaction.commit();
        }



        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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

            //remove the comment to make the back button work
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new MyAppoinmentsFragment();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new FavoritesFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_pharmacy:
                            selectedFragment = new PharmacyPatient();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };


    public void loadDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.dialogbox_update_user_profile, null);

        //S TextView txtDeleteUserDialogBoxConfirmation = (TextView) subView.findViewById(R.id.txtDeleteUser);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setTitle("Update Your Profile");
        //builder.setMessage("Successfully Deleted");
        builder.setView(subView);
        builder.create();





       /* btnUserUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                DBHandler db = new DBHandler(getApplicationContext());


                txtAddress = (EditText)findViewById(R.id.txtAddress);
                txtMobile = (EditText)findViewById(R.id.txtMobile);
                btnUserUpdate = (Button) findViewById(R.id.btnUpdateUser);

                userAddress = txtAddress.toString();
                userMobile = txtMobile.toString();



                if(db.updateUserDetails(userAddress,userMobile)){
                    Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_LONG).show();
                }else{
                    Log.i("testing","User not Deleted");
                }
            }
        });*/


        /*builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHandler db = new DBHandler(getApplicationContext());

                txtAddress = (EditText)findViewById(R.id.txtAddress);
                txtMobile = (EditText)findViewById(R.id.txtMobile);

                userAddress = txtAddress.toString();
                userMobile = txtMobile.toString();

                if(db.updateUserDetails(userAddress,userMobile)){
                    Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_LONG).show();
                }else{
                    Log.i("testing","User not Deleted");
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/

        //btnUpdateUser
        builder.show();
    }



}
