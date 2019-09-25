package com.example.myapplication.EcareFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;


public class AddDoctorTimeSlotFragment extends Fragment {

    public static final String DATA_RECIEVE_EMAIL = "datarecieveemail";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_doctor_time_slot, container, false);







        return v;



    }


}
