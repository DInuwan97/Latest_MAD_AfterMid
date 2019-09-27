package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TestAddFragment extends Fragment {


    DBHandler db;
    EditText testName,testDes,testCost;
    Button addTestBtn,historyBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_test_add,container,false);



        testName = (EditText)v.findViewById(R.id.addTestName);
        testDes = (EditText)v.findViewById(R.id.addTestDes);
        testCost = (EditText)v.findViewById(R.id.addTestCost);

        addTestBtn = (Button)v.findViewById(R.id.addTestBtn);
        historyBtn = (Button)v.findViewById(R.id.HistoryBtn);

        db = new DBHandler(getActivity().getApplicationContext());


        addTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String TestName = testName.getText().toString();
                String TestDes = testDes.getText().toString();
                String TestCost = testCost.getText().toString();
                //float TestCostFloat = Float.valueOf(TestCostTemp);

                if(TestName.equals("")||TestDes.equals("")||TestCost.equals(""))
                {

                    Toast.makeText(getContext(),"Fields are empty",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    float TestCostFloat = Float.valueOf(TestCost);

                    if(TestCostFloat == 0)
                    {
                        Toast.makeText(getContext(),"Add Cost",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        //database adding part

                        //int pid,int tid,String tname,String des,float price,String dat

                        Integer pid = Integer.valueOf(db.getPatientID());
                        Integer tid = Integer.valueOf(db.getLoggedUserID());

                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                        boolean chkAddTest = db.addTest(pid,tid,TestName,TestDes,TestCostFloat,currentDate);

                        if(chkAddTest==true)
                        {

                            Toast.makeText(getContext(),"Test Added successfully",Toast.LENGTH_SHORT).show();

                            testName.setText("");
                            testDes.setText("");
                            testCost.setText("");



                        }
                        else
                        {

                            Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();

                        }













                    }




                }












            }
        });



        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment frag = new TestViewPreviousFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();




            }
        });






        return v;

    }
}
