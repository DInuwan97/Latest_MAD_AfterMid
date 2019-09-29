package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;


public class TestUpdateFragment extends Fragment {

    private static final String TAG = "Update Test";

    DBHandler db;
    String TestID,testName,testDes,testCost,testDate;
    EditText editName,editDes,editCost;
    TextView showDate;
    Button UpdateBtn,DeleteBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_test_update,container,false);

        db = new DBHandler(getActivity().getApplicationContext());

        editName  = (EditText)v.findViewById(R.id.test_update_getName);
        editDes  = (EditText)v.findViewById(R.id.test_update_getDes);
        editCost  = (EditText)v.findViewById(R.id.test_update_getCost);
        showDate = (TextView)v.findViewById(R.id.showDate);
        UpdateBtn = (Button)v.findViewById(R.id.updateBtn);
        DeleteBtn = (Button)v.findViewById(R.id.deleteBtn);



        Bundle bundle = this.getArguments();

        if(bundle != null){

            TestID = bundle.getString("keyTestID");

        }

        Log.d(TAG, "onCreateView: Test ID " + TestID);

        getDetails();

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editedTname = editName.getText().toString();
                String editedTdes = editDes.getText().toString();
                String editedTcost = editCost.getText().toString();

                if(editedTname.equals("")||editedTdes.equals("")||editedTcost.equals(""))
                {

                    Toast.makeText(getContext(),"Fields are empty",Toast.LENGTH_SHORT).show();


                }
                else
                {

                    updateDetails(editedTname,editedTdes,editedTcost);

                    Fragment frag = new TestViewPreviousFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();


                }






            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                deleteDetails();

                Fragment frag = new TestViewPreviousFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();

                Toast.makeText(getContext(),"Test Deleted Successfully! ",Toast.LENGTH_SHORT).show();




            }
        });




        return v;
    }





    public void getDetails()
    {
        Cursor TestDet = db.getTest(TestID);

        if(TestDet.moveToFirst())
        {
            testName = TestDet.getString(TestDet.getColumnIndex("test_name"));
            testDes = TestDet.getString(TestDet.getColumnIndex("description"));
            testCost = TestDet.getString(TestDet.getColumnIndex("test_price"));
            testDate = TestDet.getString(TestDet.getColumnIndex("test_date"));

        }

        editName.setText(testName, TextView.BufferType.EDITABLE);
        editDes.setText(testDes, TextView.BufferType.EDITABLE);
        editCost.setText(testCost, TextView.BufferType.EDITABLE);

        showDate.setText(testDate);



    }


    public void updateDetails(String editedTname,String editedTdes,String editedTcost)
    {


        db.updateTest(TestID,editedTname,editedTdes,editedTcost);

        Toast.makeText(getContext(),"Updated Correctly",Toast.LENGTH_SHORT).show();

        Log.d(TAG, "updateDetails: test updaetd");

    }

    public void deleteDetails()
    {

        db.deleteTest(TestID);


    }
}
