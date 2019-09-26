package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapters.TimeSlotListAdapter;
import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Models.TimeSlots;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    @Nullable


    DBHandler myDB;
    private ListView listView;
    private TimeSlotListAdapter adapterTimeSlotList;
    private List<TimeSlots> mDoctorTimeSlotsList;
    private Button btnViewPatientDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_doctor_time_slot_list, container, false);


        myDB = new DBHandler(getActivity().getApplicationContext());
        listView = (ListView)v.findViewById(R.id.listView2);
        myDB = new DBHandler(getActivity().getApplicationContext());
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getDoctorSlotListContents();

        mDoctorTimeSlotsList = new ArrayList<>();

        if(data.getCount() == 0){
            Toast.makeText(getActivity().getApplicationContext(),"The Table was Empty",Toast.LENGTH_LONG).show();
        }else{
            //Toast.makeText(getActivity().getApplicationContext(),"The Table was Not Empty",Toast.LENGTH_LONG).show();
            while(data.moveToNext()){

                //if(data.getString(3) == "Patient") {

                mDoctorTimeSlotsList.add(new TimeSlots
                               ("anura@gmail.com",
                                "Monday",
                                "7.00PM",
                                "8.30PM"));



                adapterTimeSlotList = new TimeSlotListAdapter(getContext(), (ArrayList<TimeSlots>) mDoctorTimeSlotsList);
                listView.setAdapter(adapterTimeSlotList);
                //}

            }
        }




        return v;
    }
}
