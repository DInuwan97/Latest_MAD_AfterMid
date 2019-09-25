package com.example.myapplication.EcareFragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.DoctorListAdapter;
import com.example.myapplication.DoctorsInformation;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SearchFragment;


import java.util.ArrayList;

import java.util.List;

public class DoctorListFragment extends Fragment {

    DBHandler myDB;
    private ListView listView;
    private DoctorListAdapter adapterDoctorList;
    private List<DoctorsInformation> mDoctorList;
    private Button btnViewPatientDetails,btnViewDoctor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor_list, container, false);

        btnViewDoctor = (Button)v.findViewById(R.id.btnViewDoctor);
        myDB = new DBHandler(getActivity().getApplicationContext());
        listView = (ListView)v.findViewById(R.id.listView1);
        myDB = new DBHandler(getActivity().getApplicationContext());
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getDoctorListContents();

        mDoctorList = new ArrayList<>();


        if(data.getCount() == 0){
            Toast.makeText(getActivity().getApplicationContext(),"The Table was Empty",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){

                mDoctorList.add(new DoctorsInformation(
                                data.getInt(0),
                                data.getString(1),
                                data.getString(2),
                                data.getString(3),
                                data.getString(4),
                                data.getString(5),
                                data.getString(6)));

                adapterDoctorList = new DoctorListAdapter(getContext(),(ArrayList<DoctorsInformation>)mDoctorList);
                listView.setAdapter(adapterDoctorList);

            }
        }

    /*    btnViewDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDoctorTimeSlots(view);
            }
        });*/





        return v;
    }


}
