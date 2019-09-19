package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PharmacyPatient extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new PharmacyMedicineList()).commit();

        View v = inflater.inflate(R.layout.fragment_pharmacy_patient, container, false);
        BottomNavigationView bottomNav = v.findViewById(R.id.bottom_nav_menu_pharmacy);


        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;


                switch (item.getItemId()) {
                    case R.id.nav_cart:
                        selectedFragment = new FavoritesFragment();
                        break;

                    case R.id.nav_medicine:
                        selectedFragment = new PharmacyMedicineList();
                        break;


                }

                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });


        return v;
    }



}
