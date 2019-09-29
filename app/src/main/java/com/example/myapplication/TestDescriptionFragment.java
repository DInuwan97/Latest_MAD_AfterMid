package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;




import com.example.myapplication.Database.DBHandler;


public class TestDescriptionFragment extends Fragment {

    DBHandler db;
    TextView pName,pID,testDes;
    Button next;
    String patName,patId,patEmail,patDes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_test_description,container,false);

        ((TestActivity)getActivity()).getSupportActionBar().setTitle("Test Description");

        db = new DBHandler(getActivity().getApplicationContext());

        pName = (TextView)v.findViewById(R.id.pNameDes);
        pID = (TextView)v.findViewById(R.id.pIDdes);
        testDes = (TextView)v.findViewById(R.id.testDescription);

        next = (Button)v.findViewById(R.id.nextBtn);


        //set ID

        patId = db.getPatientID();

        String IDshow= "Patient ID : "+ db.getPatientID();

        pID.setText(IDshow);

        //set patient name

        setPatientName();

        setPatientEmail();

        setPatientDes();

        //set description


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment frag = new TestAddFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();




            }
        });



        return v;
    }

    public void setPatientName()
    {


        Cursor data = db.getPatientName(patId);

        if(data.moveToFirst())
        {

            patName = data.getString(data.getColumnIndex("username"));

            pName.setText(patName);


        }
        else
        {

            pName.setText("Hello");

        }






    }
    public void setPatientEmail()
    {


        Cursor data = db.getPatientEmail(patId);

        if(data.moveToFirst())
        {

            patEmail = data.getString(data.getColumnIndex("useremail"));



        }
        else
        {

            patEmail = "abc";

        }


    }


    public void setPatientDes()
    {


        Cursor data = db.getPatientTeseDes(patEmail);

        if(data.moveToFirst())
        {

            patDes = data.getString(data.getColumnIndex("tests"));

            testDes.setText(patDes);



        }
        else
        {

            testDes.setText("ECG, X-ray and CT scan");

        }


    }
}
