package com.example.myapplication.EcareFragments;

import android.content.Context;
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
import com.example.myapplication.R;
import com.example.myapplication.SystemUsers;
import com.example.myapplication.SystemUsersAdapter;

import java.util.ArrayList;
import java.util.List;

public class DoctorTimeSlotListFragment extends Fragment {

    DBHandler myDB;
    private ListView listView;
    private SystemUsersAdapter adapterTimeSlotList;
    private List<SystemUsers> mDoctorTimeSlotsList;
    private Button btnViewPatientDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_doctor_time_slot_list, container, false);


        myDB = new DBHandler(getActivity().getApplicationContext());
        listView = (ListView)v.findViewById(R.id.listView1);
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

                mDoctorTimeSlotsList.add(


                        new SystemUsers(
                                data.getInt(0),
                                data.getString(1),
                                data.getString(2),
                                data.getString(3)));

                adapterTimeSlotList = new SystemUsersAdapter(getContext(), (ArrayList<SystemUsers>) mDoctorTimeSlotsList);
                listView.setAdapter(adapterTimeSlotList);
                //}

            }
        }




        return v;
    }

}
