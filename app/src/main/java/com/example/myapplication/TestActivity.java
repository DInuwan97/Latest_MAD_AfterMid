package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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

public class TestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    Intent intent;
    TextView txtViewUserEmail,txtViewUserName;
    String LoggedUserEmail,LoggedUserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        DBHandler myDB = new DBHandler(this);

        intent = getIntent();
        LoggedUserEmail = intent.getStringExtra("A");
        //Log.i("TEST_test",LoggedUserEmail.toString());

        NavigationView navigationView1 = findViewById(R.id.nav_view);
        View header1 = navigationView1.getHeaderView(0);
        txtViewUserEmail = header1.findViewById(R.id.textViewLoggedEmail);
        //txtViewUserEmail.setText(LoggedUserEmail.toString());
        txtViewUserEmail.setText(LoggedUserEmail);

        txtViewUserName = header1.findViewById(R.id.txtLoggedUserName);
        //txtViewUserName.setText(myDB.getLoggedUserName().toString());
        txtViewUserName.setText(myDB.getLoggedUserName());


        //LoggedUserType = myDB.getLoggedUserType().toString();
        LoggedUserType = myDB.getLoggedUserType();











        if(savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, new TestFindUserFragment());
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_options, menu);
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
            View view = inflater.inflate(R.layout.about,null);
            AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
            builder.setView(view);
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
                        case R.id.nav_find_pid:
                            selectedFragment = new TestFindUserFragment();
                            break;

                        case R.id.nav_test_description:
                            selectedFragment = new TestDescriptionFragment();
                            break;

                        case R.id.nav_test_add:
                            selectedFragment = new TestAddFragment();
                            break;

                        case R.id.nav_test_history:
                            selectedFragment = new TestHistoryFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
