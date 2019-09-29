package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Database.EcareManager;

import java.util.ArrayList;
import java.util.List;


public class TestViewPreviousFragment extends Fragment {

    private static final String TAG = "Pre Tests";

    ListView listView;
    DBHandler db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_test_view_previous,container,false);

        ((TestActivity)getActivity()).getSupportActionBar().setTitle("Previous Tests");

        db = new DBHandler(getActivity().getApplicationContext());

        listView = (ListView)v.findViewById(R.id.preTestLIstView);

        Log.d(TAG, "onCreateView: started onCreaet View");


        showListView();


        return v;
    }

    public void showListView()
    {

        Log.d(TAG, "showListView: Started");

        //get table

        String pid = db.getPatientID();


        ArrayList<String> listData = new ArrayList<String>();

        Cursor testlist = db.getPreviousTests(pid);



        //fetch data
        if (testlist.moveToFirst()){



            do{

                listData.add(testlist.getString(testlist.getColumnIndex("test_name")));



            }while(testlist.moveToNext());
        }
        //end fo fetch data
        testlist.close();





        Log.d(TAG, "showListView: ended list fetching");

        ListAdapter adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listData);

        listView.setAdapter(adapter);

        Log.d(TAG, "showListView: adapter run");



        //clickable list view

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String theText = adapterView.getItemAtPosition(i).toString();

                Log.d(TAG, "onItemClick: clicked on " + theText);

                String TestID = "";

                TestID = db.gettestID(theText);

                if(TestID.equals(""))
                {

                    Toast.makeText(getContext(),"No ID with this Text",Toast.LENGTH_SHORT).show();


                }
                else
                {

                    Log.d(TAG, "onItemClick: Item clicked ID :"+TestID);

                    Bundle bundle = new Bundle();
                    bundle.putString("keyTestID",TestID);

                    Fragment frag = new TestUpdateFragment();
                    frag.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();


                }




            }
        });






    }
}
