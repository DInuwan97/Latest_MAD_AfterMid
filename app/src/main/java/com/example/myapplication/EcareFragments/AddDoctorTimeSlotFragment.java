package com.example.myapplication.EcareFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.myapplication.PharmacyAdminAddMedicine;
import com.example.myapplication.R;

import static com.example.myapplication.PharmacyAdminAddMedicine.DATA_RECIEVE;


public class AddDoctorTimeSlotFragment extends Fragment {

    Bundle args = getArguments();
    String medicineName = args.getString(DATA_RECIEVE);
    public static final String DATA_RECIEVE = "data_recieve";
    Button btnViewDoctorTimeSlot;
    ArrayAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_doctor_time_slot, container, false);
        adapter.notifyDataSetChanged();






        return v;



    }


}
